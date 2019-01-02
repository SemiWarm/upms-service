package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.AuthUtils;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.PwdUtils;
import com.pavis.upmsservice.form.PwdForm;
import com.pavis.upmsservice.model.SysAcl;
import com.pavis.upmsservice.model.SysRole;
import com.pavis.upmsservice.model.SysUser;
import com.pavis.upmsservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private DefaultTokenServices defaultTokenServices;

    @Autowired
    private SysUserServiceImpl userService;

    @Autowired
    private SysRoleServiceImpl roleService;

    @Autowired
    private SysAclServiceImpl aclService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        wrapper.allEq(map);
        SysUser user = userService.getOne(wrapper);
        Preconditions.checkNotNull(user, "用户不存在");
        List<SysRole> roleList = roleService.getRoleListByUsername(username);
        List<SysAcl> aclList = aclService.getAclListByUsername(username);
        Set<String> roles = Sets.newHashSet();
        Set<String> acls = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(roleList)) {
            roles = roleList.stream().map(SysRole::getName).collect(Collectors.toSet());
        } else {
            roles.add("ROLE_USER");
        }
        if (CollectionUtils.isNotEmpty(aclList)) {
            acls = aclList.stream().map(SysAcl::getUrl).collect(Collectors.toSet());
        }
        roles.addAll(acls);
        return new User(
                user.getUsername(),
                user.getPassword(),
                BooleanUtils.toBoolean(user.getEnabled()),
                BooleanUtils.toBoolean(user.getAccountNonExpired()),
                BooleanUtils.toBoolean(user.getCredentialsNonExpired()),
                BooleanUtils.toBoolean(user.getAccountNonLocked()),
                AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.join(roles, ",")));
    }

    @Override
    public void resetPwd(PwdForm form, HttpServletRequest request) {
        if (checkPwd(form.getNewPwd(), form.getConfirmPwd())) {
            SysUser current = userService.getById(form.getUserId());
            Preconditions.checkNotNull(current, "用户不存在");
            current.setPassword(PwdUtils.encrypt(form.getNewPwd()));
            current.setOperator(AuthUtils.getCurrentUsername());
            current.setOperateIp(IpUtils.getIpAddr(request));
            current.setOperateTime(new Date());
            userService.updateById(current);
            String tokenValue = StringUtils.substringAfter(request.getQueryString(), "=");
            // 清空当前token
            defaultTokenServices.revokeToken(tokenValue);
        } else {
            throw new ParamException("两次密码不一致");
        }
    }

    private boolean checkPwd(String newPwd, String confirmPwd) {
        return StringUtils.equals(newPwd, confirmPwd);
    }
}

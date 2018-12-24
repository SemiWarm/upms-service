package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.IgnorePropertiesUtils;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.PrincipalUtils;
import com.pavis.upmsservice.common.utils.PwdUtils;
import com.pavis.upmsservice.form.UserForm;
import com.pavis.upmsservice.mapper.SysUserMapper;
import com.pavis.upmsservice.model.SysUser;
import com.pavis.upmsservice.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser add(UserForm form, HttpServletRequest request) {
        if (checkUsernameExist(form.getUsername()) || checkTelephoneExist(form.getTelephone()) || checkEmailExist(form.getEmail())) {
            throw new ParamException("用户已存在");
        }

        // 生成随机密码
        String password = PwdUtils.randomPassword();
        log.info("password:{}", password);
        SysUser sysUser = SysUser.builder()
                .username(form.getUsername())
                .password(PwdUtils.encrypt(password))
                .telephone(form.getTelephone())
                .email(form.getEmail())
                .remark(form.getRemark())
                .deptId(form.getDeptId())
                .status(form.getStatus())
                .build();
        sysUser.setOperator(PrincipalUtils.getCurrentUsername());
        sysUser.setOperateIp(IpUtils.getIpAddr(request));
        sysUser.setOperateTime(new Date());
        // todo sendEmail通知用户初始密码,如果失败了则认为是无法创建成功
        baseMapper.insert(sysUser);
        return sysUser;
    }

    private boolean checkUsernameExist(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        wrapper.allEq(map);
        List<SysUser> sysUserList = baseMapper.selectList(wrapper);
        return null != sysUserList && sysUserList.size() > 0;
    }

    private boolean checkTelephoneExist(String telephone) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("telephone", telephone);
        wrapper.allEq(map);
        List<SysUser> sysUserList = baseMapper.selectList(wrapper);
        return null != sysUserList && sysUserList.size() > 0;
    }

    private boolean checkEmailExist(String email) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        wrapper.allEq(map);
        List<SysUser> sysUserList = baseMapper.selectList(wrapper);
        return null != sysUserList && sysUserList.size() > 0;
    }

    @Override
    public SysUser update(UserForm form, HttpServletRequest request) {
        if (checkUsernameExist(form.getUsername()) || checkTelephoneExist(form.getTelephone()) || checkEmailExist(form.getEmail())) {
            throw new ParamException("用户已存在");
        }
        SysUser before = baseMapper.selectById(form.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = new SysUser();
        BeanUtils.copyProperties(form, after, IgnorePropertiesUtils.getNullPropertyNames(form));
        after.setOperator(PrincipalUtils.getCurrentUsername());
        after.setOperateIp(IpUtils.getIpAddr(request));
        after.setOperateTime(new Date());
        baseMapper.updateById(after);
        return baseMapper.selectById(form.getId());
    }
}

package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.PrincipalUtils;
import com.pavis.upmsservice.form.RoleUserForm;
import com.pavis.upmsservice.mapper.SysRoleUserMapper;
import com.pavis.upmsservice.model.SysRoleUser;
import com.pavis.upmsservice.service.SysRoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {


    @Override
    public SysRoleUser add(RoleUserForm form, HttpServletRequest request) {
        if (checkExist(form.getRoleId(), form.getUserId())) {
            throw new ParamException("已存在新增的用户角色关系");
        }
        SysRoleUser roleUser = SysRoleUser.builder()
                .roleId(form.getRoleId())
                .userId(form.getUserId())
                .operator(PrincipalUtils.getCurrentUsername())
                .operateIp(IpUtils.getIpAddr(request))
                .operateTime(new Date())
                .build();
        baseMapper.insert(roleUser);
        return roleUser;
    }

    private boolean checkExist(Integer roleId, Integer userId) {
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("role_id", roleId);
        map.put("user_id", userId);
        wrapper.allEq(map);
        return baseMapper.selectOne(wrapper) != null;
    }
}

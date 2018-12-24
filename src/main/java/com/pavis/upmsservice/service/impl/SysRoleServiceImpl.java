package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.IgnorePropertiesUtils;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.PrincipalUtils;
import com.pavis.upmsservice.form.RoleForm;
import com.pavis.upmsservice.mapper.SysRoleMapper;
import com.pavis.upmsservice.model.SysRole;
import com.pavis.upmsservice.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public SysRole add(RoleForm form, HttpServletRequest request) {
        if (checkExist(form.getName())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole role = SysRole.builder()
                .name(form.getName())
                .status(form.getStatus())
                .type(form.getType())
                .remark(form.getRemark())
                .build();
        role.setOperator(PrincipalUtils.getCurrentUsername());
        role.setOperateIp(IpUtils.getIpAddr(request));
        role.setOperateTime(new Date());
        baseMapper.insert(role);
        return role;
    }


    @Override
    public SysRole update(RoleForm form, HttpServletRequest request) {
        if (checkExist(form.getName())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole before = baseMapper.selectById(form.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");

        SysRole after = new SysRole();
        BeanUtils.copyProperties(form, after, IgnorePropertiesUtils.getNullPropertyNames(form));
        after.setOperator(PrincipalUtils.getCurrentUsername());
        after.setOperateIp(IpUtils.getIpAddr(request));
        after.setOperateTime(new Date());
        baseMapper.updateById(after);
        return baseMapper.selectById(after.getId());
    }

    private boolean checkExist(String name) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        wrapper.allEq(map);
        List<SysRole> sysRoleList = baseMapper.selectList(wrapper);
        return null != sysRoleList && sysRoleList.size() > 0;
    }
}

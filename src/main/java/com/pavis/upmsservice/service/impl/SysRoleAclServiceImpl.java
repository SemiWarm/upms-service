package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.PrincipalUtils;
import com.pavis.upmsservice.form.RoleAclForm;
import com.pavis.upmsservice.mapper.SysRoleAclMapper;
import com.pavis.upmsservice.model.SysRoleAcl;
import com.pavis.upmsservice.service.SysRoleAclService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleAclServiceImpl extends ServiceImpl<SysRoleAclMapper, SysRoleAcl> implements SysRoleAclService {


    @Override
    public SysRoleAcl add(RoleAclForm form, HttpServletRequest request) {
        if (checkExist(form.getRoleId(), form.getAclId())) {
            throw new ParamException("已存在要新增的权限角色关系");
        }

        SysRoleAcl roleAcl = SysRoleAcl.builder()
                .roleId(form.getRoleId())
                .aclId(form.getAclId())
                .operator(PrincipalUtils.getCurrentUsername())
                .operateIp(IpUtils.getIpAddr(request))
                .operateTime(new Date())
                .build();

        baseMapper.insert(roleAcl);

        return roleAcl;
    }

    private boolean checkExist(Integer roleId, Integer aclId) {
        QueryWrapper<SysRoleAcl> wrapper = new QueryWrapper<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("role_id", roleId);
        map.put("acl_id", aclId);
        wrapper.allEq(map);
        return baseMapper.selectOne(wrapper) != null;
    }
}

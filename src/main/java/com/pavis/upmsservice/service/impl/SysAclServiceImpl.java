package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.IgnoreUtils;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.AuthUtils;
import com.pavis.upmsservice.form.AclForm;
import com.pavis.upmsservice.mapper.SysAclMapper;
import com.pavis.upmsservice.model.SysAcl;
import com.pavis.upmsservice.service.SysAclService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysAclServiceImpl extends ServiceImpl<SysAclMapper, SysAcl> implements SysAclService {

    @Override
    public SysAcl add(AclForm form, HttpServletRequest request) {
        if (checkExist(form.getAclModuleId(), form.getName())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder()
                .name(form.getName())
                .aclModuleId(form.getAclModuleId())
                .url(form.getUrl())
                .type(form.getType())
                .status(form.getStatus())
                .seq(form.getSeq())
                .remark(form.getRemark())
                .build();
        acl.setCode(generateCode());
        acl.setOperator(AuthUtils.getCurrentUsername());
        acl.setOperateTime(new Date());
        acl.setOperateIp(IpUtils.getIpAddr(request));
        baseMapper.insert(acl);
        return acl;
    }

    @Override
    public SysAcl update(AclForm form, HttpServletRequest request) {
        if (checkExist(form.getAclModuleId(), form.getName())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = baseMapper.selectById(form.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在");
        SysAcl after = new SysAcl();
        org.springframework.beans.BeanUtils.copyProperties(form, after, IgnoreUtils.getNullPropertyNames(form));
        after.setOperator(AuthUtils.getCurrentUsername());
        after.setOperateIp(IpUtils.getIpAddr(request));
        after.setOperateTime(new Date());
        baseMapper.updateById(after);
        return baseMapper.selectById(form.getId());
    }

    @Override
    public List<SysAcl> getAclListByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    public List<SysAcl> getAclListByRoleId(Integer roleId) {
        return baseMapper.selectByRoleId(roleId);
    }

    private boolean checkExist(Integer aclModuleId, String name) {
        QueryWrapper<SysAcl> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("acl_module_id", aclModuleId);
        map.put("name", name);
        wrapper.allEq(map);
        List<SysAcl> aclList = baseMapper.selectList(wrapper);
        return null != aclList && aclList.size() > 0;
    }

    private String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }
}

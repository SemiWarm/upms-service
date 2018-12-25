package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.RoleAclForm;
import com.pavis.upmsservice.model.SysRoleAcl;

import javax.servlet.http.HttpServletRequest;

public interface SysRoleAclService extends IService<SysRoleAcl> {
    SysRoleAcl add(RoleAclForm form, HttpServletRequest request);
}

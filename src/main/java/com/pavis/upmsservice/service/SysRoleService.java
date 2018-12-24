package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.RoleForm;
import com.pavis.upmsservice.model.SysRole;

import javax.servlet.http.HttpServletRequest;

public interface SysRoleService extends IService<SysRole> {

    SysRole add(RoleForm form, HttpServletRequest request);

    SysRole update(RoleForm form, HttpServletRequest request);
}

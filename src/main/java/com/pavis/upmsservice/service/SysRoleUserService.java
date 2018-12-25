package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.RoleUserForm;
import com.pavis.upmsservice.model.SysRoleUser;

import javax.servlet.http.HttpServletRequest;

public interface SysRoleUserService extends IService<SysRoleUser> {

    SysRoleUser add(RoleUserForm form, HttpServletRequest request);
}

package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.UserForm;
import com.pavis.upmsservice.model.SysUser;

import javax.servlet.http.HttpServletRequest;

public interface SysUserService extends IService<SysUser> {

    SysUser add(UserForm form, HttpServletRequest request);

    SysUser update(UserForm form, HttpServletRequest request);
}

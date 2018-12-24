package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.AclModuleForm;
import com.pavis.upmsservice.model.SysAclModule;

import javax.servlet.http.HttpServletRequest;

public interface SysAclModuleService extends IService<SysAclModule> {

    SysAclModule add(AclModuleForm form, HttpServletRequest request);

    SysAclModule update(AclModuleForm form, HttpServletRequest request);
}

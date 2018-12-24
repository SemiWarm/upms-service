package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.AclForm;
import com.pavis.upmsservice.model.SysAcl;

import javax.servlet.http.HttpServletRequest;

public interface SysAclService extends IService<SysAcl> {

    SysAcl add(AclForm form, HttpServletRequest request);

    SysAcl update(AclForm form, HttpServletRequest request);
}

package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.AclForm;
import com.pavis.upmsservice.model.SysAcl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SysAclService extends IService<SysAcl> {

    SysAcl add(AclForm form, HttpServletRequest request);

    SysAcl update(AclForm form, HttpServletRequest request);

    List<SysAcl> getAclListByUsername(String username);

    List<SysAcl> getAclListByRoleId(Integer roleId);
}

package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.AclModuleForm;
import com.pavis.upmsservice.service.impl.SysAclModuleServiceImpl;
import com.pavis.upmsservice.service.impl.SysTreeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys/acl/module")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleServiceImpl sysAclModuleService;

    @Autowired
    private SysTreeServiceImpl sysTreeService;

    @GetMapping("/list")
    public Response deptAclModuleList() {
        return ResUtils.ok(sysAclModuleService.list());
    }

    @PostMapping("/add")
    public Response addAclModule(@Valid AclModuleForm form, HttpServletRequest request) {
        return ResUtils.ok(sysAclModuleService.add(form, request));
    }

    @GetMapping("/tree")
    public Response aclModuleTree() {
        return ResUtils.ok(sysTreeService.aclModuleTree());
    }

    @PostMapping("/update")
    public Response updateAclModule(@Valid AclModuleForm form, HttpServletRequest request) {
        return ResUtils.ok(sysAclModuleService.update(form, request));
    }
}

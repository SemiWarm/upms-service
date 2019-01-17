package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.AclForm;
import com.pavis.upmsservice.service.impl.SysAclServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclServiceImpl sysAclService;

    @GetMapping("/list")
    public Response aclList() {
        return ResUtils.ok(sysAclService.list());
    }

    @PostMapping("/add")
    public Response addAcl(@Valid AclForm form, HttpServletRequest request) {
        return ResUtils.ok(sysAclService.add(form, request));
    }


    @PostMapping("/update")
    public Response updateAcl(@Valid AclForm form, HttpServletRequest request) {
        return ResUtils.ok(sysAclService.update(form, request));
    }
}

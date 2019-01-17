package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.RoleAclForm;
import com.pavis.upmsservice.service.impl.SysRoleAclServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/sys/role/acl")
public class SysRoleAclController {

    @Autowired
    private SysRoleAclServiceImpl roleAclService;

    @GetMapping("/list")
    public Response deptRoleAclList() {
        return ResUtils.ok(roleAclService.list());
    }

    @PostMapping(value = "/add")
    public Response addRoleAcl(RoleAclForm form, HttpServletRequest request) {
        return ResUtils.ok(roleAclService.add(form, request));
    }
}

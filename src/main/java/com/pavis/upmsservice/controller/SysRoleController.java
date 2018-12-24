package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.RoleForm;
import com.pavis.upmsservice.service.impl.SysRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys/role")
public class SysRoleController {


    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @PostMapping("/add")
    public Response addRole(@Valid RoleForm form, HttpServletRequest request) {
        return ResUtils.ok(sysRoleService.add(form, request));
    }


    @PostMapping("/update")
    public Response updateRole(@Valid RoleForm form, HttpServletRequest request) {
        return ResUtils.ok(sysRoleService.update(form, request));
    }

    @GetMapping("/tree")
    public Response tree() {
        return ResUtils.ok();
    }
}

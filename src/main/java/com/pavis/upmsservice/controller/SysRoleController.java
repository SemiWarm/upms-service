package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.RoleForm;
import com.pavis.upmsservice.service.impl.SysRoleServiceImpl;
import com.pavis.upmsservice.service.impl.SysTreeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys/role")
public class SysRoleController {


    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private SysTreeServiceImpl sysTreeService;

    @GetMapping("/list")
    public Response deptRoleList() {
        return ResUtils.ok(sysRoleService.list());
    }

    @PostMapping("/add")
    public Response addRole(@Valid RoleForm form, HttpServletRequest request) {
        return ResUtils.ok(sysRoleService.add(form, request));
    }


    @PostMapping("/update")
    public Response updateRole(@Valid RoleForm form, HttpServletRequest request) {
        return ResUtils.ok(sysRoleService.update(form, request));
    }

    @GetMapping("/tree/{id}")
    public Response tree(@PathVariable("id") Integer id) {
        return ResUtils.ok(sysTreeService.roleTree(id));
    }
}

package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.RoleUserForm;
import com.pavis.upmsservice.service.impl.SysRoleUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys/role/user")
public class SysRoleUserController {

    @Autowired
    private SysRoleUserServiceImpl roleUserService;

    @PostMapping("/add")
    public Response addRoleUser(@Valid RoleUserForm form, HttpServletRequest request) {
        return ResUtils.ok(roleUserService.add(form, request));
    }
}

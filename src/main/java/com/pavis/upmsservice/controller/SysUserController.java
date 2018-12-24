package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.UserForm;
import com.pavis.upmsservice.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys/user")
public class SysUserController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @PostMapping("/add")
    public Response addUser(@Valid UserForm form, HttpServletRequest request) {
        return ResUtils.ok(sysUserService.add(form, request));
    }


    @PostMapping("/update")
    public Response updateUser(@Valid UserForm form, HttpServletRequest request) {
        return ResUtils.ok(sysUserService.update(form, request));
    }
}

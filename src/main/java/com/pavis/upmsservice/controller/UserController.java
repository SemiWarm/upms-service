package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.PwdForm;
import com.pavis.upmsservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/reset/pwd")
    public Response updatePwd(@Valid PwdForm form, HttpServletRequest request) {
        userService.resetPwd(form, request);
        return ResUtils.ok();
    }

    @GetMapping("/")
    public Response getCurrentUserPrincipal(Principal user) {
        return ResUtils.ok(user);
    }
}

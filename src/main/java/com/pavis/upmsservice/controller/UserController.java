package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.PwdForm;
import com.pavis.upmsservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public Response getCurrentUserPrincipal(Principal user) {
        return ResUtils.ok(user);
    }

    @PostMapping("/reset/pwd")
    public Response updatePwd(@Valid PwdForm form, HttpServletRequest request) {
        userService.resetPwd(form, request);
        return ResUtils.ok();
    }

    @GetMapping("/{name}")
    public Map<String, Object> loadUserByUsername(@PathVariable("name") String username) throws UsernameNotFoundException {
        return userService.loadUserByUsername(username);
    }
}

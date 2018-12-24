package com.pavis.upmsservice.service;

import com.pavis.upmsservice.form.PwdForm;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends UserDetailsService {
    void resetPwd(PwdForm form, HttpServletRequest request);
}

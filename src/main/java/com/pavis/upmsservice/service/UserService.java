package com.pavis.upmsservice.service;

import com.pavis.upmsservice.form.PwdForm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    void resetPwd(PwdForm form, HttpServletRequest request);

    Map<String, Object> loadUserByUsername(String username) throws UsernameNotFoundException;
}

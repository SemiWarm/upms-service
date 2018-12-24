package com.pavis.upmsservice.controller;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import com.pavis.upmsservice.form.DeptForm;
import com.pavis.upmsservice.service.impl.SysDeptServiceImpl;
import com.pavis.upmsservice.service.impl.SysTreeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptServiceImpl sysDeptService;

    @Autowired
    private SysTreeServiceImpl sysTreeService;

    @PostMapping("/add")
    public Response addDept(@Valid DeptForm form, HttpServletRequest request) {
        return ResUtils.ok(sysDeptService.add(form, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tree")
    public Response deptTree() {
        return ResUtils.ok(sysTreeService.deptTree());
    }

    @PostMapping("/update")
    public Response updateDept(@Valid DeptForm form, HttpServletRequest request) {
        return ResUtils.ok(sysDeptService.update(form, request));
    }
}

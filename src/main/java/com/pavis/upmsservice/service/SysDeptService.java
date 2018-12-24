package com.pavis.upmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pavis.upmsservice.form.DeptForm;
import com.pavis.upmsservice.model.SysDept;

import javax.servlet.http.HttpServletRequest;

public interface SysDeptService extends IService<SysDept> {

    SysDept add(DeptForm form, HttpServletRequest request);

    SysDept update(DeptForm form, HttpServletRequest request);
}

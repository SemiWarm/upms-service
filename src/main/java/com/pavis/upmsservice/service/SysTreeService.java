package com.pavis.upmsservice.service;

import com.pavis.upmsservice.dto.SysAclModuleDto;
import com.pavis.upmsservice.dto.SysDeptDto;

import java.util.List;

public interface SysTreeService {
    List<SysDeptDto> deptTree();
    List<SysAclModuleDto> aclModuleTree();
    List<SysAclModuleDto> roleTree(Integer roleId);
}

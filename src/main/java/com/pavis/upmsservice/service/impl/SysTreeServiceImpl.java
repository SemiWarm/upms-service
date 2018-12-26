package com.pavis.upmsservice.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.LevelUtils;
import com.pavis.upmsservice.common.utils.AuthUtils;
import com.pavis.upmsservice.dto.SysAclDto;
import com.pavis.upmsservice.dto.SysAclModuleDto;
import com.pavis.upmsservice.dto.SysDeptDto;
import com.pavis.upmsservice.model.SysAcl;
import com.pavis.upmsservice.model.SysAclModule;
import com.pavis.upmsservice.model.SysDept;
import com.pavis.upmsservice.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysAclServiceImpl sysAclService;

    @Autowired
    private SysDeptServiceImpl sysDeptService;

    @Autowired
    private SysAclModuleServiceImpl sysAclModuleService;

    private static final String SUPER_ADMIN = "admin";

    @Override
    public List<SysDeptDto> deptTree() {
        List<SysDept> sysDeptList = sysDeptService.list();
        List<SysDeptDto> sysDeptDtoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(sysDeptList)) {
            for (SysDept dept : sysDeptList) {
                SysDeptDto dto = SysDeptDto.adapt(dept);
                sysDeptDtoList.add(dto);
            }
        }
        return deptListToTree(sysDeptDtoList);
    }

    @Override
    public List<SysAclModuleDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleService.list();
        List<SysAclModuleDto> aclModuleDtoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(aclModuleList)) {
            for (SysAclModule aclModule : aclModuleList) {
                SysAclModuleDto dto = SysAclModuleDto.adapt(aclModule);
                aclModuleDtoList.add(dto);
            }
        }
        return aclModuleListToTree(aclModuleDtoList);
    }

    @Override
    public List<SysAclModuleDto> roleTree(Integer roleId) {
        // 1. 当前用户的权限点列表
        List<SysAcl> currentUserAclList = getAclListByUsername(AuthUtils.getCurrentUsername());

        // 2. 当前角色的权限点列表
        List<SysAcl> currentRoleAclList = getAclListByRoleId(roleId);

        // 3. 当前系统所有权限点
        List<SysAclDto> aclDtoList = Lists.newArrayList();

        Set<Integer> currentUserAclIdSet = currentUserAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());
        Set<Integer> currentRoleAclIdSet = currentRoleAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());

        List<SysAcl> allAclList = sysAclService.list();
        for (SysAcl acl : allAclList) {
            SysAclDto dto = SysAclDto.adapt(acl);
            if (currentUserAclIdSet.contains(acl.getId())) {
                dto.setHasAcl(true);
            }
            if (currentRoleAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    private List<SysAclModuleDto> aclListToTree(List<SysAclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<SysAclModuleDto> aclModuleDtoList = aclModuleTree();

        Multimap<Integer, SysAclDto> aclDtoMultimap = ArrayListMultimap.create();
        for(SysAclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                aclDtoMultimap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleDtoList, aclDtoMultimap);
        return aclModuleDtoList;
    }

    private void bindAclsWithOrder(List<SysAclModuleDto> aclModuleDtoList, Multimap<Integer, SysAclDto> aclDtoMultimap) {
        if (CollectionUtils.isEmpty(aclModuleDtoList)) {
            return;
        }
        for (SysAclModuleDto dto : aclModuleDtoList) {
            List<SysAclDto> aclDtoList = (List<SysAclDto>)aclDtoMultimap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                aclDtoList.sort(Comparator.comparingInt(SysAcl::getSeq));
                dto.setSubAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getSubAclModuleList(), aclDtoMultimap);
        }
    }

    private List<SysAcl> getAclListByRoleId(Integer roleId) {
        return sysAclService.getAclListByRoleId(roleId);
    }

    private List<SysAcl> getAclListByUsername(String username) {
        if (StringUtils.isNotEmpty(username)) {
            if (isSuperAdmin(username)) {
                return sysAclService.list();
            } else {
                return sysAclService.getAclListByUsername(username);
            }
        } else {
            throw new ParamException("用户不存在");
        }
    }

    private boolean isSuperAdmin(String username) {
        return StringUtils.containsIgnoreCase(username, SUPER_ADMIN);
    }


    private List<SysAclModuleDto> aclModuleListToTree(List<SysAclModuleDto> aclModuleDtoList) {
        if (CollectionUtils.isEmpty(aclModuleDtoList)) {
            return Lists.newArrayList();
        }
        Multimap<String, SysAclModuleDto> aclModuleDtoMultimap = ArrayListMultimap.create();
        List<SysAclModuleDto> rootList = Lists.newArrayList();
        for (SysAclModuleDto dto : aclModuleDtoList) {
            aclModuleDtoMultimap.put(dto.getLevel(), dto);
            if (LevelUtils.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序
        rootList.sort(Comparator.comparingInt(SysAclModule::getSeq));
        // 生成递归树
        transformAclModuleTree(rootList, LevelUtils.ROOT, aclModuleDtoMultimap);
        return rootList;
    }

    public List<SysDeptDto> deptListToTree(List<SysDeptDto> sysDeptDtoList) {
        if (CollectionUtils.isEmpty(sysDeptDtoList)) {
            return Lists.newArrayList();
        }
        Multimap<String, SysDeptDto> deptDtoMultimap = ArrayListMultimap.create();
        List<SysDeptDto> rootList = Lists.newArrayList();
        for (SysDeptDto dto : sysDeptDtoList) {
            deptDtoMultimap.put(dto.getLevel(), dto);
            if (LevelUtils.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序
        rootList.sort(Comparator.comparingInt(SysDept::getSeq));
        // 生成递归树
        transformDeptTree(rootList, LevelUtils.ROOT, deptDtoMultimap);
        return rootList;
    }

    public void transformAclModuleTree(List<SysAclModuleDto> aclModuleDtoList, String level, Multimap<String, SysAclModuleDto> aclModuleDtoMultimap) {
        for (SysAclModuleDto aclModuleDto : aclModuleDtoList) {
            // 处理当前层级数据
            String nextLevel = LevelUtils.calculateLevel(level, aclModuleDto.getId());
            // 处理下一层
            List<SysAclModuleDto> tempAclModuleList = (List<SysAclModuleDto>) aclModuleDtoMultimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempAclModuleList)) {
                tempAclModuleList.sort(Comparator.comparingInt(SysAclModule::getSeq));
                aclModuleDto.setSubAclModuleList(tempAclModuleList);
                transformAclModuleTree(tempAclModuleList, nextLevel, aclModuleDtoMultimap);
            }
        }
    }

    public void transformDeptTree(List<SysDeptDto> sysDeptDtoList, String level, Multimap<String, SysDeptDto> deptDtoMultimap) {
        for (SysDeptDto sysDeptDto : sysDeptDtoList) {
            // 处理当前层级数据
            String nextLevel = LevelUtils.calculateLevel(level, sysDeptDto.getId());
            // 处理下一层
            List<SysDeptDto> tempDeptList = (List<SysDeptDto>) deptDtoMultimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                tempDeptList.sort(Comparator.comparingInt(SysDept::getSeq));
                sysDeptDto.setSubDeptList(tempDeptList);
                transformDeptTree(tempDeptList, nextLevel, deptDtoMultimap);
            }
        }
    }

}

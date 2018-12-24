package com.pavis.upmsservice.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.pavis.upmsservice.common.utils.LevelUtils;
import com.pavis.upmsservice.dto.SysAclModuleDto;
import com.pavis.upmsservice.dto.SysDeptDto;
import com.pavis.upmsservice.model.SysAclModule;
import com.pavis.upmsservice.model.SysDept;
import com.pavis.upmsservice.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysDeptServiceImpl sysDeptService;

    @Autowired
    private SysAclModuleServiceImpl sysAclModuleService;

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
        return null;
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

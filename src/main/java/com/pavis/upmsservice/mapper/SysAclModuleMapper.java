package com.pavis.upmsservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pavis.upmsservice.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper extends BaseMapper<SysAclModule> {
    List<SysAclModule> selectChildAclModuleByLevel(@Param("level") String level);
}

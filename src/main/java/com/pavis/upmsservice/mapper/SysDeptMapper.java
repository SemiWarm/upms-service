package com.pavis.upmsservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pavis.upmsservice.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper extends BaseMapper<SysDept> {
    List<SysDept> selectChildDeptByLevel(@Param("level") String level);
}

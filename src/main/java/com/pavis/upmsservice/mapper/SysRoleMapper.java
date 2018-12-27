package com.pavis.upmsservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pavis.upmsservice.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectByUsername(@Param("username") String username);
}

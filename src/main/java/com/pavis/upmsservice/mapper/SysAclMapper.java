package com.pavis.upmsservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pavis.upmsservice.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper extends BaseMapper<SysAcl> {

    List<SysAcl> selectByUsername(@Param("username") String username);

    List<SysAcl> selectByRoleId(@Param("roleId") Integer roleId);
}

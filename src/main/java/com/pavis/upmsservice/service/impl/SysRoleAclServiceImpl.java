package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pavis.upmsservice.mapper.SysRoleAclMapper;
import com.pavis.upmsservice.model.SysRoleAcl;
import com.pavis.upmsservice.service.SysRoleAclService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleAclServiceImpl extends ServiceImpl<SysRoleAclMapper, SysRoleAcl> implements SysRoleAclService {
}

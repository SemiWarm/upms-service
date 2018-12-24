package com.pavis.upmsservice.dto;

import com.google.common.collect.Lists;
import com.pavis.upmsservice.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
public class SysAclModuleDto extends SysAclModule {

    private List<SysAclModuleDto> subAclModuleList = Lists.newArrayList();

    public static SysAclModuleDto adapt(SysAclModule aclModule) {
        SysAclModuleDto dto = new SysAclModuleDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }
}

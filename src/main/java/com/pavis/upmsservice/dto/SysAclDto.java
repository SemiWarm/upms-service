package com.pavis.upmsservice.dto;

import com.pavis.upmsservice.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class SysAclDto extends SysAcl {

    // 是否要默认选中
    private boolean checked = false;

    // 是否有权限操作
    private boolean hasAcl = false;

    public static SysAclDto adapt(SysAcl acl) {
        SysAclDto dto = new SysAclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }


}

package com.pavis.upmsservice.dto;

import com.google.common.collect.Lists;
import com.pavis.upmsservice.model.SysDept;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
public class SysDeptDto extends SysDept {

    private List<SysDeptDto> subDeptList = Lists.newArrayList();

    public static SysDeptDto adapt(SysDept dept) {
        SysDeptDto dto = new SysDeptDto();
        BeanUtils.copyProperties(dept, dto);
        return dto;
    }
}

package com.pavis.upmsservice.form;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AclForm {

    private Integer id;

    @NotBlank(message = "权限点名称不能为空")
    private String name;

    @NotNull(message = "所属权限模块不能为空")
    private Integer aclModuleId;

    private String url;

    @NotNull(message = "权限点类型不能为空")
    private Integer type;

    @NotNull(message = "权限点状态不能为空")
    private Integer status;

    @NotNull(message = "展示顺序不能为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度不能超过150个字符")
    private String remark;
}

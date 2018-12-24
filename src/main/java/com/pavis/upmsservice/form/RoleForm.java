package com.pavis.upmsservice.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleForm {

    private Integer id;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotNull(message = "角色类型不能为空")
    private Integer type;

    @NotNull(message = "角色状态不能为空")
    private Integer status;

    @Length(max = 150, message = "备注长度不能超过150个字符")
    private String remark;

}

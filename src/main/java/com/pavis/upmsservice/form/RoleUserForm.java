package com.pavis.upmsservice.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleUserForm {

    private Integer id;

    @NotNull(message = "角色id不能为空")
    private Integer roleId;

    @NotNull(message = "用户id不能为空")
    private Integer userId;
}

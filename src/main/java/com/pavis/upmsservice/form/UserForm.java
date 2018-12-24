package com.pavis.upmsservice.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserForm {

    private Integer id;

    @NotBlank(message = "用户名不能空")
    private String username;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 1, max = 11, message = "手机号码格式有误")
    private String telephone;

    private String email;

    @Length(max = 150, message = "备注长度不能超过150个字符")
    private String remark;

    private Integer deptId;

    @NotNull(message = "用户状态不能为空")
    private Integer status;

}

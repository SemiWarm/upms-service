package com.pavis.upmsservice.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PwdForm {

    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotBlank(message = "密码不能为空")
    private String newPwd;

    @NotBlank(message = "密码不能为空")
    private String confirmPwd;
}

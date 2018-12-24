package com.pavis.upmsservice.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AclModuleForm {

    private Integer id;

    @NotBlank(message = "权限模块名称不能为空")
    private String name;

    private Integer parentId;

    private Integer status;

    @NotNull(message = "展示顺序不能为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度不能超过150个字符")
    private String remark;
}

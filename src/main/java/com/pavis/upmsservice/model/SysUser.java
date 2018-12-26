package com.pavis.upmsservice.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {

    private Integer id;
    private String username;
    private String password;
    private String telephone;
    private String email;
    private String remark;
    private Integer deptId;
    private Integer status;
    private Integer enabled;
    private Integer accountNonLocked;
    private Integer accountNonExpired;
    private Integer credentialsNonExpired;
    private String operator;
    private Date operateTime;
    private String operateIp;

}

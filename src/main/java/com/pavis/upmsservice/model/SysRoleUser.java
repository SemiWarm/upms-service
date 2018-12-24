package com.pavis.upmsservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysRoleUser {

    private Integer id;
    private Integer roleId;
    private Integer userId;
    private String operator;
    private Date operateTime;
    private String operateIp;

}

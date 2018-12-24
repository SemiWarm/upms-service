package com.pavis.upmsservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysLog {

    private Integer id;
    private Integer type;
    private Integer targetId;
    private String oldValue;
    private String newValue;
    private String operator;
    private Date operateTime;
    private String operateIp;

}

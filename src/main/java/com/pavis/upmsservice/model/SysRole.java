package com.pavis.upmsservice.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {

    private Integer id;
    private String name;
    private Integer type;
    private Integer status;
    private String remark;
    private String operator;
    private Date operateTime;
    private String operateIp;

}

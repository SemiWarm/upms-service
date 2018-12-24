package com.pavis.upmsservice.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAclModule {

    private Integer id;
    private String name;
    private Integer parentId;
    private String level;
    private Integer status;
    private Integer seq;
    private String remark;
    private String operator;
    private Date operateTime;
    private String operateIp;
}

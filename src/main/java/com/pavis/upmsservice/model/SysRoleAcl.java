package com.pavis.upmsservice.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleAcl {

    private Integer id;
    private Integer roleId;
    private Integer aclId;
    private String operator;
    private Date operateTime;
    private String operateIp;

}

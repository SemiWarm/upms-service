package com.pavis.upmsservice.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDept {

    @TableId
    private Integer id;
    private String name;
    private String level;
    private Integer seq;
    private String remark;
    private Integer parentId;
    private String operator;
    private Date operateTime;
    private String operateIp;

}

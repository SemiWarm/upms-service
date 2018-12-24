package com.pavis.upmsservice.common.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private Integer code;
    private String message;
    private Object data;
}

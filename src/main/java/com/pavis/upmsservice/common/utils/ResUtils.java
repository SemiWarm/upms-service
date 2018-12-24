package com.pavis.upmsservice.common.utils;

import com.pavis.upmsservice.common.http.Response;

public class ResUtils {

    public final static Integer STATUS_OK_CODE = 1;
    public final static Integer STATUS_ERROR_CODE = 0;
    public final static String STATUS_OK_MESSAGE = "操作成功";
    public final static String STATUS_ERROR_MESSAGE = "操作失败";

    public static Response ok() {
        return ok(null);
    }

    public static Response ok(Object data) {
        return ok(STATUS_OK_MESSAGE, data);
    }

    public static Response ok(String message, Object data) {
        return ok(STATUS_OK_CODE, message, data);
    }

    public static Response ok(int code, String message, Object data) {
        return Response
                .builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static Response error() {
        return error(null);
    }

    public static Response error(Object data) {
        return error(STATUS_ERROR_MESSAGE, data);
    }

    public static Response error(String message, Object data) {
        return error(STATUS_ERROR_CODE, message, data);
    }

    public static Response error(int code, String message, Object data) {
        return Response
                .builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}

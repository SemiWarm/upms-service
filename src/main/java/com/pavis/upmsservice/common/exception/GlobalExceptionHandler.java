package com.pavis.upmsservice.common.exception;

import com.pavis.upmsservice.common.http.Response;
import com.pavis.upmsservice.common.utils.ResUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public Response handleBindException(BindException exception) {
        StringBuilder errors = new StringBuilder();
        exception.getAllErrors().forEach(ex -> errors.append(ex.getDefaultMessage()).append(";"));
        return ResUtils.error(errors.toString(), null);
    }

    @ExceptionHandler(value = ParamException.class)
    public Response handleParamException(ParamException exception) {
        return ResUtils.error(exception.getMessage(), null);
    }
}

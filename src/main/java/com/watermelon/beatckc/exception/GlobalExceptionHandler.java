package com.watermelon.beatckc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

//处理全局捕获
@Slf4j
@ControllerAdvice("com.watermelon.beatckc.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> catchValidationError(HandlerMethodValidationException e) {
        Map<String, Object> map = new HashMap<>();
        e.getParameterValidationResults().forEach(v -> {
            String paramName = v.getMethodParameter().getParameterName();
            v.getResolvableErrors().forEach(error -> {
                String message = error.getDefaultMessage();
                map.put(paramName, message);
            });
        });
        //log包含时间戳和错误堆栈，会被写入系统日志
        System.out.println(e.getMessage());
        log.error("校验异常:{}", e.getMessage());

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}

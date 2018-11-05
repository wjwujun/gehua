package com.gehua.common.advice;

import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
import com.gehua.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(GehuaException.class)
    public ResponseEntity<ExceptionResult> handleExcption(GehuaException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}

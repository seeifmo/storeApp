package com.cgc.store.controlleradvice;

import com.cgc.store.dto.AppResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new AppResponse(400, null, e.getMessage()));
    }
}

package com.example.oril_test_task.exception;

import com.example.oril_test_task.api.dto.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DataNotFound.class)
    public ResponseEntity DataNotFoundErrorHandler(DataNotFound exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(exception.getMessage()));
    }
}

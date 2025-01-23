package com.igq.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ProductClassExceptionsController {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> noProductHandle(ProductNotFoundException productNotFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productNotFoundException.getMessage());
    }


    @ExceptionHandler(InvalidProductRequestException.class)
    public ResponseEntity<String> invalidProductRequestExceptionHandle(InvalidProductRequestException productNotFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productNotFoundException.getMessage());
    }

}

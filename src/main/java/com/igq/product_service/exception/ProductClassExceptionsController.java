package com.igq.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ProductClassExceptionsController {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> noProductHandle(ProductNotFoundException productNotFoundException){
        return new ResponseEntity<>("Product not Found With ID!", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidProductRequestException.class)
    public ResponseEntity<String> invalidProductRequestExceptionHandle(InvalidProductRequestException productNotFoundException){
        return new ResponseEntity<>("Invalid product request: All fields (name, description, and price) must be non-empty.", HttpStatus.BAD_REQUEST);
    }

}

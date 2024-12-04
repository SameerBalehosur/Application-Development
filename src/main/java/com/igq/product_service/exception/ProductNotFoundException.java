package com.igq.product_service.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class ProductNotFoundException extends  Exception{
    public ProductNotFoundException(){
        super("Product Not Found!!");
    }
}

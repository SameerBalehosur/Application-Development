package com.igq.product_service.controller;

import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.dto.ProductResponse;
import com.igq.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest request) {
        if (request != null) {
            try {
                productService.createProduct(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @GetMapping("/getProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductResponse> getAllProduct() {
        try {
            List<ProductResponse> allProduct = productService.getAllProduct();
            return allProduct;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

package com.igq.product_service.controller;

import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.dto.ProductResponse;
import com.igq.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity createProduct(@RequestBody ProductRequest request) {
        if (request != null) {
            try {
                String product = productService.createProduct(request);
                if(product.equalsIgnoreCase("Created")){
                    return new ResponseEntity<>(product,HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<>(product,HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @GetMapping("/getProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductResponse> getAllProduct() {
        try {
            return productService.getAllProduct();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addProducts")
    public HttpStatus addProducts(@RequestBody List<ProductRequest> productsRequest) {
        if(!productsRequest.isEmpty()){
            productService.addProducts(productsRequest);
        }
        return null;
    }


}

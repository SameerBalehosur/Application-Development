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
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
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

    @GetMapping("/getProducts")
    public ResponseEntity<?> getAllProduct() {
        try {
            List<ProductResponse> allProduct = productService.getAllProduct();
            if(!allProduct.isEmpty()){
                return new ResponseEntity<>(allProduct,HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Products List is Empty!!",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        if(!id.isEmpty()){
            try {
                ProductResponse productByID = productService.getProductByID(id);
                return new ResponseEntity<>(productByID,HttpStatus.FOUND);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>("Data Not Found with "+id,HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addProducts")
    public ResponseEntity<?> addProducts(@RequestBody List<ProductRequest> productsRequest) {
        if(!productsRequest.isEmpty()){
            productService.addProducts(productsRequest);
            return new ResponseEntity<>("Added",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("List can't be Empty",HttpStatus.BAD_REQUEST);
    }


}

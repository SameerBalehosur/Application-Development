package com.igq.product_service.controllers;

import com.igq.product_service.constants.ProductsConstants;
import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {


    @Mock
    private ProductService productService;

    @InjectMocks
    ProductController productController;

    @Test
    void createProductSuccessfully() {
        ProductRequest request = new ProductRequest();
        request.setName("Sameer");
        request.setDescription("High-end gaming laptop");
        request.setPrice(BigDecimal.valueOf(1200.0));

        // Mock service behavior

        Mockito.when(productService.createProduct(request)).thenReturn(ProductsConstants.CREATED);
        ResponseEntity<?> product = productController.createProduct(request);
        assertEquals(HttpStatus.CREATED,product.getStatusCode());
        assertEquals(ProductsConstants.CREATED,product.getBody());
        Mockito.verify(productService,Mockito.times(1)).createProduct(request);

    }

}
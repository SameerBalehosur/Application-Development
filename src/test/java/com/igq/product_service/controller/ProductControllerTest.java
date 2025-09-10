package com.igq.product_service.controller;

import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.model.Product;
import com.igq.product_service.repository.ProductRepository;
import com.igq.product_service.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;


    @Test
    public void createProductSuccessfully() {
        // Arrange
        Product product = new Product();
        product.setId("1");
        product.setName("Sameer");
        product.setDescription("iPhone-15");
        product.setPrice(BigDecimal.valueOf(12345));

        // Mock repository behavior (match any Product)
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product saved = invocation.getArgument(0);
            saved.setId("1");
            return saved;
        });
        ProductRequest request = new ProductRequest();
        request.setPrice(BigDecimal.valueOf(12345.0));
        request.setDescription(product.getDescription());
        request.setName(product.getName());

        // Act
        String productId = productService.createProduct(request);

        // Assert
        Assertions.assertEquals("1", productId);
    }



}
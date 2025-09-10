package com.igq.product_service.controller;

import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.dto.ProductResponse;
import com.igq.product_service.model.Product;
import com.igq.product_service.repository.ProductRepository;
import com.igq.product_service.service.ProductService;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
        assertEquals("1", productId);
    }

    @Test
    void getAllProductsSuccessfully() {
        // Arrange
        Product product1 = new Product("1", "Laptop", 1000.0);
        Product product2 = new Product("2", "Mobile", 500.0);

        List<Product> products = List.of(product1, product2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // Act
        ProductResponse response = productService.getAllProducts(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getTotalItems());
        assertEquals(1, response.getTotalPages());
        assertEquals(2, response.getProductList().size());
        assertEquals("Laptop", response.getProductList().get(0).getName());

        verify(productRepository, times(1)).findAll(pageable);
    }

}
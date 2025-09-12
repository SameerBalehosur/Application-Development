package com.igq.product_service.controllers;

import com.igq.product_service.constants.ProductsConstants;
import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.dto.ProductResponse;
import com.igq.product_service.exception.ProductNotFoundException;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        assertEquals(HttpStatus.CREATED, product.getStatusCode());
        assertEquals(ProductsConstants.CREATED, product.getBody());
        Mockito.verify(productService, Mockito.times(1)).createProduct(request);

    }

    @Test
    void createProductFail() {
        ProductRequest request = new ProductRequest();
        // Mock service behavior

        Mockito.when(productService.createProduct(request)).thenReturn(String.valueOf(HttpStatus.BAD_REQUEST));
        ResponseEntity<?> product = productController.createProduct(request);
        assertEquals(HttpStatus.BAD_REQUEST, product.getStatusCode());
        Mockito.verify(productService, Mockito.times(1)).createProduct(request);

    }

    @Test
    public void getAllProductSuccess() {
        ProductResponse request = new ProductResponse();
        request.setName("Sameer");
        request.setDescription("High-end gaming laptop");
        request.setPrice(BigDecimal.valueOf(1200.0));
        List<ProductResponse> productResponses = new ArrayList<>();
        productResponses.add(request);
        Mockito.when(productService.getAllProduct()).thenReturn(productResponses);
        ResponseEntity<?> allProduct = productController.getAllProduct();
        assertEquals(HttpStatus.OK, allProduct.getStatusCode());
        Mockito.verify(productService, Mockito.times(1)).getAllProduct();

    }

    @Test
    public void getAllProductEmptyList() {
        List<ProductResponse> productResponses = new ArrayList<>();
        Mockito.when(productService.getAllProduct()).thenReturn(productResponses);
        ResponseEntity<?> allProduct = productController.getAllProduct();
        assertEquals(HttpStatus.NOT_FOUND, allProduct.getStatusCode());
        Mockito.verify(productService, Mockito.times(1)).getAllProduct();

    }

    @Test
    public void getProductByIdSuccess() {
        String productID = "111";
        String name = "sameer";
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productID);
        productResponse.setName(name);

        Mockito.when(productService.getProductByID(productID)).thenReturn(productResponse);
        ResponseEntity<?> productById = productController.getProductById(productID);
        assertEquals(HttpStatus.OK, productById.getStatusCode());

    }

    @Test
    public void getProductByIdFail() {
        String productID = "";
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productID);
        ResponseEntity<?> productById = productController.getProductById(productID);
        assertEquals(HttpStatus.BAD_REQUEST, productById.getStatusCode());

    }

    @Test
    public void addProductsSuccess() {
        ProductRequest request = new ProductRequest();
        request.setName("Laptop");
        request.setPrice(BigDecimal.valueOf(1000.0));

        List<ProductRequest> productRequests = List.of(request);
        Mockito.doNothing().when(productService).addProducts(productRequests);
        ResponseEntity<?> responseEntity = productController.addProducts(productRequests);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(productService, times(1)).addProducts(productRequests);
    }

    @Test
    public void addProductsFail() {
        //Declared Empty List and checking flow
        List<ProductRequest> productRequests = new ArrayList<>();
        ResponseEntity<?> responseEntity = productController.addProducts(productRequests);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void updateProductSuccess() {
        String id = "12";
        ProductRequest request = new ProductRequest();
        ProductResponse response = new ProductResponse();
        when(productService.updateProduct(id, request)).thenReturn(response);

        ResponseEntity<?> responseEntity = productController.updateProduct(id, request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateProductFail() {
        String id = " 1";
        ProductRequest request = new ProductRequest();
        when(productService.updateProduct(id, request)).thenThrow(new ProductNotFoundException("Not Found"));
        assertThrows(ProductNotFoundException.class, () -> {
            productController.updateProduct(id, request);
        });

        verify(productService, times(1)).updateProduct(id, request);
    }

}
package com.igq.product_service.controllers;

import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.dto.ProductResponse;
import com.igq.product_service.model.Product;
import com.igq.product_service.repository.ProductRepository;
import com.igq.product_service.service.ProductService;

import org.junit.jupiter.api.BeforeAll;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;


    @BeforeAll
    public static void decalringBeforeAll(){
//        product.setId("1");
//        product.setName("Sameer");
//        product.setDescription("iPhone-15");
//        product.setPrice(BigDecimal.valueOf(12345));
    }


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
    public void getAllProductsSuccessfully() {
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

    @Test
    public void getProductByIdSuccessfully() {
        //Arrange
        Product product = new Product("1", "Laptop", 1000.0);
        //Call or Act
        when(productRepository.findAllById(product.getId())).thenReturn(product);
        //Mapping
        ProductResponse productByID = productService.getProductByID(product.getId());
        //Checking
        assertEquals("1",productByID.getId());
        assertEquals("Laptop",productByID.getName());
        assertEquals(BigDecimal.valueOf(1000.0),productByID.getPrice());
    }

    @Test
    void addProductsSuccessfully() {
        Product product1 = new Product("1", "Laptop", 1000.0);
        Product product2 = new Product("2", "Mobile", 500.0);

        List<Product> products = List.of(product1, product2);
        ProductRequest request =new ProductRequest();
        request.setProductlist(products);

        List<ProductRequest> requestList = new ArrayList<>();
        requestList.add(request);

        doNothing().when(productService).addProducts(requestList);


        verify(productService,times(1)).addProducts(requestList);
    }
}
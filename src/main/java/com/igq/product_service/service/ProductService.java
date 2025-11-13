package com.igq.product_service.service;

import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.dto.ProductResponse;
import com.igq.product_service.exception.InvalidProductRequestException;
import com.igq.product_service.exception.ProductNotFoundException;
import com.igq.product_service.model.Product;
import com.igq.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Builder
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String createProduct(ProductRequest request) {
        if (request == null || request.getDescription() == null || request.getDescription().isEmpty() || request.getName() == null || request.getName().isEmpty() || request.getPrice() == null) {
            throw new InvalidProductRequestException("Invalid product request: All fields (name, description, and price) must be non-empty.");
        }
        Product product = Product.builder().name(request.getName()).description(request.getDescription()).price(request.getPrice()).build();
        productRepository.save(product);
        log.info("Product {} is created", product.getId());
        return product.getId();

    }

    public List<ProductResponse> getAllProduct() {
        List<Product> all = productRepository.findAll();
        if (!all.isEmpty()) {
            return all.stream().map(this::mapToProduct).collect(Collectors.toList());
        }
        log.info("Fetched All the Products Data {}", all);
        return null;
    }

    public ProductResponse getProductByID(String id) {
        ProductResponse productResponse = new ProductResponse();
        Product allById;
        allById = productRepository.findAllById(id);
        if (allById == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        productResponse.setId(allById.getId());
        productResponse.setName(allById.getName());
        productResponse.setDescription(allById.getDescription());
        productResponse.setPrice(allById.getPrice());
        log.info("Product Updated Vals {}", productResponse);

        return productResponse;
    }

    private ProductResponse mapToProduct(Product product) {
        log.info("Product Data check {}",product);
        return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription()).price(product.getPrice()).build();
    }

    public void addProducts(List<ProductRequest> productRequests) {
        if (productRequests == null || productRequests.isEmpty()) {
            throw new InvalidProductRequestException("Product list cannot be empty.");
        }

        // Filtering out invalid ProductRequest objects
        List<Product> validProducts = productRequests.stream()
                .filter(request -> {
                    try {
                        validateProductRequest(request);
                        return true;
                    } catch (InvalidProductRequestException e) {
                        log.warn("Skipping invalid product request: {}", request, e);
                        return false;
                    }
                })
                .map(data -> {
                    Product product = new Product();
                    product.setName(data.getName());
                    product.setDescription(data.getDescription());
                    product.setPrice(data.getPrice());
                    return product;
                })
                .toList();

        if (validProducts.isEmpty()) {
            throw new InvalidProductRequestException("No valid products to add.");
        }

        productRepository.saveAll(validProducts);
        log.info("List of valid products added: {}", validProducts);
    }
    private void validateProductRequest(ProductRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new InvalidProductRequestException("Product name cannot be null or empty.");
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new InvalidProductRequestException("Product description cannot be null or empty.");
        }
        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidProductRequestException("Product price must be greater than zero.");
        }
    }
    public ProductResponse updateProduct(String id, ProductRequest request) {
        if (request instanceof ProductRequest) {
            log.info("Checking te Product Request Type to add further Implementation {}", request instanceof ProductRequest);
        }
        if (request == null ||
                (request.getName() != null && request.getName().trim().isEmpty()) ||
                (request.getDescription() != null && request.getDescription().trim().isEmpty()) ||
                (request.getPrice() != null && request.getPrice().compareTo(BigDecimal.ZERO) < 0)) {
            throw new InvalidProductRequestException("Invalid product request: Fields must be non-empty and price must be non-negative.");
        }
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with ID " + id + " not found."));
        log.info("Product Data for validation--->{}", product);
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            log.info("Inside the Description Setting Page");
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        Product updatedProduct = productRepository.save(product);
        ProductResponse response = new ProductResponse();
        response.setId(updatedProduct.getId());
        response.setName(updatedProduct.getName());
        response.setDescription(updatedProduct.getDescription());
        response.setPrice(updatedProduct.getPrice());
        log.info("Product Updated Vals {}", response);

        return response;
    }

    public ProductResponse getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return ProductResponse.builder().productList(productPage.getContent()).totalItems(productPage.getTotalElements()).totalPages(productPage.getTotalPages()).currentPage(productPage.getNumber()).build();
    }


}

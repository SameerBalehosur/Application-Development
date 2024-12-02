package com.igq.product_service.service;

import com.igq.product_service.dto.ProductRequest;
import com.igq.product_service.dto.ProductResponse;
import com.igq.product_service.model.Product;
import com.igq.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (request.getDescription() != null && request.getName() != null && request.getPrice() != null &&
                !request.getDescription().isEmpty() && !request.getName().isEmpty()) {
            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice()).build();
            productRepository.save(product);
            log.info("Product {} is created", product.getId());
            return "Created";
        }
        log.info("Something went wrong");
        return "Bad Request";
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> all = productRepository.findAll();
        if (!all.isEmpty()) {
            return all.stream().map(this::mapToProduct).collect(Collectors.toList());
        }
        log.info("Fetched All the Products Data {}", all);
        return null;
    }

    private ProductResponse mapToProduct(Product product) {
        return ProductResponse.builder().id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice()).build();
    }

    public void addProducts(List<ProductRequest> productRequests) {
        if(!productRequests.isEmpty()){
            List<Product> collect = productRequests.stream().map(data -> {
                Product product = new Product();
                product.setName(data.getName());
                product.setDescription(data.getDescription());
                product.setPrice(data.getPrice());
                return product;
            }).toList();
            productRepository.saveAll(collect);
            log.info("List of Products That are being adding... {}", collect);
        }
    }
}

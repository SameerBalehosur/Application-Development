package com.igq.product_service.repository;

import com.igq.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {

    Product findAllById(String id);

}

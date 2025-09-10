package com.igq.product_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String typeOfProduct;
    private Date createdDate;
    private Date modifiedDate;

    public Product(String id, String name, double price) {
        this.id=id;
        this.name=name;
        this.price= BigDecimal.valueOf(price);
    }
}

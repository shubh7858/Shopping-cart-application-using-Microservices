package com.shubhampatil.ProductService.Model;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private Long price;
    private long quantity;
}

package com.shubhampatil.ProductService.Service;

import com.shubhampatil.ProductService.Model.ProductRequest;
import com.shubhampatil.ProductService.Model.ProductResponse;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);
}

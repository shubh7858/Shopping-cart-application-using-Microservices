package com.shubhampatil.ProductService.Service;

import com.shubhampatil.ProductService.Entity.Product;
import com.shubhampatil.ProductService.Model.ProductRequest;
import com.shubhampatil.ProductService.Model.ProductResponse;
import com.shubhampatil.ProductService.Repository.ProductRepository;
import com.shubhampatil.ProductService.exception.ProductServiceCustomException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding Product.......");

        Product product = Product.builder()
                          .productName(productRequest.getName())
                          .price(productRequest.getPrice())
                          .quantity(productRequest.getQuantity()).build();
        productRepository.save(product);
        log.info("Product Created......");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
       log.info("Get the prduct by using id {}",productId);

       Product product=productRepository.findById(productId)
               .orElseThrow(() -> new ProductServiceCustomException("Product with Given ID not found","PRODUCT_NOT_FOUND"));

       ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);

        return productResponse;




    }
}

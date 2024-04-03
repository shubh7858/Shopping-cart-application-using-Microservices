package com.shubhampatil.ProductService.Controller;

import com.shubhampatil.ProductService.Model.ProductRequest;
import com.shubhampatil.ProductService.Model.ProductResponse;
import com.shubhampatil.ProductService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){

    Long productID = productService.addProduct(productRequest);

    return new ResponseEntity<>(productID, HttpStatus.CREATED);
    }



    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer') || hasAuthority('SCOPE_internal')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
         ProductResponse productResponse =productService.getProductById(productId);

         return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<String> reduceQuantity(@PathVariable("id") long productId,@RequestParam long quantity){
       productService.reduceQuantity(productId,quantity);
       return new ResponseEntity<>("Product Reduced Successfully",HttpStatus.OK);
    }




}

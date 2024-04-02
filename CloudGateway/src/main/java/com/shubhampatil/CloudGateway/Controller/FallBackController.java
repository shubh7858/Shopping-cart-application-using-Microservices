package com.shubhampatil.CloudGateway.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {
    @GetMapping("/orderServiceFallback")
    public String orderServiceFallback(){
        return "Order Service Is Down....";
    }

    @GetMapping("/productServiceFallback")
    public String productServiceFallback(){
        return "Product Service Is Down....";
    }

    @GetMapping("/paymentServiceFallback")
    public String paymentServiceFallback(){
        return "payment Service Is Down....";
    }

}

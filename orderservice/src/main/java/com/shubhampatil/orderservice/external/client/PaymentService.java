package com.shubhampatil.orderservice.external.client;

import com.shubhampatil.orderservice.external.Request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping
    public ResponseEntity<String> doPayment(@RequestBody PaymentRequest paymentRequest);
}

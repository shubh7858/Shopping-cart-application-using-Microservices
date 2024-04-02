package com.shubhampatil.PaymentService.Controller;

import com.shubhampatil.PaymentService.Entity.TransactionDetails;
import com.shubhampatil.PaymentService.Model.PaymentRequest;
import com.shubhampatil.PaymentService.Model.PaymentResponse;
import com.shubhampatil.PaymentService.Service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Log4j2
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> doPayment(@RequestBody PaymentRequest paymentRequest) {



        return new ResponseEntity<>(paymentService.doPayment(paymentRequest),HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId){

        return new ResponseEntity<>(paymentService.getPaymentDetailsByOrderId(orderId),HttpStatus.OK);
    }
}

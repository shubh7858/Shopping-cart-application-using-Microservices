package com.shubhampatil.PaymentService.Controller;

import com.shubhampatil.PaymentService.Entity.TransactionDetails;
import com.shubhampatil.PaymentService.Model.PaymentRequest;
import com.shubhampatil.PaymentService.Service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

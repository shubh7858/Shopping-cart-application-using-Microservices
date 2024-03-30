package com.shubhampatil.PaymentService.Service;


import com.shubhampatil.PaymentService.Model.PaymentRequest;

public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);
}

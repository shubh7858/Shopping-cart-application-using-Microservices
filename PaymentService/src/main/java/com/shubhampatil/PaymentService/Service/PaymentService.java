package com.shubhampatil.PaymentService.Service;


import com.shubhampatil.PaymentService.Model.PaymentRequest;
import com.shubhampatil.PaymentService.Model.PaymentResponse;

public interface PaymentService {
    PaymentResponse getPaymentDetailsByOrderId(long orderId);

    String doPayment(PaymentRequest paymentRequest);
}

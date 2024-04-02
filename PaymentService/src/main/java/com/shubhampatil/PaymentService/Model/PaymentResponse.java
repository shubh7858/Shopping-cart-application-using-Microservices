package com.shubhampatil.PaymentService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private long orderId;
    private long orderAmount;
    private String orderStatus;
    private Instant orderDate;
    private PaymentMode paymentMode;
}

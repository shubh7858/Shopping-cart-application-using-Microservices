package com.shubhampatil.orderservice.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private long orderId;
    private Instant date;
    private String orderStatus;
    private long orderAmount;
    private long quantity;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDetails {
        private String productName;
        private Long productId;
        private Long price;
        private Long quantity;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentDetails {

        private long orderId;
        private long paymentAmount;
        private String paymentStatus;
        private Instant paymentDate;
        private PaymentMode paymentMode;
    }




}

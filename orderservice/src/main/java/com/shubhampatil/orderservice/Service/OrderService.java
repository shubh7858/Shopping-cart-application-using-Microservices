package com.shubhampatil.orderservice.Service;

import com.shubhampatil.orderservice.Model.OrderRequest;
import com.shubhampatil.orderservice.Model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}

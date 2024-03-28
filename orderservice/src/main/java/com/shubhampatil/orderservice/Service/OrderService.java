package com.shubhampatil.orderservice.Service;

import com.shubhampatil.orderservice.Model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}

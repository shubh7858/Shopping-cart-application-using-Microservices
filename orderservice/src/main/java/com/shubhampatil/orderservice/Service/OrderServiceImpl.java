package com.shubhampatil.orderservice.Service;

import com.shubhampatil.orderservice.Entity.Order;
import com.shubhampatil.orderservice.Model.OrderRequest;
import com.shubhampatil.orderservice.Repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order request {}",orderRequest);

         Order order = Order.builder().productId(orderRequest.getProductId())
                          .quantity(orderRequest.getQuantity()).orderDate(Instant.now()).orderStatus("CREATED")
                          .amount(orderRequest.getTotalAmount()).build();
        log.info("Creating the order now....wait");
        order =orderRepository.save(order);

        log.info("Order Placed Successfully With Order ID {}",order.getId());

        return order.getId();
    }
}

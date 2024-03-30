package com.shubhampatil.orderservice.Service;

import com.shubhampatil.ProductService.Model.ProductResponse;
import com.shubhampatil.orderservice.Entity.Order;
import com.shubhampatil.orderservice.Model.OrderRequest;
import com.shubhampatil.orderservice.Model.OrderResponse;
import com.shubhampatil.orderservice.Repository.OrderRepository;
import com.shubhampatil.orderservice.exception.CustomException;
import com.shubhampatil.orderservice.external.Request.PaymentRequest;
import com.shubhampatil.orderservice.external.client.PaymentService;
import com.shubhampatil.orderservice.external.client.ProductService;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Qualifier;
import java.time.Instant;


@Log4j2
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order request {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating Order Now By Reducing Stock first");

        Order order = Order.builder().productId(orderRequest.getProductId())
                          .quantity(orderRequest.getQuantity()).orderDate(Instant.now()).orderStatus("CREATED")
                          .amount(orderRequest.getTotalAmount()).build();
        log.info("Creating the order now....wait");
        order =orderRepository.save(order);

        log.info("Order Placed Successfully With Order ID {}",order.getId());

        log.info("CALLING THE PAYMENT SERVICE FOR THE PAYMENT");

        PaymentRequest paymentRequest =  PaymentRequest.builder()
                .orderId(order.getId()).paymentMode(orderRequest.getPaymentmode()).amount(order.getAmount()).build();

        String orderStatus=null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("PAYMENT IS SUCCESSFUL CHANGING THE ORDER STATUS TO PLACED");
            orderStatus = "PLACED";
        }catch (Exception e){
          log.error("PAYMENT PROCESS IS FAILED.CHANGING THE ORDER STATUS TO PAYMENT_FAILED");
          orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);



        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new CustomException("Order not found with Id:"+ orderId,"Order_Not_Found",404));
         log.info("Calling The Product Srvice To get THe Product Details With ID {}",order.getProductId());

        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(),ProductResponse.class);

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName()).price(productResponse.getPrice())
                .productId(productResponse.getProductId()).quantity(productResponse.getQuantity()).build();





        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId()).date(order.getOrderDate())
                .orderStatus(order.getOrderStatus()).orderAmount(order.getAmount())
                .quantity(order.getQuantity()).productDetails(productDetails).build();

        return orderResponse;


    }
}

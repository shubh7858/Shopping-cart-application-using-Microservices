package com.shubhampatil.orderservice.Service;

import com.shubhampatil.ProductService.Model.ProductResponse;
import com.shubhampatil.orderservice.Entity.Order;
import com.shubhampatil.orderservice.Model.OrderRequest;
import com.shubhampatil.orderservice.Model.OrderResponse;
import com.shubhampatil.orderservice.Model.PaymentMode;
import com.shubhampatil.orderservice.Repository.OrderRepository;
import com.shubhampatil.orderservice.exception.CustomException;
import com.shubhampatil.orderservice.external.Request.PaymentRequest;
import com.shubhampatil.orderservice.external.client.PaymentService;
import com.shubhampatil.orderservice.external.client.ProductService;
import com.shubhampatil.orderservice.external.response.PaymentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static java.lang.Long.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private RestTemplate restTemplate;


    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @Test
    @DisplayName("Get order - success scenario")
    void test_when_get_order_success(){
        //Mocking
        Order order = getMockOrder();
        when(orderRepository.findById(anyLong() )).thenReturn(Optional.of(getMockOrder()));

        when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(), ProductResponse.class))
                .thenReturn(getMockProductRespone());


        when(restTemplate.getForObject("http://PAYMENT-SERVICE/payment/"+order.getId(),PaymentResponse.class))
                .thenReturn(getMockPaymentRespone());

        //Actual Calling
        OrderResponse orderResponse = orderService.getOrderDetails(11);

        //Verify

        verify(orderRepository,times(1)).findById(anyLong());

        verify(restTemplate,times(1)).getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(), ProductResponse.class);

        verify(restTemplate,times(1)).getForObject("http://PAYMENT-SERVICE/payment/"+order.getId(),PaymentResponse.class);

        //Assertions
        assertNotNull(orderResponse);
        assertEquals(order.getId(),orderResponse.getOrderId());

    }

    private PaymentResponse getMockPaymentRespone() {
        return PaymentResponse.builder()
                .orderId(valueOf(9))
                .orderAmount(valueOf(500)).orderDate(Instant.now()).orderStatus("PAYMENT-SUCCESS").build();
    }

    private ProductResponse getMockProductRespone() {
        return ProductResponse.builder()
                .price(valueOf("300")).productName("VIVO")
                .productId(valueOf(33)).quantity(valueOf(600)).build();
    }

    private Order getMockOrder() {
        return Order.builder()
                .orderStatus("PLACED")
                .amount(valueOf("300")).amount(valueOf("200")).orderDate(Instant.now()).id(11).productId(5)
                .build();
    }

    @DisplayName("Get Orders - Failure Scenario")
    @Test
    void test_when_get_order_NOT_FOUND_then_NOT_FOUND(){

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        CustomException exception = assertThrows(CustomException.class,()->orderService.getOrderDetails(11));

        assertEquals("Order_Not_Found",exception.getErrorCode());
        assertEquals(404,exception.getStatus());

        verify(orderRepository,times(1)).findById(anyLong());

    }

    @DisplayName("Place Order - Success Scenario")
    @Test
    void test_when_place_order_success(){

        Order order = getMockOrder();
        OrderRequest orderRequest = getMockOrderRequest();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productService.reduceQuantity(anyLong(),anyLong())).thenReturn(new ResponseEntity<String>("SUCCESS", HttpStatus.OK));

        when(paymentService.doPayment(any(PaymentRequest.class))).thenReturn(new ResponseEntity<String>("Payment Done",HttpStatus.OK));

         long orderId = orderService.placeOrder(orderRequest);

       verify(orderRepository,times(2)).save(any());
       verify(productService,times(1)).reduceQuantity(anyLong(),anyLong());
       verify(paymentService,times(1)).doPayment(any(PaymentRequest.class));

       assertEquals(order.getId(),orderId);

    }

    @DisplayName("place order - payment failed scenario")
    @Test
    void test_when_place_order_payment_fails_then_order_placed(){

        Order order = getMockOrder();
        OrderRequest orderRequest = getMockOrderRequest();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productService.reduceQuantity(anyLong(),anyLong())).thenReturn(new ResponseEntity<String>("SUCCESS", HttpStatus.OK));

        when(paymentService.doPayment(any(PaymentRequest.class))).thenThrow(new RuntimeException());
        long orderId = orderService.placeOrder(orderRequest);
        verify(orderRepository,times(2)).save(any());
        verify(productService,times(1)).reduceQuantity(anyLong(),anyLong());
        verify(paymentService,times(1)).doPayment(any(PaymentRequest.class));

        assertEquals(order.getId(),orderId);

    }

    private OrderRequest getMockOrderRequest() {

    return OrderRequest.builder()
            .productId(12L).quantity(7L)
            .paymentmode(PaymentMode.OnlinePayment).totalAmount(1299L)
            .build();
    }

}
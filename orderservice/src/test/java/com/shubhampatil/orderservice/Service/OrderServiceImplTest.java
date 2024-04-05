package com.shubhampatil.orderservice.Service;

import com.shubhampatil.orderservice.Repository.OrderRepository;
import com.shubhampatil.orderservice.external.client.PaymentService;
import com.shubhampatil.orderservice.external.client.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
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
    void test_when_order_success(){

        orderService.getOrderDetails(11);

    }


}
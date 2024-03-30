package com.shubhampatil.PaymentService.Service;

import com.shubhampatil.PaymentService.Entity.TransactionDetails;
import com.shubhampatil.PaymentService.Model.PaymentRequest;
import com.shubhampatil.PaymentService.Repository.TransactionDetailsRepository;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.DoubleStream;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public String doPayment(PaymentRequest paymentRequest) {

        log.info("Recording Payment Details {}",paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder().orderId(paymentRequest.getOrderId()).
                                                paymentDate(Instant.now()).paymentStatus("SUCCESS").amount(paymentRequest.getAmount()).referenceNumber(paymentRequest.getReferenceNumber()).
                                                paymentMode(paymentRequest.getPaymentMode().name()).build();

        transactionDetailsRepository.save(transactionDetails);
        log.info("PAYMENT IS SUCCESSFUL WITH TRANSACTION ID {}",transactionDetails.getId());

        return  ("PAYMENT IS SUCCESSFUL");
    }
}

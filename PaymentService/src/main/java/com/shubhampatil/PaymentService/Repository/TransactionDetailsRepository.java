package com.shubhampatil.PaymentService.Repository;

import com.shubhampatil.PaymentService.Entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long> {
}

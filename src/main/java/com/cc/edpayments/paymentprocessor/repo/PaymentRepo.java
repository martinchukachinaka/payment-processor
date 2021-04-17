package com.cc.edpayments.paymentprocessor.repo;

import com.cc.edpayments.paymentprocessor.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

}

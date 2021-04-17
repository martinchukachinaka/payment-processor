package com.cc.edpayments.paymentprocessor.service;

import com.cc.edpayments.paymentprocessor.AppConfig;
import com.cc.edpayments.paymentprocessor.model.Payment;
import com.cc.edpayments.paymentprocessor.repo.PaymentRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;

    private final AppConfig config;

    private final StreamBridge streamBridge;


    @Transactional
    public void savePayment(List<Payment> logs) {
        logs.stream().sorted(Comparator.comparing(Payment::getPaymentDate)).forEach(log -> {
            log.setCurrencyCode(config.getCurrency());
            paymentRepo.save(log);
        });
    }


    @SneakyThrows
    @Async
    public void publishToQueue(List<Payment> logs) {
        logs.stream().sorted(Comparator.comparing(Payment::getPaymentDate)).forEach(payment -> {
            streamBridge.send("payment-out-0", payment);
        });
        log.info("done with pushes");
    }


    public Page<Payment> getLogs(int page, int size) {
        return paymentRepo.findAll(PageRequest.of(page, size));
    }
}

package com.cc.edpayments.paymentprocessor.service;

import com.cc.edpayments.paymentprocessor.AppConfig;
import com.cc.edpayments.paymentprocessor.model.Currency;
import com.cc.edpayments.paymentprocessor.model.Payment;
import com.cc.edpayments.paymentprocessor.repo.PaymentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


class PaymentServiceTest {

    PaymentService paymentService;

    PaymentRepo paymentRepo = mock(PaymentRepo.class);


    @BeforeEach
    void setup() {
        paymentService = new PaymentService(paymentRepo, mock(AppConfig.class), null);
    }


    @Test
    void itShouldSavePaymentGivenValidInputs() {
        List<Payment> requests = createPaymentRequests();
        paymentService.savePayment(requests);
        verify(paymentRepo, times(2)).save(any());
    }


    private List<Payment> createPaymentRequests() {
        List<Payment> requests = new ArrayList<>();
        requests.add(Payment.builder()
                .id("123")
                .amount(500d)
                .paymentDate(LocalDateTime.now())
                .currencyCode(Currency.NGN)
                .payerEmail("cchinaka@gmail.com")
                .recipientEmail("martinchukachinaka@gmaiil.com")
                .build());
        requests.add(Payment.builder()
                .id("456")
                .amount(300d)
                .paymentDate(LocalDateTime.now())
                .currencyCode(Currency.NGN)
                .payerEmail("cchinaka@gmail.com")
                .recipientEmail("martinchukachinaka@gmaiil.com")
                .build());
        return requests;
    }
}

package com.cc.edpayments.paymentprocessor.rest;

import com.cc.edpayments.paymentprocessor.model.Payment;
import com.cc.edpayments.paymentprocessor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class ProcessorController {

    private final PaymentService paymentService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void processPayment(@RequestBody List<Payment> logs) {
        paymentService.savePayment(logs);
        paymentService.publishToQueue(logs);
    }


    @GetMapping("logs")
    public Page<Payment> getLogs(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return paymentService.getLogs(page, size);
    }

}

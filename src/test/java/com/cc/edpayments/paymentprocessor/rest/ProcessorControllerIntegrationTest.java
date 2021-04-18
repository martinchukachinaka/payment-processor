package com.cc.edpayments.paymentprocessor.rest;

import com.cc.edpayments.paymentprocessor.model.Currency;
import com.cc.edpayments.paymentprocessor.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class ProcessorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void itShouldProcessPayment() throws Exception {
        String payload = asJsonString(createPaymentRequests());
        mockMvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON_VALUE).content(payload))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/payments/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
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


    public String asJsonString(List<Payment> logs) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String payload = mapper.writeValueAsString(logs);
            System.out.println("request: " + payload);
            return payload;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

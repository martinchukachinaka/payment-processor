package com.cc.edpayments.paymentprocessor.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Entity
public class Payment {

    @Id
    private String id;

    @Email
    private String payerEmail;

    @NotNull
    private Double amount;

    @Email
    private String recipientEmail;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime paymentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currencyCode;

}

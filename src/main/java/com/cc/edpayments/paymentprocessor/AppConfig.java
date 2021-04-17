package com.cc.edpayments.paymentprocessor;

import com.cc.edpayments.paymentprocessor.model.Currency;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties("app")
public class AppConfig {

    private Currency currency;
}

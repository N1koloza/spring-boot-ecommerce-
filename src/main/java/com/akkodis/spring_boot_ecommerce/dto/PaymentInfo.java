package com.akkodis.spring_boot_ecommerce.dto;

import lombok.Data;

@Data
public class PaymentInfo {
    private int amount; // for example: $12.54 USD => 1254 cents
    private String currency;
}

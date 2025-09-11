package com.akkodis.spring_boot_ecommerce.service;

import com.akkodis.spring_boot_ecommerce.dto.Purchase;
import com.akkodis.spring_boot_ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}

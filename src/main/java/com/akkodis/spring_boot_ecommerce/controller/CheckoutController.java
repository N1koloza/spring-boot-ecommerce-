package com.akkodis.spring_boot_ecommerce.controller;

import com.akkodis.spring_boot_ecommerce.dto.PaymentInfo;
import com.akkodis.spring_boot_ecommerce.dto.Purchase;
import com.akkodis.spring_boot_ecommerce.dto.PurchaseResponse;
import com.akkodis.spring_boot_ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService){
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        PurchaseResponse pr = checkoutService.placeOrder(purchase);
        return pr;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent pi = checkoutService.createPaymentIntent(paymentInfo);
        String paymentStr = pi.toJson();

        return new ResponseEntity<>(paymentStr,HttpStatus.OK);
    }


}

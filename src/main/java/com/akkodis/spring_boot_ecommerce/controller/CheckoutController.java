package com.akkodis.spring_boot_ecommerce.controller;

import com.akkodis.spring_boot_ecommerce.dto.Purchase;
import com.akkodis.spring_boot_ecommerce.dto.PurchaseResponse;
import com.akkodis.spring_boot_ecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
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


}

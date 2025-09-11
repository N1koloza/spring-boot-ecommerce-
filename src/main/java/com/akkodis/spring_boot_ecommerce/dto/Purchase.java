package com.akkodis.spring_boot_ecommerce.dto;

import com.akkodis.spring_boot_ecommerce.entity.*;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}

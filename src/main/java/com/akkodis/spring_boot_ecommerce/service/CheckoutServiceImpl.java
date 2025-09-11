package com.akkodis.spring_boot_ecommerce.service;

import com.akkodis.spring_boot_ecommerce.dao.CustomerRepository;
import com.akkodis.spring_boot_ecommerce.dto.Purchase;
import com.akkodis.spring_boot_ecommerce.dto.PurchaseResponse;
import com.akkodis.spring_boot_ecommerce.entity.Customer;
import com.akkodis.spring_boot_ecommerce.entity.Order;
import com.akkodis.spring_boot_ecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private final CustomerRepository customerRepository;

    @Autowired
    public  CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> order.add(orderItem));
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();

//        String theEmail = customer.getEmail();
//        Customer customerFromDb = customerRepository.findByEmail(theEmail);

        // save to the database
        customer.add(order);
        customerRepository.save(customer);


        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}

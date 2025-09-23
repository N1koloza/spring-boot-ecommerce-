package com.akkodis.spring_boot_ecommerce.service;

import com.akkodis.spring_boot_ecommerce.dao.CustomerRepository;
import com.akkodis.spring_boot_ecommerce.dto.PaymentInfo;
import com.akkodis.spring_boot_ecommerce.dto.Purchase;
import com.akkodis.spring_boot_ecommerce.dto.PurchaseResponse;
import com.akkodis.spring_boot_ecommerce.entity.Customer;
import com.akkodis.spring_boot_ecommerce.entity.Order;
import com.akkodis.spring_boot_ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        //initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
        //System.out.println("secretKey = " + secretKey);
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
        Customer customerFromDB = customerRepository.findByEmail(customer.getEmail());

        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            customer = customerFromDB;
        }

//        String theEmail = customer.getEmail();
//        Customer customerFromDb = customerRepository.findByEmail(theEmail);

        // save to the database
        customer.add(order);
        customerRepository.save(customer);


        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
       List<String> paymentMethodTypes = new ArrayList<>();
       paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();

        params.put("amount", paymentInfo.getAmount());

        params.put("currency", paymentInfo.getCurrency());

        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}

package com.example.hexagonal.domain.wallet;

import com.example.hexagonal.domain.payment_method.PaymentMethod;
import org.springframework.data.mongodb.core.mapping.Document;

public class Wallet {
    private PaymentMethod paymentMethod;
}

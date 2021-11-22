package com.ourclassroom.ourclassroom.service.transaction.service;

import com.paypal.api.payments.Payment;

import java.util.Map;

public interface PaypalService {

    Payment createPayment(String orderNo);

    Payment executePayment(String paymentId, String payerId);

}

package com.ourclassroom.ourclassroom.service.transaction.service.impl;

import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.transaction.entity.Order;
import com.ourclassroom.ourclassroom.service.transaction.service.OrderService;
import com.ourclassroom.ourclassroom.service.transaction.service.PaypalService;
import com.ourclassroom.ourclassroom.service.transaction.util.PaypalConfig;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PaypalServiceImpl implements PaypalService {
    @Autowired
    private APIContext apiContext;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaypalConfig paypalConfig;

    @Override
    public Payment createPayment(String orderNo) {
        try {
            // get the order by order number
            Order order = orderService.getOrderByOrderNo(orderNo);

            Amount amount = new Amount();
            amount.setCurrency("USD");
            amount.setTotal(String.format("%.2f", order.getTotalAmount().setScale(2, RoundingMode.HALF_UP).doubleValue()));

            Transaction transaction = new Transaction();
            transaction.setDescription(order.getOrderNo());
            transaction.setAmount(amount);

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Item item = new Item();
            item.setCurrency("USD");
            item.setName(order.getOrderNo());
            item.setQuantity("1");
            item.setPrice(String.format("%.2f", order.getTotalAmount().setScale(2, RoundingMode.HALF_UP).doubleValue()));

            ItemList itemList = new ItemList();
            List<Item> items = new ArrayList<>();

            items.add(item);
            itemList.setItems(items);
            transaction.setItemList(itemList);

            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");
            PayerInfo payerInfo = new PayerInfo();
            payerInfo.setPayerId(order.getMemberId());
            payerInfo.setFirstName(order.getUsername());
            payerInfo.setEmail(order.getEmail());
            payer.setPayerInfo(payerInfo);

            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(transactions);

            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(paypalConfig.getCancelUrl());
            redirectUrls.setReturnUrl(paypalConfig.getSuccessUrl());

            payment.setRedirectUrls(redirectUrls);
            apiContext.setMaskRequestId(true);
            return payment.create(apiContext);

        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.PAY_UNIFIEDORDER_ERROR);
        }
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) {
        try {
            //Payment payment = Payment.get(apiContext, paymentId);
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            return payment.execute(apiContext, paymentExecute);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.PAY_UNIFIEDORDER_ERROR);
        }
    }

}

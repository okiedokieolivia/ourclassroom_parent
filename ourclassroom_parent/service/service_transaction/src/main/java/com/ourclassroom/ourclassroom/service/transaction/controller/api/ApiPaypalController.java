package com.ourclassroom.ourclassroom.service.transaction.controller.api;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.transaction.entity.Order;
import com.ourclassroom.ourclassroom.service.transaction.service.OrderService;
import com.ourclassroom.ourclassroom.service.transaction.service.PaypalService;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction/paypal")
@Api(tags = "Pay by paypal")
//@CrossOrigin
@Slf4j
public class ApiPaypalController {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private OrderService orderService;

    @PostMapping("create-payment/{orderNo}")
    public R createPayment(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("create the payment...");
        Payment payment = paypalService.createPayment(orderNo);
        System.out.println("creating the payment: ");
        System.out.println(payment.toJSON());
        for (Links link: payment.getLinks()) {
            System.out.println(link.getRel());
            System.out.println(link.getRel().equals("approval_url"));
            if (link.getRel().equals("approval_url")) {
                System.out.println(link.getHref());
                return R.ok().data("link", link.getHref());
            }
        }
         return R.error().message("Fail to pay");
    }

    @GetMapping(value = "pay/cancel")
    public void cancelPay(HttpServletResponse response) {
        try {
            response.sendRedirect("http://localhost:3000");
        } catch (IOException e) {
            ExceptionUtils.getMessage(e);
            throw new OurclassroomException(ResultCodeEnum.UNKNOWN_REASON);
        }
    }

    @GetMapping(value = "pay/success")
    public void successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletResponse response) {
        Payment payment = paypalService.executePayment(paymentId, payerId);
        Transaction transaction = payment.getTransactions().get(0);
        Item item = transaction.getItemList().getItems().get(0);
        String orderNo = item.getName();
        Order order = orderService.getOrderByOrderNo(orderNo);

        if (order != null && payment.getState().equals("approved")) {
            if (order.getStatus() == 0) {
                Map<String, String> map = new HashMap<>();
                map.put("orderNo", orderNo);
                map.put("totalAmount", item.getPrice());
                map.put("transactionId", paymentId);
                map.put("status", "1");
                map.put("attr", payment.toJSON());
                orderService.updateOrderStatus(map);
            }
        }

        try {
            response.sendRedirect("http://localhost:3000/pay/" + orderNo);
        } catch (IOException e) {
            ExceptionUtils.getMessage(e);
            throw new OurclassroomException(ResultCodeEnum.PAY_UNIFIEDORDER_ERROR);
        }
    }
}

package com.ourclassroom.ourclassroom.service.transaction.service;

import com.ourclassroom.ourclassroom.service.transaction.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);

    Order getByOrderId(String orderId, String memberId);

    Boolean isBuyCourseId(String courseId, String memberId);

    List<Order> selectByMemberId(String memberId);

    boolean removeById(String orderId, String memberId);

    Order getOrderByOrderNo(String orderNo);

    void updateOrderStatus(Map<String, String> map);

    boolean queryPaymentStatus(String orderNo);

    Order getOrderByOrderNo(String orderNo, String memberId);
}

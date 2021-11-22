package com.ourclassroom.ourclassroom.service.transaction.controller.api;


import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.JwtInfo;
import com.ourclassroom.ourclassroom.base.util.JwtUtils;
import com.ourclassroom.ourclassroom.service.transaction.entity.Order;
import com.ourclassroom.ourclassroom.service.transaction.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/transaction/order")
@Api(tags = "order management")
//@CrossOrigin
@Slf4j
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("New order")
    @PostMapping("auth/save/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.saveOrder(courseId, jwtInfo.getId());
        return R.ok().data("orderId", orderId);
    }

    @ApiOperation("Get order by id")
    @GetMapping("auth/get/{orderId}")
    public R get(@PathVariable String orderId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Order order = orderService.getByOrderId(orderId, jwtInfo.getId());
        return R.ok().data("item", order);
    }

    @ApiOperation( "Check if the user already bought this course")
    @GetMapping("auth/is-buy/{courseId}")
    public R isBuyByCourseId(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBuy = orderService.isBuyCourseId(courseId, jwtInfo.getId());
        return R.ok().data("isBuy", isBuy);
    }

    @ApiOperation(value = "Get the order list of current user")
    @GetMapping("auth/list")
    public R list(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<Order> list = orderService.selectByMemberId(jwtInfo.getId());
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "Delete order")
    @DeleteMapping("auth/remove/{orderId}")
    public R remove(@PathVariable String orderId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = orderService.removeById(orderId, jwtInfo.getId());
        if (result) {
            return R.ok().message("Successfully deleted");
        } else {
            return R.error().message("Data not found");
        }
    }

    @GetMapping("auth/get-by-order-number/{orderNo}")
    public R getByOrderNo(@PathVariable String orderNo, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Order order = orderService.getOrderByOrderNo(orderNo, jwtInfo.getId());
        return R.ok().data("item", order);
    }
}


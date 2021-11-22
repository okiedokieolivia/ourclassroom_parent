package com.ourclassroom.ourclassroom.service.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.service.base.dto.CourseDto;
import com.ourclassroom.ourclassroom.service.base.dto.MemberDto;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.transaction.entity.Log;
import com.ourclassroom.ourclassroom.service.transaction.entity.Order;
import com.ourclassroom.ourclassroom.service.transaction.feign.EduCourseService;
import com.ourclassroom.ourclassroom.service.transaction.feign.UcenterMemberService;
import com.ourclassroom.ourclassroom.service.transaction.mapper.LogMapper;
import com.ourclassroom.ourclassroom.service.transaction.mapper.OrderMapper;
import com.ourclassroom.ourclassroom.service.transaction.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ourclassroom.ourclassroom.service.transaction.util.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Autowired
    private LogMapper logMapper;


    @Override
    public String saveOrder(String courseId, String memberId) {
        // check if the order already exists
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        Order order = baseMapper.selectOne(queryWrapper);

        if (order != null) {
            return order.getId();
        }

        CourseDto courseDto = eduCourseService.getCourseDtoById(courseId);
        if (courseDto == null) {
            throw new OurclassroomException(ResultCodeEnum.PARAM_ERROR);
        }

        MemberDto memberDto = ucenterMemberService.getMemberDtoByMemberId(memberId);
        if (memberDto == null) {
            throw new OurclassroomException(ResultCodeEnum.PARAM_ERROR);
        }

        Order newOrder = new Order();

        newOrder.setOrderNo(OrderNoUtils.getOrderNo());
        newOrder.setCourseId(courseId);
        newOrder.setCourseTitle(courseDto.getTitle());
        newOrder.setCourseCover(courseDto.getCover());
        newOrder.setTeacherName(courseDto.getTeacherName());
        newOrder.setTotalAmount(courseDto.getPrice());

        newOrder.setMemberId(memberId);
        newOrder.setEmail(memberDto.getEmail());
        newOrder.setUsername(memberDto.getUsername());

        newOrder.setStatus(0);

        baseMapper.insert(newOrder);

        return newOrder.getId();
    }

    @Override
    public Order getByOrderId(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        queryWrapper.eq("member_id", memberId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean isBuyCourseId(String courseId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("status", 1);
        Integer count = baseMapper.selectCount(queryWrapper);

        return count > 0;
    }

    @Override
    public List<Order> selectByMemberId(String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("member_id", memberId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean removeById(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        queryWrapper.eq("member_id", memberId);
        return this.remove(queryWrapper);
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Map<String, String> map) {
        String orderNo = map.get("orderNo");
        Order order = this.getOrderByOrderNo(orderNo);
        order.setStatus(1);
        baseMapper.updateById(order);

        Log log = new Log();
        log.setOrderNo(orderNo);
        log.setStatus("1");
        log.setAttr(map.get("attr"));
        log.setDeleted(false);
        log.setTransactionId(map.get("transactionId"));
        log.setTotalAmount(order.getTotalAmount().toBigInteger().longValue());
        log.setTransactionTime(new Date());

        logMapper.insert(log);

        // update the course sale
        eduCourseService.updateSalesById(order.getCourseId());
    }

    @Override
    public boolean queryPaymentStatus(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = baseMapper.selectOne(queryWrapper);
        return order.getStatus() == 1;
    }

    @Override
    public Order getOrderByOrderNo(String orderNo, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        queryWrapper.eq("member_id", memberId);
        Order order = baseMapper.selectOne(queryWrapper);
        return order;
    }
}

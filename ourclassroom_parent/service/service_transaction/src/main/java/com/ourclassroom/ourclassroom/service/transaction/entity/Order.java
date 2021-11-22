package com.ourclassroom.ourclassroom.service.transaction.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.ourclassroom.ourclassroom.service.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("transaction_order")
@ApiModel(value="Order object", description="order")
public class Order extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "order number")
    private String orderNo;

    @ApiModelProperty(value = "course id")
    private String courseId;

    @ApiModelProperty(value = "course title")
    private String courseTitle;

    @ApiModelProperty(value = "course cover")
    private String courseCover;

    @ApiModelProperty(value = "instructor name")
    private String teacherName;

    @ApiModelProperty(value = "member id")
    private String memberId;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "total amount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "order status(0: no paid 1: paid)")
    private Integer status;

    @ApiModelProperty(value = "logical delete 1(deleted)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}

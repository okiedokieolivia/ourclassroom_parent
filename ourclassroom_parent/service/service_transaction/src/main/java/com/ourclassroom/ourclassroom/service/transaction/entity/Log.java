package com.ourclassroom.ourclassroom.service.transaction.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ourclassroom.ourclassroom.service.base.model.BaseEntity;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("transaction_log")
@ApiModel(value="Log object", description="transaction log")
public class Log extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "order number")
    private String orderNo;

    @ApiModelProperty(value = "the time of the transaction")
    private Date transactionTime;

    @ApiModelProperty(value = "total amount")
    private Long totalAmount;

    @ApiModelProperty(value = "transaction id")
    private String transactionId;

    @ApiModelProperty(value = "the status of this transaction")
    private String status;

    @ApiModelProperty(value = "other attributes")
    private String attr;

    @ApiModelProperty(value = "logical delete 1(deleted)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}

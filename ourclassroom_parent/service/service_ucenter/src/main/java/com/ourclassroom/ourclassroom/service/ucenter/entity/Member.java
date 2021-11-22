package com.ourclassroom.ourclassroom.service.ucenter.entity;

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
@TableName("ucenter_member")
@ApiModel(value="Member", description="member form")
public class Member extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "gender 1 male，2 female, 3 unknow")
    private Integer gender;

    @ApiModelProperty(value = "age")
    private Integer age;

    @ApiModelProperty(value = "avatar")
    private String avatar;

    @ApiModelProperty(value = "biography")
    private String bio;

    @ApiModelProperty(value = "is disabled 1（true）disabled")
    @TableField("is_disabled")
    private Boolean disabled;

    @ApiModelProperty(value = "logical deletion 1（true）deleted")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}

package com.ourclassroom.ourclassroom.service.edu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("edu_teacher")
@ApiModel(value="Teacher ", description="teacher")
public class Teacher extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "introduction")
    private String intro;

    @ApiModelProperty(value = "career")
    private String career;

    @ApiModelProperty(value = "level")
    private Integer level;

    @ApiModelProperty(value = "avatar")
    private String avatar;

    @ApiModelProperty(value = "sort")
    private Integer sort;

    @ApiModelProperty(value = "join date")
    @JsonFormat(timezone = "America/New_York", pattern = "yyyy-MM-dd")
    private Date joinDate;

    @ApiModelProperty(value = "logical delete")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}

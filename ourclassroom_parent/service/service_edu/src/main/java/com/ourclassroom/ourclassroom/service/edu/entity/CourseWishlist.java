package com.ourclassroom.ourclassroom.service.edu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.ourclassroom.ourclassroom.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_course_wishlist")
@ApiModel(value="CourseWishlist", description="course wishlist")
public class CourseWishlist extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "teacher ID")
    private String courseId;

    @ApiModelProperty(value = "subject ID")
    private String memberId;


}

package com.ourclassroom.ourclassroom.service.edu.entity;

import java.math.BigDecimal;
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
@TableName("edu_course")
@ApiModel(value="Course ", description="课程")
public class Course extends BaseEntity {

    private static final long serialVersionUID=1L;

    public static final String COURSE_DRAFT = "Draft";
    public static final String COURSE_NORMAL = "Normal";

    @ApiModelProperty(value = "teacher ID")
    private String teacherId;

    @ApiModelProperty(value = "subject ID")
    private String subjectId;

    @ApiModelProperty(value = "primary subject ID")
    private String subjectParentId;

    @ApiModelProperty(value = "course title")
    private String title;

    @ApiModelProperty(value = "price")
    private BigDecimal price;

    @ApiModelProperty(value = "total length")
    private Integer lessonNum;

    @ApiModelProperty(value = "cover")
    private String cover;

    @ApiModelProperty(value = "sales")
    private Long buyCount;

    @ApiModelProperty(value = "views")
    private Long viewCount;

    @ApiModelProperty(value = "optimistic lock")
    private Long version;

    @ApiModelProperty(value = "course status")
    private String status;


}

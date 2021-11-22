package com.ourclassroom.ourclassroom.service.edu.entity.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel("Course details")
@Data
public class CourseInfoForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Course Id")
    private String id;

    @ApiModelProperty(value = "Instructor Id")
    private String teacherId;

    @ApiModelProperty(value = "Secondary Subject Id")
    private String subjectId;

    @ApiModelProperty(value = "Primary Subject Id")
    private String subjectParentId;

    @ApiModelProperty(value = "Course Title")
    private String title;

    @ApiModelProperty(value = "Price")
    private BigDecimal price;

    @ApiModelProperty(value = "Total hours")
    private Integer lessonNum;

    @ApiModelProperty(value = "Cover")
    private String cover;

    @ApiModelProperty(value = "Description")
    private String description;
}

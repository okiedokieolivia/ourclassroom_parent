package com.ourclassroom.ourclassroom.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseWishlistVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String courseId;
    private String title;
    private BigDecimal price;
    private Integer lessonNum;
    private String cover;
    private String teacherName;
}

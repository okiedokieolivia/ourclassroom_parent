package com.ourclassroom.ourclassroom.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WebCourseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private BigDecimal price;
    private Integer lessonNum;
    private String cover;
    private Long buyCount;
    private Long viewCount;
    private String description;
    private String teacherId;
    private String teacherName;
    private String intro;
    private String avatar;

    private String primarySubjectId;
    private String primarySubject;
    private String secondarySubjectId;
    private String secondarySubject;

    public WebCourseVo() {
    }
}

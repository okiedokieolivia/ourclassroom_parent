package com.ourclassroom.ourclassroom.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String teacherId;
    private String subjectId;
    private String subjectParentId;

}

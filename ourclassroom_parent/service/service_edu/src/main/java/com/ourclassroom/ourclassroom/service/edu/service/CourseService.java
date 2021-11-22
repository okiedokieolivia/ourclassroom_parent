package com.ourclassroom.ourclassroom.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ourclassroom.ourclassroom.service.base.dto.CourseDto;
import com.ourclassroom.ourclassroom.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ourclassroom.ourclassroom.service.edu.entity.form.CourseInfoForm;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.*;

import java.util.List;

public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo);

    boolean removeCoverById(String id);

    boolean removeCourseById(String id);

    CoursePublishVo getCoursePublishVoBtId(String id);

    boolean publishCourseById(String id);

    boolean unpublishCourseById(String id);

    List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo);

    WebCourseVo selectWebCourseVoById(String id);

    List<Course> selectPopularCourse();

    CourseDto getCourseDtoById(String courseId);

    void updateSalesById(String id);
}

package com.ourclassroom.ourclassroom.service.edu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.CoursePublishVo;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.CourseVo;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.WebCourseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseVo> selectPageByCourseQueryVo(Page<CourseVo> pageParam,
                                             @Param(Constants.WRAPPER)
                                             QueryWrapper<Course> queryWrapper);

    CoursePublishVo selectCoursePublishVoById(String id);

    WebCourseVo selectWebCourseVoById(String courseId);
}

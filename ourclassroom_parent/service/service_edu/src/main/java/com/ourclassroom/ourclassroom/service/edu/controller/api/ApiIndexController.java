package com.ourclassroom.ourclassroom.service.edu.controller.api;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.Course;
import com.ourclassroom.ourclassroom.service.edu.entity.Teacher;
import com.ourclassroom.ourclassroom.service.edu.service.CourseService;
import com.ourclassroom.ourclassroom.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

////@CrossOrigin
@Api(tags="Homepage")
@RestController
@RequestMapping("/api/edu/index")
public class ApiIndexController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("Get the instructors and courses for homepage")
    @GetMapping
    public R index() {
        List<Course> courseList = courseService.selectPopularCourse();

        List<Teacher> teacherList = teacherService.selectPopularTeacher();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}

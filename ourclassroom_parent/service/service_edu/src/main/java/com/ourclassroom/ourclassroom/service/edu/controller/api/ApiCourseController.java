package com.ourclassroom.ourclassroom.service.edu.controller.api;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.Course;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.ChapterVo;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.WebCourseQueryVo;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.WebCourseVo;
import com.ourclassroom.ourclassroom.service.edu.service.ChapterService;
import com.ourclassroom.ourclassroom.service.edu.service.CourseService;
import com.ourclassroom.ourclassroom.service.base.dto.CourseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

////@CrossOrigin
@Api(tags="Courses")
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("Courses")
    @GetMapping("list")
    public R pageList(
            @ApiParam(value = "Query object", required = true)
            WebCourseQueryVo webCourseQueryVo) {
        List<Course> courseList = courseService.webSelectList(webCourseQueryVo);

        return R.ok().data("courseList", courseList);
    }

    @ApiOperation("Get course by id")
    @GetMapping("get/{courseId}")
    public R getById(
            @ApiParam(value = "course Id", required = true)
            @PathVariable String courseId) {
        // get course info
        WebCourseVo webCourseVo = courseService.selectWebCourseVoById(courseId);

        // get the content of the course
        List<ChapterVo> chapterVos = chapterService.nestedList(courseId);

        return R.ok().data("course", webCourseVo).data("chapterVoList", chapterVos);

    }

    @ApiOperation("Get course info by course id")
    @GetMapping("inner/get-course-dto/{courseId}")
    public CourseDto getCourseDtoById(
            @ApiParam(value = "course ID", required = true)
            @PathVariable String courseId){
        CourseDto courseDto = courseService.getCourseDtoById(courseId);
        return courseDto;
    }

    @ApiOperation("Update the sales")
    @GetMapping("inner/update-sales/{id}")
    public R updateSalesById(
            @ApiParam(value = "course id", required = true)
            @PathVariable String id){
        courseService.updateSalesById(id);
        return R.ok();
    }


}

package com.ourclassroom.ourclassroom.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.form.CourseInfoForm;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.CoursePublishVo;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.CourseQueryVo;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.CourseVo;
import com.ourclassroom.ourclassroom.service.edu.service.CourseService;
import com.ourclassroom.ourclassroom.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

////@CrossOrigin
@Api(tags = "Course Management")
@RestController
@RequestMapping("/admin/edu/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private VideoService videoService;

    @ApiOperation("Start a new course")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "Course Information", required = true)
            @RequestBody CourseInfoForm courseInfoForm
            ) {
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId).message("Saved Successfully");
    }

    @ApiOperation("Get course by id")
    @GetMapping("course-info/{id}")
    public R getById(
        @ApiParam(value = "Course Id", required = true)
        @PathVariable String id
    ) {
        CourseInfoForm form = courseService.getCourseInfoById(id);
        if (form != null) {
            return R.ok().data("item", form);
        } else {
            return R.ok().message("No such course");
        }
    }

    @ApiOperation("Update course details")
    @PutMapping("update-course-info")
    public R updateById(
            @ApiParam(value = "Course Id", required = true)
            @RequestBody CourseInfoForm courseInfoForm
    ) {
        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok().message("Updated Successfully");
    }


    @ApiOperation("page break")
    @GetMapping("list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "current page", required = true)
            @PathVariable Long page,

            @ApiParam(value = "number of records on current page", required = true)
            @PathVariable Long limit,

            @ApiParam(value = "query object")
                    CourseQueryVo courseQueryVo){

        IPage<CourseVo> pageModel = courseService.selectPage(page, limit, courseQueryVo);
        List<CourseVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("Delete course by id")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "course id ", required = true)
            @PathVariable String id){

        //delete all videos of this course: VOD
        videoService.removeMediaVideoByCourseId(id);

        // delete cover：OSS
        courseService.removeCoverById(id);

        // delete the course
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return R.ok().message("Deleted Successfully");
        } else {
            return R.error().message("No such record");
        }
    }

    @ApiOperation("Get course publish vo")
    @GetMapping("course-publish/{id}")
    public R getCoursePublishVoById(
            @ApiParam(value = "course Id", required = true)
            @PathVariable String id
    ) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoBtId(id);
        if (coursePublishVo != null) {
            return R.ok().data("item", coursePublishVo);
        } else {
            return R.error().message("No Such Record");
        }

    }

    @ApiOperation("Publish course")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(value = "course id", required = true)
            @PathVariable String id
    ) {
        boolean result = courseService.publishCourseById(id);
        if (result) {
            return R.ok().message("Published Successfully");
        } else {
            return R.error().message("No Such Record");
        }
    }

    @ApiOperation("Unpublish course")
    @PutMapping("unpublish-course/{id}")
    public R unpublishCourseById(
            @ApiParam(value = "course id", required = true)
            @PathVariable String id
    ) {
        boolean result = courseService.unpublishCourseById(id);
        if (result) {
            return R.ok().message("Unpublished");
        } else {
            return R.error().message("No Such Record");
        }
    }

}


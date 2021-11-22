package com.ourclassroom.ourclassroom.service.edu.controller.api;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.Teacher;
import com.ourclassroom.ourclassroom.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

////@CrossOrigin
@Api(tags="Instructors")
@RestController
@RequestMapping("/api/edu/teacher")
public class ApiTeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("list")
    @ApiOperation("List all instructors")
    public R listAll() {
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list).message("Successfully get the instructor list!");
    }

    @ApiOperation(value = "Get instructor info with given id")
    @GetMapping("get/{id}")
    public R get(
            @ApiParam(value = "Instructor ID", required = true)
            @PathVariable String id) {
        Map<String, Object> map = teacherService.selectTeacherInfoById(id);
        return R.ok().data(map);
    }
}

package com.ourclassroom.ourclassroom.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.Teacher;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.TeacherQueryVo;
import com.ourclassroom.ourclassroom.service.edu.feign.OssFileService;
import com.ourclassroom.ourclassroom.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

////@CrossOrigin
@Api(tags = "Instructor management")
@RestController
@RequestMapping("admin/edu/teacher")
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private OssFileService ossFileService;

    @GetMapping("list")
    @ApiOperation("List all instructors")
    public R listAll(){
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list);
    }

    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "Remove the instructor with given ID", notes = "logical delete")
    public R removeById(@ApiParam("The ID of the instructor") @PathVariable String id) {
        // delete the avatar
        teacherService.removeAvatarById(id);
        // delete the instructor record
        boolean result = teacherService.removeById(id);
        if (result) {
            return R.ok().message("Successfully deleted");
        } else {
            return R.error().message("Record not found");
        }
    }

    @ApiOperation("Page break")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "Current page", required = true) @PathVariable Long page,
                      @ApiParam(value = "Number of records each page", required = true) @PathVariable Long limit,
                      @ApiParam("Instructor query object") TeacherQueryVo teacherQueryVo) {
        Page<Teacher> instructorPage = new Page<>(page, limit);
        IPage<Teacher> pageModel = teacherService.selectPage(instructorPage, teacherQueryVo);
        List<Teacher> records = pageModel.getRecords();
        long totalRecords = pageModel.getTotal();
        return R.ok().data("total", totalRecords).data("rows", records);
    }

    @ApiOperation("Add a new instructor into database")
    @PostMapping("save")

    public R save(@ApiParam("Instructor") @RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return R.ok().message("Successfully saved");
    }

    @ApiOperation("Update the information of an instructor")
    @PutMapping("update")
    public R updateById(@ApiParam("Instructor") @RequestBody Teacher teacher) {
        boolean result = teacherService.updateById(teacher);
        if (result) {
            return R.ok().message("Successfully updated");
        } else {
            return R.error().message("Failed to update. Try again!");
        }

    }

    @ApiOperation("Get instructor by id")
    @GetMapping("get/{id}")
    public R getById(@ApiParam("Instructor id") @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return R.ok().data("item", teacher);
        }
        return R.error().message("Record not found");
    }

    @ApiOperation(value = "Delete instructor records in bulk")
    @DeleteMapping("batch-remove")
    public R removeByIds(@ApiParam(value = "List of instructor Ids", required = true) @RequestBody  List<String> idList) {
        boolean result = teacherService.removeByIds(idList);
        if(result){
            return R.ok().message("Successfully deleted");
        }else{
            return R.error().message("No records found");
        }
    }

    @ApiOperation(value = "Select instructors by keyword")
    @GetMapping("list/name/{keyword}")
    public R selectNameListByKeyword(@ApiParam(value = "keyword", required = true) @PathVariable String keyword) {
        List<Map<String, Object>> nameList = teacherService.selectNameList(keyword);
        return R.ok().data("nameList", nameList);
    }
}


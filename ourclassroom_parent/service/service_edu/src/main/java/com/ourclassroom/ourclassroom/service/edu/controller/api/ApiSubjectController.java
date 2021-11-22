package com.ourclassroom.ourclassroom.service.edu.controller.api;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.SubjectVo;
import com.ourclassroom.ourclassroom.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

////@CrossOrigin
@Api(tags="Subject Categories")
@RestController
@RequestMapping("/api/edu/subject")
public class ApiSubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("Get the nested subject list")
    @GetMapping("nested-list")
    public R nestedList() {
        List<SubjectVo> nestedList = subjectService.getNestedList();
        return R.ok().data("items", nestedList);
    }

}

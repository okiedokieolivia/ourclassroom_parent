package com.ourclassroom.ourclassroom.service.edu.controller.admin;


import com.alibaba.excel.support.ExcelTypeEnum;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.SubjectVo;
import com.ourclassroom.ourclassroom.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
////@CrossOrigin
@Api(tags = "Subject Management")
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("Read Excel file uploaded")
    @PostMapping("import")
    public R batchImport(
            @ApiParam(value = "Excel file", required = true)
            @RequestParam("file") MultipartFile file) {
        //todo: support 2 types of excels
        System.out.println(file.getOriginalFilename());
        try {
            String originalName = file.getOriginalFilename();
            String suffix = originalName.substring(originalName.lastIndexOf('.'));
            System.out.println(suffix);
            if (".xls".equalsIgnoreCase(suffix)) {
                subjectService.batchImport(file.getInputStream(), ExcelTypeEnum.XLSX);
            } else if (".xlsx".equalsIgnoreCase(suffix)) {
                subjectService.batchImport(file.getInputStream(), ExcelTypeEnum.XLSX);
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
        return R.ok().message("File Uploaded Successfully");
    }

    @ApiOperation("Nested list for subjects")
    @GetMapping("nested-list")
    public R nestedList() {
        List<SubjectVo> subjectVoList = subjectService.getNestedList();
        return R.ok().data("items", subjectVoList);
    }
}


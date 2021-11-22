package com.ourclassroom.ourclassroom.service.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSubjectData {
    @ExcelProperty(value = "PrimaryCategory")
    private String primary;
    @ExcelProperty(value = "SecondaryCategory")
    private String secondary;


}

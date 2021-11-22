package com.ourclassroom.ourclassroom.service.edu.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ourclassroom.ourclassroom.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ourclassroom.ourclassroom.service.edu.entity.excel.ExcelSubjectData;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.SubjectVo;
import com.ourclassroom.ourclassroom.service.edu.listener.ExcelSubjectDataListener;

import java.io.InputStream;
import java.util.List;


public interface SubjectService extends IService<Subject> {

    void batchImport(InputStream inputStream, ExcelTypeEnum type);

    List<SubjectVo> getNestedList();
}

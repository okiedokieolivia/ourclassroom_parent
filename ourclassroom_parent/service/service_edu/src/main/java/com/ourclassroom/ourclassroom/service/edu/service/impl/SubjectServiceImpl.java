package com.ourclassroom.ourclassroom.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ourclassroom.ourclassroom.service.edu.entity.Subject;
import com.ourclassroom.ourclassroom.service.edu.entity.excel.ExcelSubjectData;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.SubjectVo;
import com.ourclassroom.ourclassroom.service.edu.listener.ExcelSubjectDataListener;
import com.ourclassroom.ourclassroom.service.edu.mapper.SubjectMapper;
import com.ourclassroom.ourclassroom.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void batchImport(InputStream inputStream, ExcelTypeEnum type) {
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectDataListener(baseMapper)).excelType(type).sheet().doRead();
    }

    @Override
    public List<SubjectVo> getNestedList() {
        return baseMapper.selectNestedListByParentId("0");
    }
}

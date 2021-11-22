package com.ourclassroom.ourclassroom.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourclassroom.ourclassroom.service.edu.entity.Subject;
import com.ourclassroom.ourclassroom.service.edu.entity.excel.ExcelSubjectData;
import com.ourclassroom.ourclassroom.service.edu.mapper.SubjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {
    private SubjectMapper subjectMapper;

    /**
     * loop and read each row
     * @param excelSubjectData
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {

        log.info("reading the record");
        String primary = excelSubjectData.getPrimary();
        String secondary = excelSubjectData.getSecondary();
        log.info("primary category: " + primary);
        log.info("secondary category: " + secondary);

        // store the record
        // store the primary category
        // check if the primary category already exists
        Subject primaryCategory = this.getByTitle(primary);
        String parentId = null;
        // if it is the new primary category
        if (primaryCategory == null) {
            Subject subject = new Subject();
            subject.setParentId("0");
            subject.setTitle(primary);
            subjectMapper.insert(subject);
            parentId = subject.getId();
        } else {
            parentId = primaryCategory.getId();
        }

        // store the secondary category
        Subject secondaryCategory = this.getSecondaryByTitle(secondary, parentId);
        if (secondaryCategory == null) {
            Subject subject = new Subject();
            subject.setParentId(parentId);
            subject.setTitle(secondary);
            subjectMapper.insert(subject);
        }

    }

    /**
     * this method is called after the whole file is done reading
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("finish!");
    }

    /**
     * helper method: determine if it is the primary category
     * @param title
     * @return
     */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        return subjectMapper.selectOne(queryWrapper);
    }


    private Subject getSecondaryByTitle(String title, String parentId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return subjectMapper.selectOne(queryWrapper);
    }

}

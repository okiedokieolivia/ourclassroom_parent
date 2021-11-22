package com.ourclassroom.ourclassroom.service.edu.mapper;

import com.ourclassroom.ourclassroom.service.edu.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.SubjectVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectMapper extends BaseMapper<Subject> {

    List<SubjectVo> selectNestedListByParentId(String parentId);
}

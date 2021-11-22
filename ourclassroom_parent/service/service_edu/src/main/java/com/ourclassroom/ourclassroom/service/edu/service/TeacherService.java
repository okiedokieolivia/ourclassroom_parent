package com.ourclassroom.ourclassroom.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.TeacherQueryVo;

import java.util.List;
import java.util.Map;


public interface TeacherService extends IService<Teacher> {

    IPage<Teacher> selectPage(Page<Teacher> instructorPage, TeacherQueryVo teacherQueryVo);

    List<Map<String, Object>> selectNameList(String keyword);

    boolean removeAvatarById(String id);

    Map<String, Object> selectTeacherInfoById(String id);

    List<Teacher> selectPopularTeacher();
}


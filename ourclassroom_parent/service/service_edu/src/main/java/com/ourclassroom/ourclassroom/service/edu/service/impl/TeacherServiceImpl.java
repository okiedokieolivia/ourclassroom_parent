package com.ourclassroom.ourclassroom.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.Course;
import com.ourclassroom.ourclassroom.service.edu.entity.Teacher;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.TeacherQueryVo;
import com.ourclassroom.ourclassroom.service.edu.feign.OssFileService;
import com.ourclassroom.ourclassroom.service.edu.mapper.CourseMapper;
import com.ourclassroom.ourclassroom.service.edu.mapper.TeacherMapper;
import com.ourclassroom.ourclassroom.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public IPage<Teacher> selectPage(Page<Teacher> instructorPage, TeacherQueryVo teacherQueryVo) {
        // return the query result
        // 1. order by the `sort` filed in the table
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");

        // 2. query by given condition
        // 2.1 directly get the result
       if (teacherQueryVo == null) {
           return baseMapper.selectPage(instructorPage, wrapper);
       }
       // 2.2 query by conditions
       String name = teacherQueryVo.getName();
       Integer level = teacherQueryVo.getLevel();
       String joinDateBegin = teacherQueryVo.getJoinDateBegin();
       String joinDateEnd = teacherQueryVo.getJoinDateEnd();

       if (!StringUtils.isEmpty(name)) {
           wrapper.likeRight("name", name);
       }

       if (level != null) {
           wrapper.eq("level", level);
       }

       if (!StringUtils.isEmpty(joinDateBegin)) {
           wrapper.ge("join_date", joinDateBegin);
       }

       if (!StringUtils.isEmpty(joinDateEnd)) {
           wrapper.le("join_date", joinDateEnd);
       }

       return baseMapper.selectPage(instructorPage, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectNameList(String keyword) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.select("name");
        wrapper.likeRight("name", keyword);
        List<Map<String, Object>> maps = baseMapper.selectMaps(wrapper);
        return maps;
    }

    @Override
    public boolean removeAvatarById(String id) {
        // get the url of avatar of instructor with given id
        Teacher teacher = baseMapper.selectById(id);
        if (teacher != null) {
            String url = teacher.getAvatar();
            if (!StringUtils.isEmpty(url)) {
                R r = ossFileService.removeFile(url);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> selectTeacherInfoById(String id) {
        Teacher teacher = baseMapper.selectById(id);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id", id);
        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        map.put("courseList", courseList);

        return map;
    }

    @Override
    public List<Teacher> selectPopularTeacher() {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        queryWrapper.last("limit 4");

        return baseMapper.selectList(queryWrapper);
    }
}

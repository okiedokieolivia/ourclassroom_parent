package com.ourclassroom.ourclassroom.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.base.dto.CourseDto;
import com.ourclassroom.ourclassroom.service.edu.entity.*;
import com.ourclassroom.ourclassroom.service.edu.entity.form.CourseInfoForm;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.*;
import com.ourclassroom.ourclassroom.service.edu.feign.OssFileService;
import com.ourclassroom.ourclassroom.service.edu.mapper.*;
import com.ourclassroom.ourclassroom.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CourseWishlistMapper courseCollectMapper;

    @Autowired
    private TeacherMapper teacherMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        // save the course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.insert(course);
        // then the unique id for this course will be generated, and we can directly get it through this object

        // save the description
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        // note that we can't use the baseMapper to insert courseDescription object since we are
        // currently at CourseServiceImpl and the baseMapper would insert records into the Course table
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        // get course
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }
        // get course description
        CourseDescription description = courseDescriptionMapper.selectById(id);

        CourseInfoForm form = new CourseInfoForm();
        BeanUtils.copyProperties(course, form);
        form.setDescription(description.getDescription());

        return form;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        // update course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);

        // update course description
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoForm.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionMapper.updateById(courseDescription);
    }

    @Override
    public IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("c.gmt_create");

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }

        Page<CourseVo> pageParam = new Page<>(page, limit);

        List<CourseVo> records = baseMapper.selectPageByCourseQueryVo(pageParam, queryWrapper);

        return pageParam.setRecords(records);

    }

    @Override
    public boolean removeCoverById(String id) {
        // get the url of the cover image
        Course course = baseMapper.selectById(id);
        if (course != null) {
            String url = course.getCover();
            if (!StringUtils.isEmpty(url)) {
                R r = ossFileService.removeFile(url);
                return r.getSuccess();
            }
        }
        return false;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCourseById(String id) {
        // delete video
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        // delete chapter
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        // delete comment
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        // delete wishlist
        QueryWrapper<CourseWishlist> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        // delete course description
        courseDescriptionMapper.deleteById(id);

        return this.removeById(id);

    }

    @Override
    public CoursePublishVo getCoursePublishVoBtId(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }

    @Override
    public boolean unpublishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_DRAFT);
        return this.updateById(course);
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("status", Course.COURSE_NORMAL);
        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", webCourseQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())) {
            if ("1".equals(webCourseQueryVo.getPriceSort())) {
                queryWrapper.orderByAsc("price");
            } else {
                queryWrapper.orderByDesc("price");
            }
        }

        return baseMapper.selectList(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WebCourseVo selectWebCourseVoById(String id) {
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);

        // get course info
        return baseMapper.selectWebCourseVoById(id);
    }

    @Override
    public List<Course> selectPopularCourse() {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("view_count");

        queryWrapper.last("limit 8");

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public CourseDto getCourseDtoById(String courseId) {
        Course course = baseMapper.selectById(courseId);
        Teacher teacher = teacherMapper.selectById(course.getTeacherId());

        CourseDto courseDto = new CourseDto();

        courseDto.setId(course.getId());
        courseDto.setCover(course.getCover());
        courseDto.setPrice(course.getPrice());
        courseDto.setTitle(course.getTitle());
        courseDto.setTeacherName(teacher.getName());

        return courseDto;
    }

    @Override
    public void updateSalesById(String id) {
        Course course = baseMapper.selectById(id);
        course.setBuyCount(course.getBuyCount() + 1);
        this.updateById(course);
    }
}

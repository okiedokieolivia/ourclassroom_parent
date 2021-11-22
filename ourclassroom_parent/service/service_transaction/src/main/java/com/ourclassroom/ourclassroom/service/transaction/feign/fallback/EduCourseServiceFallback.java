package com.ourclassroom.ourclassroom.service.transaction.feign.fallback;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.base.dto.CourseDto;
import com.ourclassroom.ourclassroom.service.transaction.feign.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EduCourseServiceFallback implements EduCourseService {
    @Override
    public CourseDto getCourseDtoById(String courseId) {

        log.info("Degrade protection");
        return null;
    }

    @Override
    public R updateSalesById(String id) {
        log.info("Degrade protection");
        return R.error();
    }
}

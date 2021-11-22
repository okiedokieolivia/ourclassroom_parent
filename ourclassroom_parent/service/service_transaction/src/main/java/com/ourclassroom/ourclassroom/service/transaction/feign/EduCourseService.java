package com.ourclassroom.ourclassroom.service.transaction.feign;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.base.dto.CourseDto;
import com.ourclassroom.ourclassroom.service.transaction.feign.fallback.EduCourseServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "service-edu", fallback = EduCourseServiceFallback.class)
public interface EduCourseService {

    @GetMapping("api/edu/course/inner/get-course-dto/{courseId}")
    CourseDto getCourseDtoById(@PathVariable(value = "courseId") String courseId);

    @GetMapping("/api/edu/course/inner/update-buy-count/{id}")
    R updateSalesById(@PathVariable("id") String id);
}

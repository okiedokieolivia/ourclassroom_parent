package com.ourclassroom.ourclassroom.service.edu.service;

import com.ourclassroom.ourclassroom.service.edu.entity.CourseWishlist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface CourseWishlistService extends IService<CourseWishlist> {

    boolean isAdded(String courseId, String memberId);

    void saveCourseWishlist(String courseId, String memberId);

    List<CourseWishlist> selectListByMemberId(String memberId);

    boolean removeCourseWishlist(String courseId, String memberId);
}

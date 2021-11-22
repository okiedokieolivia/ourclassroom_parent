package com.ourclassroom.ourclassroom.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourclassroom.ourclassroom.service.edu.entity.CourseWishlist;
import com.ourclassroom.ourclassroom.service.edu.mapper.CourseWishlistMapper;
import com.ourclassroom.ourclassroom.service.edu.service.CourseWishlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCollectWishlistImpl extends ServiceImpl<CourseWishlistMapper, CourseWishlist> implements CourseWishlistService {

    @Override
    public boolean isAdded(String courseId, String memberId) {
        QueryWrapper<CourseWishlist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public void saveCourseWishlist(String courseId, String memberId) {
        // check if the course is added or not
        if (!this.isAdded(courseId, memberId)) {
            CourseWishlist wishlist = new CourseWishlist();
            wishlist.setCourseId(courseId);
            wishlist.setMemberId(memberId);
            this.save(wishlist);
        }
    }

    @Override
    public List<CourseWishlist> selectListByMemberId(String memberId) {
        return baseMapper.selectPageByMemberId(memberId);
    }

    @Override
    public boolean removeCourseWishlist(String courseId, String memberId) {
        // check if the course is added
        if (this.isAdded(courseId, memberId)) {
            QueryWrapper<CourseWishlist> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id", courseId);
            queryWrapper.eq("member_id", memberId);

            return this.remove(queryWrapper);
        }

        return false;
    }
}

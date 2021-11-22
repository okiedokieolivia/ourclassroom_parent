package com.ourclassroom.ourclassroom.service.edu.mapper;

import com.ourclassroom.ourclassroom.service.edu.entity.CourseWishlist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author Olivia
 * @since 2021-10-10
 */
@Repository
public interface CourseWishlistMapper extends BaseMapper<CourseWishlist> {
    List<CourseWishlist> selectPageByMemberId(String memberId);
}

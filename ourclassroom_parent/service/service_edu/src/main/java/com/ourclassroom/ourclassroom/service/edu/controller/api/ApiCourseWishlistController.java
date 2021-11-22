package com.ourclassroom.ourclassroom.service.edu.controller.api;


import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.util.JwtInfo;
import com.ourclassroom.ourclassroom.base.util.JwtUtils;
import com.ourclassroom.ourclassroom.service.edu.entity.CourseWishlist;
import com.ourclassroom.ourclassroom.service.edu.service.CourseWishlistService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

////@CrossOrigin
@RestController
@RequestMapping("/api/edu/wishlist")
@Slf4j
public class ApiCourseWishlistController {
    @Autowired
    private CourseWishlistService wishlistService;

    @ApiOperation("Check if the course is in the wishlist")
    @GetMapping("auth/is-added/{courseId}")
    public R isAdded(@ApiParam(value = "course id", required = true)
                         @PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean isAdded = wishlistService.isAdded(courseId, jwtInfo.getId());
        return R.ok().data("isAdded", isAdded);
    }

    @ApiOperation(value = "Save the course in the wishlist")
    @PostMapping("auth/save/{courseId}")
    public R save(@ApiParam(value = "course id", required = true)
                      @PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        wishlistService.saveCourseWishlist(courseId, jwtInfo.getId());
        return R.ok();
    }

    @ApiOperation(value = "Get the wishlist")
    @GetMapping("auth/list")
    public R collectList(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<CourseWishlist> wishlist = wishlistService.selectListByMemberId(jwtInfo.getId());
        return R.ok().data("items", wishlist);
    }

    @ApiOperation(value = "Remove from wishlist")
    @DeleteMapping("auth/remove/{courseId}")
    public R remove(
            @ApiParam(name = "courseId", value = "course id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = wishlistService.removeCourseWishlist(courseId, jwtInfo.getId());
        if (result) {
            return R.ok().message("Successfully removed");
        } else {
            return R.error().message("Data not found");
        }
    }
}


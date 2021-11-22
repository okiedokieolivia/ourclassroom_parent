package com.ourclassroom.ourclassroom.service.edu.controller.admin;


import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.edu.entity.Video;
import com.ourclassroom.ourclassroom.service.edu.feign.VodMediaService;
import com.ourclassroom.ourclassroom.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

////@CrossOrigin
@Api(tags = "Lecture Management")
@RestController
@RequestMapping("/admin/edu/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VodMediaService vodMediaService;

    @ApiOperation("Upload a new lecture")
    @PostMapping("save")
    public R save(
            @ApiParam(value="Video object", required = true)
            @RequestBody Video video){
        boolean result = videoService.save(video);
        if (result) {
            return R.ok().message("Saved");
        } else {
            return R.error().message("Failed to save this lecture");
        }
    }

    @ApiOperation("Get lecture by id")
    @GetMapping("get/{id}")
    public R getById(
            @ApiParam(value="video id", required = true)
            @PathVariable String id){

        Video video = videoService.getById(id);
        if (video != null) {
            return R.ok().data("item", video);
        } else {
            return R.error().message("No such record!");
        }
    }

    @ApiOperation("Edit lecture by id")
    @PutMapping("update")
    public R updateById(
            @ApiParam(value="Video object", required = true)
            @RequestBody Video video){

        boolean result = videoService.updateById(video);
        if (result) {
            return R.ok().message("Saved!");
        } else {
            return R.error().message("No such record!");
        }
    }

    @ApiOperation("Delete course by Id")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "Video ID", required = true)
            @PathVariable String id){

        // remove the video stored in amazon s3
        videoService.removeMediaVideoById(id);

        boolean result = videoService.removeById(id);
        if (result) {
            return R.ok().message("Successfully Deleted!");
        } else {
            return R.error().message("No such record!");
        }
    }

}


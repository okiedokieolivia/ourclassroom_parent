package com.ourclassroom.ourclassroom.service.vod.controller.admin;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Api(tags="Video Service")
//@CrossOrigin
@RestController
@RequestMapping("/admin/vod/media")
@Slf4j
public class MediaController {
    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "file", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            InputStream fileInputStream = file.getInputStream();
            String originalFileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            String videoSource = videoService.uploadVideo(fileInputStream, originalFileName, metadata);
            return R.ok().message("Successfully Uploaded").data("videoSource", videoSource);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.VIDEO_UPLOAD_ERROR);
        }

    }

    @DeleteMapping("remove")
    @ApiOperation("Remove video")
    public R removeVideo(@ApiParam(value = "The video source", required = true) @RequestBody String videoUrl) {
        try {
            videoService.removeVideo(videoUrl);
            return R.ok().message("Video deleted successfully");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.VIDEO_DELETE_ERROR);
        }

    }

    @DeleteMapping("remove-video-list")
    @ApiOperation("Remove a list of videos with given list of video source urls")
    public R removeVideoList(
            @ApiParam(value = "Video ID list", required = true)
            @RequestBody List<String> videoIdList){

        try {
            videoService.removeVideoByIdList(videoIdList);
            return  R.ok().message("Successfully Deleted!");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.VIDEO_DELETE_ERROR);
        }
    }
}

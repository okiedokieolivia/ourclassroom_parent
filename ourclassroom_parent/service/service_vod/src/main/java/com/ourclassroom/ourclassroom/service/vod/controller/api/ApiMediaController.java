package com.ourclassroom.ourclassroom.service.vod.controller.api;

import com.amazonaws.services.rekognition.model.Video;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="Get the video url")
//@CrossOrigin
@RestController
@RequestMapping("/api/vod/media")
@Slf4j
public class ApiMediaController {

    @Autowired
    private VideoService videoService;

    @GetMapping("get-play-auth/{videoSourceId}")
    public R getPlayAuth(@ApiParam(value = "video source id", required = true)
                         @PathVariable String videoSourceId) {
        try{
            String playAuth = videoService.getPlayAuth(videoSourceId);
            return  R.ok().message("Get the authorization").data("playAuth", playAuth);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.FETCH_PLAYAUTH_ERROR);
        }
    }
}

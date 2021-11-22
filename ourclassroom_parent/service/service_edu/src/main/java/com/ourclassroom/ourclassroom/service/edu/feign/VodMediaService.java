package com.ourclassroom.ourclassroom.service.edu.feign;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.feign.fallback.OssFileServiceFallback;
import com.ourclassroom.ourclassroom.service.edu.feign.fallback.VodMediaServiceFallback;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient(value = "service-vod", fallback = VodMediaServiceFallback.class)
public interface VodMediaService {
    @DeleteMapping("/admin/vod/media/remove")
    R removeVideo(@RequestBody String videoSource);

    @DeleteMapping("/admin/vod/media/remove-video-list")
    R removeVideoList(@RequestBody List<String> videoSourceList);
}

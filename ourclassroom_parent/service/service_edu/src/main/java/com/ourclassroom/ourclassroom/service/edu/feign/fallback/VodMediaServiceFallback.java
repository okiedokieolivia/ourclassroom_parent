package com.ourclassroom.ourclassroom.service.edu.feign.fallback;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.feign.VodMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VodMediaServiceFallback implements VodMediaService {
    @Override
    public R removeVideo(String videoSource) {
        log.info("protection: degrade");
        return R.error();
    }

    @Override
    public R removeVideoList(List<String> videoSourceList) {
        log.info("protection: degrade");
        return R.error();
    }
}

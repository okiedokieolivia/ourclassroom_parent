package com.ourclassroom.ourclassroom.service.edu.feign.fallback;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OssFileServiceFallback implements OssFileService {
    @Override
    public R removeFile(String url) {
        log.info("protection: degrade");
        return R.error();
    }

    @Override
    public R test() {
        return R.error();
    }
}

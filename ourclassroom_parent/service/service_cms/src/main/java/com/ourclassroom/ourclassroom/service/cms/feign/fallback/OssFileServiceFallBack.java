package com.ourclassroom.ourclassroom.service.cms.feign.fallback;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.cms.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {

    @Override
    public R removeFile(String url) {
        log.info("Protection degrade");
        return R.error().message("Time Limit Exceeds");
    }
}

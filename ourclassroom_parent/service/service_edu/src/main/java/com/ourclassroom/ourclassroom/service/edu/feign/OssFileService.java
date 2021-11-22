package com.ourclassroom.ourclassroom.service.edu.feign;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.feign.fallback.OssFileServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(value = "service-oss", fallback = OssFileServiceFallback.class)
public interface OssFileService {

    @DeleteMapping("admin/oss/file/remove")
    R removeFile(@RequestBody String url);

    @GetMapping("admin/oss/file/test")
    R test();
}

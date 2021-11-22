package com.ourclassroom.ourclassroom.service.oss.controller.admin;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Api(tags = "Aliyun file uploading management")
//@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation("Upload file")
    @PostMapping("upload")
    public R upload(@ApiParam(value = "file", required = true) @RequestParam("file") MultipartFile file,
                    @ApiParam(value = "module", required = true) @RequestParam("module") String module) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String url = fileService.upload(inputStream, module, originalFilename);

            return R.ok().message("File Uploaded Successfully!").data("url", url);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "Delete file with the url")
    @DeleteMapping("remove")
    public R removeFile(@ApiParam(value = "The file to be deleted", required = true) @RequestBody String url) {
        fileService.removeFile(url);
        return R.ok().message("File deleted successfully");
    }

}

package com.ourclassroom.ourclassroom.service.vod.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.vod.config.StorageConfig;
import com.ourclassroom.ourclassroom.service.vod.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {
    @Autowired
    private StorageConfig config;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public String uploadVideo(InputStream file, String originalFileName, ObjectMetadata metadata) {
        String bucket = config.getBucket();
        //String module = "videos";

        // examine if the bucket exist
        if (!amazonS3.doesBucketExistV2(bucket)) {
            amazonS3.createBucket(bucket);
            //amazonS3.setBucketAcl(bucket, CannedAccessControlList.PublicRead);
        }

        // create folder and random filename
        //String folder = new DateTime().toString("yyyy/MM/dd");
        String filename = UUID.randomUUID().toString();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String key = filename + "." + fileExtension;

        try {
            amazonS3.putObject(bucket, key, file, metadata);
            //amazonS3.setObjectAcl(bucket, key, CannedAccessControlList.PublicRead);

            return key;
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OurclassroomException(ResultCodeEnum.VIDEO_UPLOAD_SERVER_ERROR);
        }

    }

    @Override
    public void removeVideo(String videoSource) throws SdkClientException {
        String bucket = config.getBucket();
        amazonS3.deleteObject(bucket, videoSource);
    }

    @Override
    public void removeVideoByIdList(List<String> videoSourceList) throws SdkClientException {
        String bucket = config.getBucket();
        //String endpoint = "https://" + bucket + ".s3.us-east-2.amazonaws.com/";
        //List<KeyVersion> keys = videoIdList.stream().map(url -> new KeyVersion(url.substring(endpoint.length()))).collect(Collectors.toList());
        List<KeyVersion> keys = videoSourceList.stream().map(key -> new KeyVersion(key)).collect(Collectors.toList());

        int start = 0;
        int end;

        while (start < keys.size()) {
            end = Math.min(keys.size(), start + 20);
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucket)
                    .withKeys(keys.subList(start, end))
                    .withQuiet(false);
            DeleteObjectsResult delObjRes = amazonS3.deleteObjects(multiObjectDeleteRequest);
            int successfulDeletes = delObjRes.getDeletedObjects().size();

            start = end;
        }
    }

    @Override
    public String getPlayAuth(String videoSourceId) {
        String bucket = config.getBucket();
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, videoSourceId)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        System.out.println(url);

        return url.toString();

    }
}

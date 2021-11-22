package com.ourclassroom.ourclassroom.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.ourclassroom.ourclassroom.service.oss.service.FileService;
import com.ourclassroom.ourclassroom.service.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private OssProperties ossProperties;

    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecrete = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // create ossClient object
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecrete);

        // examine if the bucket exists
        if (!ossClient.doesBucketExist(bucketname)) {
            // create the bucket
            ossClient.createBucket(bucketname);
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }
        // create folder and random filename
        String folder = new DateTime().toString("yyyy/MM/dd");
        String filename = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = module + "/" + folder + "/" + filename + fileExtension;
        // upload the file
        ossClient.putObject(bucketname, key, inputStream);
        // shutdown the oss client
        ossClient.shutdown();

        return "https://" + bucketname + "." + endpoint + "/" + key;

    }

    @Override
    public void removeFile(String url) {
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecrete = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // create ossClient object
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecrete);

        String host = "https://" + bucketname + "." + endpoint + "/";
        String objectName = url.substring(host.length());
        ossClient.deleteObject(bucketname, objectName);
        // shutdown
        ossClient.shutdown();
    }
}

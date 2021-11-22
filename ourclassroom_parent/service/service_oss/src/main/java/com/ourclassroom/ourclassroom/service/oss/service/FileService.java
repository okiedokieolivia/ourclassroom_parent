package com.ourclassroom.ourclassroom.service.oss.service;

import java.io.InputStream;

public interface FileService {

    /**
     * Upload file to aliyun oss
     * @param inputStream input stream
     * @param module module name where the file should be uploaded
     * @param originalFilename the original name of this file being uploaded
     * @return the url of the file in oss server
     */
    String upload(InputStream inputStream, String module, String originalFilename);

    /**
     * remove the file with given url
     * @param url the url of the file
     */
    void removeFile(String url);
}

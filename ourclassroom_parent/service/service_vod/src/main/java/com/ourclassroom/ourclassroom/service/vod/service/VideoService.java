package com.ourclassroom.ourclassroom.service.vod.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.bouncycastle.asn1.cms.MetaData;

import java.io.InputStream;
import java.util.List;

public interface VideoService {
    String uploadVideo(InputStream file, String originalName, ObjectMetadata metaData);

    void removeVideo(String videoSource);

    void removeVideoByIdList(List<String> videoSourceList);

    String getPlayAuth(String videoSourceId);
}

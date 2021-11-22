package com.ourclassroom.ourclassroom.service.edu.service;

import com.ourclassroom.ourclassroom.service.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VideoService extends IService<Video> {
    void removeMediaVideoById(String id);

    void removeMediaVideoByChapterId(String chapterId);

    void removeMediaVideoByCourseId(String courseId);
}

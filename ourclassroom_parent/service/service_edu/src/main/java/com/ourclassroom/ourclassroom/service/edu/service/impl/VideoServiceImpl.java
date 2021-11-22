package com.ourclassroom.ourclassroom.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourclassroom.ourclassroom.service.edu.entity.Video;
import com.ourclassroom.ourclassroom.service.edu.feign.VodMediaService;
import com.ourclassroom.ourclassroom.service.edu.mapper.VideoMapper;
import com.ourclassroom.ourclassroom.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodMediaService vodMediaService;

    @Override
    public void removeMediaVideoById(String id) {
        // find the corresponding video source url according to the course id
        Video video = baseMapper.selectById(id);
        String videoSource = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSource)) {
            vodMediaService.removeVideo(videoSource);
        }

    }

    @Override
    public void removeMediaVideoByChapterId(String chapterId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("video_source_id");
        queryWrapper.eq("chapter_id", chapterId);
        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
//        List<String> keys = new ArrayList<>();
//        System.out.println(maps);
//        maps.stream().forEach(map -> {
//            if (map != null) {
//                String key = (String) map.get("video_source_id");
//                if (key!= null && !StringUtils.isEmpty(key)) {
//                    keys.add(key);
//                }
//            }
//        });
        List<String> keys = this.getVideoSourceIdList(maps);
        vodMediaService.removeVideoList(keys);
    }

    @Override
    public void removeMediaVideoByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("video_source_id");
        queryWrapper.eq("course_id", courseId);
        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);

        // remove videos
        List<String> keys = this.getVideoSourceIdList(maps);
        vodMediaService.removeVideoList(keys);

    }

    private List<String> getVideoSourceIdList(List<Map<String, Object>> maps) {
        List<String> keys = new ArrayList<>();
        System.out.println(maps);
        maps.stream().forEach(map -> {
            if (map != null) {
                String key = (String) map.get("video_source_id");
                if (key!= null && !StringUtils.isEmpty(key)) {
                    keys.add(key);
                }
            }
        });
        return keys;
    }
}

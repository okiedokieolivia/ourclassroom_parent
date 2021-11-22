package com.ourclassroom.ourclassroom.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourclassroom.ourclassroom.service.edu.entity.Chapter;
import com.ourclassroom.ourclassroom.service.edu.entity.Video;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.ChapterVo;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.VideoVo;
import com.ourclassroom.ourclassroom.service.edu.mapper.ChapterMapper;
import com.ourclassroom.ourclassroom.service.edu.mapper.VideoMapper;
import com.ourclassroom.ourclassroom.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeChapterById(String id) {
        // remove the chapter video
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        videoMapper.delete(videoQueryWrapper);
        // remove the chapter
        return this.removeById(id);
    }

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        // get chapters
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterQueryWrapper.orderByAsc("id");
        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);

        // get videos
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.orderByAsc("chapter_id", "sort", "id");
        List<Video> videos = videoMapper.selectList(videoQueryWrapper);

        // combine
        List<ChapterVo> chapterVos = new ArrayList<>();
        int j = 0;
        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVos.add(chapterVo);
            List<VideoVo> videoVos = new ArrayList<>();
            System.out.println(chapter.getId());
            while (j < videos.size() && videos.get(j).getChapterId().equals(chapter.getId())) {
                System.out.println(videos.get(j).getChapterId());
                System.out.println(videos.get(j).getChapterId().equals(chapter.getId()));
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(videos.get(j), videoVo);
                videoVos.add(videoVo);
                j++;
            }
            chapterVo.setChildren(videoVos);
        }
        chapterVos.sort(Comparator.comparingInt(ChapterVo::getSort));
        return chapterVos;
    }
}

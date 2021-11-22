package com.ourclassroom.ourclassroom.service.edu.service;

import com.ourclassroom.ourclassroom.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.ChapterVo;

import java.util.List;


public interface ChapterService extends IService<Chapter> {

    boolean removeChapterById(String id);

    List<ChapterVo> nestedList(String courseId);
}

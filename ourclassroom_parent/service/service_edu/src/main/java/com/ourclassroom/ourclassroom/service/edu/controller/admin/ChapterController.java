package com.ourclassroom.ourclassroom.service.edu.controller.admin;


import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.edu.entity.Chapter;
import com.ourclassroom.ourclassroom.service.edu.entity.vo.ChapterVo;
import com.ourclassroom.ourclassroom.service.edu.service.ChapterService;
import com.ourclassroom.ourclassroom.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

////@CrossOrigin
@Api(tags = "Chapters")
@RestController
@RequestMapping("/admin/edu/chapter")
@Slf4j
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    @ApiOperation("Add new chapter")
    @PostMapping("save")
    public R save(
            @ApiParam(value="Chapter object", required = true)
            @RequestBody Chapter chapter){
        boolean result = chapterService.save(chapter);
        if (result) {
            return R.ok().message("Successfully Saved");
        } else {
            return R.error().message("Failed to save this chapter");
        }
    }

    @ApiOperation("Get chapter by id")
    @GetMapping("get/{id}")
    public R getById(
            @ApiParam(value="chapter id", required = true)
            @PathVariable String id){

        Chapter chapter = chapterService.getById(id);
        if (chapter != null) {
            return R.ok().data("item", chapter);
        } else {
            return R.error().message("No such record!");
        }
    }

    @ApiOperation("Edit chapter")
    @PutMapping("update")
    public R updateById(
            @ApiParam(value="Chapter object", required = true)
            @RequestBody Chapter chapter){

        boolean result = chapterService.updateById(chapter);
        if (result) {
            return R.ok().message("Successfully Updated");
        } else {
            return R.error().message("No such record!");
        }
    }

    @ApiOperation("Remove chapter by id")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "The id of the chapter being deleted", required = true)
            @PathVariable  String id) {
        // delete videos related to this chapter
        videoService.removeMediaVideoByChapterId(id);

        // delete the chapter
        boolean result = chapterService.removeChapterById(id);
        if (result) {
            return R.ok().message("Successfully Deleted");
        } else {
            return R.error().message("No such record!");
        }
    }

    @ApiOperation("Get the chapters of a course with given id")
    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(
            @ApiParam(value = "course ID", required = true)
            @PathVariable String courseId){

        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }

}


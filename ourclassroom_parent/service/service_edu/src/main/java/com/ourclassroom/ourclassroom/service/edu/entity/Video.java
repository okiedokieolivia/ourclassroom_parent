package com.ourclassroom.ourclassroom.service.edu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.ourclassroom.ourclassroom.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_video")
@ApiModel(value="Video ", description="course video")
public class Video extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "course id")
    private String courseId;

    @ApiModelProperty(value = "chapter id")
    private String chapterId;

    @ApiModelProperty(value = "title")
    private String title;

    @ApiModelProperty(value = "source id")
    private String videoSourceId;

    @ApiModelProperty(value = "original name")
    private String videoOriginalName;

    @ApiModelProperty(value = "sort")
    private Integer sort;

    @ApiModelProperty(value = "play count")
    private Long playCount;

    @ApiModelProperty(value = "is free")
    @TableField("is_free")
    private Boolean free;

    @ApiModelProperty(value = "total length")
    private Float duration;

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "video size")
    private Long size;

    @ApiModelProperty(value = "optimistic lock")
    private Long version;


}

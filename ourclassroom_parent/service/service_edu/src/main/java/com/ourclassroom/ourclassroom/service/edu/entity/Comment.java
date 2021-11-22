package com.ourclassroom.ourclassroom.service.edu.entity;

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
@TableName("edu_comment")
@ApiModel(value="Comment", description="comment")
public class Comment extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "course id")
    private String courseId;

    @ApiModelProperty(value = "teacher id")
    private String teacherId;

    @ApiModelProperty(value = "member id")
    private String memberId;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "avatar")
    private String avatar;

    @ApiModelProperty(value = "content")
    private String content;


}

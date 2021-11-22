package com.ourclassroom.ourclassroom.service.cms.entity;

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
@TableName("cms_ad")
@ApiModel(value="Ad", description="recommondation")
public class Ad extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "title")
    private String title;

    @ApiModelProperty(value = "type ID")
    private String typeId;

    @ApiModelProperty(value = "image url")
    private String imageUrl;

    @ApiModelProperty(value = "background color")
    private String color;

    @ApiModelProperty(value = "link url")
    private String linkUrl;

    @ApiModelProperty(value = "the order")
    private Integer sort;


}

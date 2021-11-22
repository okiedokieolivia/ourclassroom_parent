package com.ourclassroom.ourclassroom.service.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.service.cms.entity.Ad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ourclassroom.ourclassroom.service.cms.entity.vo.AdVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdMapper extends BaseMapper<Ad> {

    List<AdVo> selectPageByQueryWrapper(Page<AdVo> pageParam,
                                        @Param(Constants.WRAPPER) QueryWrapper<AdVo> queryWrapper);
}

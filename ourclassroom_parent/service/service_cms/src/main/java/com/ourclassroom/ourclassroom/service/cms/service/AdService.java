package com.ourclassroom.ourclassroom.service.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ourclassroom.ourclassroom.service.cms.entity.Ad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ourclassroom.ourclassroom.service.cms.entity.vo.AdVo;

import java.util.List;

public interface AdService extends IService<Ad> {

    IPage<AdVo> selectPage(Long page, Long limit);

    boolean removeAdImageById(String id);

    List<Ad> selectByAdTypeId(String adTypeId);
}

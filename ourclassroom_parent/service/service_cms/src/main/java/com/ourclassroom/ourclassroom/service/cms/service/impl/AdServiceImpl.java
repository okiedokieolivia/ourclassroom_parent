package com.ourclassroom.ourclassroom.service.cms.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.cms.entity.Ad;
import com.ourclassroom.ourclassroom.service.cms.entity.vo.AdVo;
import com.ourclassroom.ourclassroom.service.cms.feign.OssFileService;
import com.ourclassroom.ourclassroom.service.cms.mapper.AdMapper;
import com.ourclassroom.ourclassroom.service.cms.service.AdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {

    @Autowired
    private OssFileService ossFileService;

    @Override
    public IPage<AdVo> selectPage(Long page, Long limit) {

        QueryWrapper<AdVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id", "a.sort");

        Page<AdVo> pageParam = new Page<>(page, limit);

        List<AdVo> records = baseMapper.selectPageByQueryWrapper(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }

    @Override
    public boolean removeAdImageById(String id) {
        Ad ad = baseMapper.selectById(id);
        if(ad != null) {
            String imagesUrl = ad.getImageUrl();
            if(!StringUtils.isEmpty(imagesUrl)){
                //remove the image
                R r = ossFileService.removeFile(imagesUrl);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Override
    public List<Ad> selectByAdTypeId(String adTypeId) {
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort", "id");
        queryWrapper.eq("type_id", adTypeId);

        return baseMapper.selectList(queryWrapper);
    }
}

package com.ourclassroom.ourclassroom.service.cms.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.cms.entity.Ad;
import com.ourclassroom.ourclassroom.service.cms.entity.vo.AdVo;
import com.ourclassroom.ourclassroom.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@Api(tags = "Ad recommendation management")
@RestController
@RequestMapping("/admin/cms/ad")
@Slf4j
public class AdController {
    @Autowired
    private AdService adService;

    @ApiOperation("Add new ad")
    @PostMapping("save")
    public R save(@ApiParam(value = "ad object", required = true) @RequestBody Ad ad) {

        boolean result = adService.save(ad);
        if (result) {
            return R.ok().message("Saved!");
        } else {
            return R.error().message("Failed to save");
        }
    }

    @ApiOperation("Update the ad")
    @PutMapping("update")
    public R updateById(@ApiParam(value = "the given ad", required = true) @RequestBody Ad ad) {
        boolean result = adService.updateById(ad);
        if (result) {
            return R.ok().message("Updated!");
        } else {
            return R.error().message("No such record");
        }
    }

    @ApiOperation("Get ad info by id")
    @GetMapping("get/{id}")
    public R getById(@ApiParam(value = "ID", required = true) @PathVariable String id) {
        Ad ad = adService.getById(id);
        if (ad != null) {
            return R.ok().data("item", ad);
        } else {
            return R.error().message("No such record");
        }
    }

    @ApiOperation("page break")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "current page", required = true) @PathVariable Long page,
                      @ApiParam(value = "the number of records each page", required = true) @PathVariable Long limit) {

        IPage<AdVo> pageModel = adService.selectPage(page, limit);
        List<AdVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "Delete the ad by id")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "ID", required = true) @PathVariable String id) {


        adService.removeAdImageById(id);


        boolean result = adService.removeById(id);
        if (result) {
            return R.ok().message("Successfully deleted");
        } else {
            return R.error().message("No such record");
        }
    }
}


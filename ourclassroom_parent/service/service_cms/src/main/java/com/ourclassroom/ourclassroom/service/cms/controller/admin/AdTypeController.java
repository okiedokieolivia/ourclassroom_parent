package com.ourclassroom.ourclassroom.service.cms.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.cms.entity.AdType;
import com.ourclassroom.ourclassroom.service.cms.service.AdTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@Api(tags = "Recommendation management")
@RestController
@RequestMapping("/admin/cms/ad-type")
@Slf4j
public class AdTypeController {
    @Autowired
    private AdTypeService adTypeService;

    @ApiOperation("list all recommmendation types")
    @GetMapping("list")
    public R listAll() {
        List<AdType> list = adTypeService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation("page break")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "current page", required = true) @PathVariable Long page,
                      @ApiParam(value = "number of records each page", required = true) @PathVariable Long limit) {

        Page<AdType> pageParam = new Page<>(page, limit);
        IPage<AdType> pageModel = adTypeService.page(pageParam);
        List<AdType> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "Delete the type by id")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "type ID", required = true) @PathVariable String id) {

        boolean result = adTypeService.removeById(id);
        if (result) {
            return R.ok().message("Successfully deleted!");
        } else {
            return R.error().message("No such record");
        }
    }

    @ApiOperation("Add new type")
    @PostMapping("save")
    public R save(@ApiParam(value = "Type object", required = true) @RequestBody AdType adType) {

        boolean result = adTypeService.save(adType);
        if (result) {
            return R.ok().message("Saved!");
        } else {
            return R.error().message("Failed to save");
        }
    }

    @ApiOperation("Update one particular type")
    @PutMapping("update")
    public R updateById(@ApiParam(value = "type", required = true) @RequestBody AdType adType) {
        boolean result = adTypeService.updateById(adType);
        if (result) {
            return R.ok().message("Updated!");
        } else {
            return R.error().message("No such record");
        }
    }

    @ApiOperation("get info by id")
    @GetMapping("get/{id}")
    public R getById(@ApiParam(value = "Type id", required = true) @PathVariable String id) {
        AdType adType = adTypeService.getById(id);
        if (adType != null) {
            return R.ok().data("item", adType);
        } else {
            return R.error().message("No such record");
        }
    }
}


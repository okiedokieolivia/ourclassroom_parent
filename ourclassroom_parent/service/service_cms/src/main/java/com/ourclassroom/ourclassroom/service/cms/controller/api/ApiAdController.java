package com.ourclassroom.ourclassroom.service.cms.controller.api;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.cms.entity.Ad;
import com.ourclassroom.ourclassroom.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@Api(tags = "Advertising Recommendation")
@RestController
@RequestMapping("/api/cms/ad")
@Slf4j
public class ApiAdController {
    @Autowired
    private AdService adService;

    @ApiOperation("Get the all the ads with given type ")
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@ApiParam(value = "type id", required = true) @PathVariable String adTypeId) {

        List<Ad> adList = adService.selectByAdTypeId(adTypeId);
        return R.ok().data("items", adList);
    }
}

package com.imooc.controller;

import com.imooc.service.BgmService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2616:28
 */
@RestController
@Api(value = "背景音乐业务的接口", tags = {"背景音乐业务的controller"})
@RequestMapping("/bgm")
public class BgmController {
    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "获取背景音乐列表", notes = "获取背景音乐列表的接口")
    @PostMapping("/list")
    public IMoocJSONResult list() {
        return IMoocJSONResult.ok(bgmService.queryBgmList());
    }

    @ApiOperation(value = "获取背景音乐分页", notes = "获取背景音乐分页的接口")
    @PostMapping("/page")
    public IMoocJSONResult list(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        return IMoocJSONResult.ok(bgmService.queryBgmPage(page, pageSize));
    }
}

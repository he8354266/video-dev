package com.imooc.controller;

import com.imooc.mapper.BgmMapper;
import com.imooc.pojo.Videos;
import com.imooc.service.BgmService;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2216:23
 */
@RestController
@Api(value = "视频相关业务的接口", tags = {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {


    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

    @PostMapping(value = "/showAll")
    public IMoocJSONResult showAll(@RequestBody Videos videos,Integer isSaveRecord,Integer page,)
}

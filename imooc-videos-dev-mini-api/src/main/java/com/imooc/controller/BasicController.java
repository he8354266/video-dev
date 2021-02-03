package com.imooc.controller;

import com.imooc.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2211:10
 */
@RestController
public class BasicController {
    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    // 文件保存的命名空间
    public static final String FILE_SPACE = "E:/imooc_videos_dev";

    // ffmpeg所在目录
    public static final String FFMPEG_EXE = "E:\\ffmpeg\\bin\\ffmpeg.exe";

    // 每页分页的记录数
    public static final Integer PAGE_SIZE = 5;
}

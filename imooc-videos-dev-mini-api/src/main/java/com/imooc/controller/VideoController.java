package com.imooc.controller;

import com.imooc.enums.VideoStatusEnum;
import com.imooc.mapper.BgmMapper;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.Comments;
import com.imooc.pojo.Videos;
import com.imooc.service.BgmService;
import com.imooc.service.VideoService;
import com.imooc.utils.*;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @ApiOperation(value = "上传视频", notes = "上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
                    dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/upload")
    public IMoocJSONResult upload(String userId,
                                  String bgmId, double videoSeconds,
                                  int videoWidth, int videoHeight,
                                  String desc, @ApiParam(value = "短视频", required = true)
                                          MultipartFile file) throws Exception {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        //文件上传的最终保存路径
        String finalVideoPath = null;
        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = "/" + userId + "/video";
        try {
            if (StringUtils.isBlank(userId)) {
                return IMoocJSONResult.errorMsg("用户id不能为空...");
            }


            if (file != null) {
                //获取文件名
                String fileName = file.getOriginalFilename();
                String arrayFilenameItem[] = fileName.split("\\.");
                String fileNamePrefix = "";
                for (int i = 0; i < arrayFilenameItem.length - 1; i++) {
                    fileNamePrefix += arrayFilenameItem[i];
                }
                if (StringUtils.isNotBlank(fileName)) {
                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";
                }
                File outFile = new File(finalVideoPath);
                if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                    outFile.getParentFile().mkdirs();
                }
                fileOutputStream = new FileOutputStream(outFile);
                inputStream = file.getInputStream();
                org.apache.commons.io.IOUtils.copy(inputStream, fileOutputStream);
            } else {
                return IMoocJSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错...");
        } finally {
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        // 判断bgmId是否为空，如果不为空，
        // 那就查询bgm的信息，并且合并视频，生产新的视频
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_SPACE + bgm.getPath();

            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            String videoInputPath = finalVideoPath;

            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "/" + userId + "/video/" + videoOutputName;
            finalVideoPath = FILE_SPACE + uploadPathDB;
            tool.convertor(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
        }
        System.out.println("uploadPathDB=" + uploadPathDB);
        System.out.println("finalVideoPath=" + finalVideoPath);

        //对视频进行截图
        FetchVideoCover videoCover = new FetchVideoCover(FFMPEG_EXE);
        videoCover.getCover(finalVideoPath, FILE_SPACE + coverPathDB);
        System.out.println("finalVideoPath=" + finalVideoPath);

        // 保存视频信息到数据库
        Videos videos = new Videos();
        videos.setAudioId(bgmId);
        videos.setUserId(userId);
        videos.setVideoSeconds((float) videoSeconds);
        videos.setVideoHeight(videoHeight);
        videos.setVideoWidth(videoWidth);
        videos.setVideoDesc(desc);
        videos.setVideoPath(uploadPathDB);
        videos.setCoverPath(coverPathDB);
        videos.setStatus(VideoStatusEnum.SUCCESS.value);
        videos.setCreateTime(new Date());

        String videoId = videoService.saveVideo(videos);
        return IMoocJSONResult.ok(videoId);
    }

    @ApiOperation(value = "上传封面", notes = "上传封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoId", value = "视频主键id", required = true,
                    dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
    public IMoocJSONResult uploadCover(String userId,
                                       String videoId,
                                       @ApiParam(value = "视频封面", required = true)
                                               MultipartFile file) throws IOException {
        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("视频主键id和用户id不能为空...");
        }
        //保存数据库中的相对路劲
        String uploadPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        //文件上传最终的保存路径
        String finalCoverPath = "";
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    finalCoverPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    File outFile = new File(finalCoverPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    org.apache.commons.io.IOUtils.copy(inputStream, fileOutputStream);
                }

            } else {
                return IMoocJSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        videoService.updateVideo(videoId, uploadPathDB);
        Map<String, String> map  = new HashMap<>();
        map.put("file",finalCoverPath);
        return IMoocJSONResult.ok(map);

    }

    /**
     * @Description: 分页和搜索查询视频列表
     * isSaveRecord：1 - 需要保存
     * 0 - 不需要保存 ，或者为空的时候
     */
    @PostMapping("/showAll")
    public IMoocJSONResult showAll(@RequestBody Videos videos, Integer isSaveRecord, Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedResult pagedResult = videoService.getAllComments(videos.getId(), page, pageSize);
        return IMoocJSONResult.ok(pagedResult);
    }

    /**
     * @Description: 我关注的人发的视频
     */
    @PostMapping("/showMyFollow")
    public IMoocJSONResult showMyFollow(String userId, Integer page) {
        if (StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("userId不能为空");
        }
        if (page == null) {
            page = 1;
        }
        int pageSize = 6;
        PagedResult pagedResult = videoService.queryMyFollowVideos(userId, page, pageSize);
        return IMoocJSONResult.ok(pagedResult);
    }

    /**
     * @Description: 我收藏(点赞)过的视频列表
     */
    @PostMapping("/showMyLike")
    public IMoocJSONResult showMyLike(String userId, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("userId不能为空");
        }
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 6;
        }
        PagedResult videoList = videoService.queryMyLikeVideos(userId, page, pageSize);
        return IMoocJSONResult.ok(videoList);
    }

    @PostMapping(value = "/hot")
    public IMoocJSONResult hot() {
        return IMoocJSONResult.ok(videoService.getHotwords());
    }

    @PostMapping(value = "/userLike")
    public IMoocJSONResult userLike(String userId, String videoId, String videoCreaterId) {
        videoService.userLikeVideo(userId, videoId, videoCreaterId);
        return IMoocJSONResult.ok();
    }

    @PostMapping(value = "/userUnLike")
    public IMoocJSONResult userUnLike(String userId, String videoId, String videoCreaterId) {
        videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return IMoocJSONResult.ok();
    }

    @PostMapping("/saveComment")
    public IMoocJSONResult saveComment(@RequestBody Comments comments, String fatherCommentId, String toUserId) {
        comments.setFatherCommentId(fatherCommentId);
        comments.setToUserId(toUserId);
        videoService.saveComment(comments);

        return IMoocJSONResult.ok();
    }

    @PostMapping("/getVideoComments")
    public IMoocJSONResult getVideoComments(String videoId, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(videoId)) {
            return IMoocJSONResult.errorMsg("videoId不能为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PagedResult list = videoService.getAllComments(videoId, page, pageSize);
        return IMoocJSONResult.ok(list);
    }
}

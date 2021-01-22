package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersReport;
import com.imooc.pojo.vo.PublisherVideo;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2117:25
 */
@RestController
@RequestMapping("/user")
public class UserController extends BasicController {
    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
            dataType = "String", paramType = "query")
    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws IOException {
        if (StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("用户id不能为空");
        }
        //文件保存的命名空间
        String fileSpace = FILE_SPACE;
        //保存到数据库的相对路劲
        String uploadPathDB = "/" + userId + "/face";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {
                //获取文件名
                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    //文件上传最终保存路劲
                    String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
                    //设置数据保存路劲
                    uploadPathDB += fileName;
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return IMoocJSONResult.errorMsg("上传出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        Users users = new Users();
        users.setId(userId);
        users.setFaceImage(uploadPathDB);
        userService.updateUserInfo(users);
        return IMoocJSONResult.ok(uploadPathDB);
    }


    @ApiOperation(value = "查询用户信息", notes = "查询用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝id", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/query")
    public IMoocJSONResult query(String userId, String fanId) {

        if (StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("用户id不能为空");
        }
        Users users = userService.queryUserInfo(userId);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setFollow(userService.queryIfFollow(userId, fanId));
        return IMoocJSONResult.ok(usersVO);
    }

    @PostMapping("/queryPublisher")
    @ApiOperation(value = "查询视频信息", notes = "查询视频信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUserId", value = "登录用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "publishUserId", value = "视频发布者id", required = true, dataType = "String", paramType = "query")
    })
    public IMoocJSONResult queryPublisher(String loginUserId, String videoId,
                                          String publishUserId) {
        if (StringUtils.isBlank(publishUserId)) {
            return IMoocJSONResult.errorMsg("视频发布者id不能为空");
        }
        //查询视频发布者信息
        Users users = userService.queryUserInfo(publishUserId);
        UsersVO publisher = new UsersVO();
        BeanUtils.copyProperties(users, publisher);

        //查询当前登陆者和视频点赞的关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);
        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);
        return IMoocJSONResult.ok(bean);
    }

    @PostMapping("/beyourfans")
    @ApiOperation(value = "用户关注", notes = "用户关注接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝id", required = true, dataType = "String", paramType = "query")
    })
    public IMoocJSONResult beyourfans(String userId, String fanId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return IMoocJSONResult.errorMsg("用户id或者粉丝id不能为空");
        }
        boolean result = userService.queryIfFollow(userId, fanId);
        if (result) {
            return IMoocJSONResult.errorMsg("已关注该用户");
        }
        userService.saveUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok("关注成功");
    }

    @PostMapping("/dontbeyourfans")
    @ApiOperation(value = "取消用户关注", notes = "取消用户关注接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝id", required = true, dataType = "String", paramType = "query")
    })
    public IMoocJSONResult dontbeyourfans(String userId, String fanId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return IMoocJSONResult.errorMsg("用户id或者粉丝id不能为空");
        }
        boolean result = userService.queryIfFollow(userId, fanId);
        if (!result) {
            return IMoocJSONResult.errorMsg("未关注该用户");
        }
        userService.deleteUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok("取消关注成功");
    }

    @PostMapping("/reportUser")
    @ApiOperation(value = "举报视频", notes = "举报视频接口")
    public IMoocJSONResult reportUser(@RequestBody UsersReport usersReport) {
        //保存举报信息
        userService.reportUser(usersReport);
        return IMoocJSONResult.ok("举报成功...有你平台变得更美好...");
    }
}

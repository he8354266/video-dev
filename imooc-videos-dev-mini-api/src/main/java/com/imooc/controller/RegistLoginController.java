package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2213:50
 */
@RestController
@Api(value = "用户注册登录的接口", tags = {"注册和登录的controller"})
public class RegistLoginController extends BasicController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @PostMapping("/regist")
    public IMoocJSONResult regist(@RequestBody Users users) throws Exception {
        //判断用户名和密码不能为空
        if (StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())) {
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }
        //判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(users.getUsername());
        //保存用户，注册信息
        if (!usernameIsExist) {
            users.setNickname(users.getUsername());
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            users.setFansCounts(0);
            users.setReceiveLikeCounts(0);
            users.setFollowCounts(0);
            userService.saveUser(users);
        } else {
            return IMoocJSONResult.errorMsg("用户名已经存在，请换一个再试");
        }
        users.setPassword("");
        UsersVO usersVO = setUserRedisSessionToken(users);
        return IMoocJSONResult.ok(usersVO);
    }


    @ApiOperation(value = "用户登录", notes = "用户登录的接口")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody Users users) throws Exception {
        String username = users.getUsername();
        String password = users.getPassword();

        //判断用户名和密码不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMoocJSONResult.ok("用户名或密码不能为空...");
        }
        //判断用户是否存在
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult != null) {
            userResult.setPassword("");
            UsersVO usersVO = setUserRedisSessionToken(userResult);
            String token = redis.get(USER_REDIS_SESSION + ":" + users.getId());
            usersVO.setUserToken(token);
            return IMoocJSONResult.ok(usersVO);
        } else {
            return IMoocJSONResult.errorMsg("用户名或密码不正确, 请重试...");
        }

    }

    @ApiOperation(value = "用户注销", notes = "用户注销的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
            dataType = "String", paramType = "query")
    @PostMapping("/logout")
    public IMoocJSONResult logout(String userId) {
        String token = redis.get(USER_REDIS_SESSION + ":" + userId);
        if (token != null) {
            redis.del(USER_REDIS_SESSION + ":" + userId);
            return IMoocJSONResult.ok("注销成功");
        }
        return IMoocJSONResult.ok("注销失败");
    }

    private UsersVO setUserRedisSessionToken(Users users) {
        String uniqueToken = String.valueOf(UUID.randomUUID());
        redis.set(USER_REDIS_SESSION + ":" + users.getId(), uniqueToken, 1000 * 60 * 30);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        return usersVO;
    }
}

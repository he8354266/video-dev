package com.imoocs.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/815:51
 */

import com.imooc.pojo.Users;

import com.imooc.utils.IMoocJSONResult;
import com.imoocs.bean.AdminUser;
import com.imoocs.service.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.imooc.utils.PagedResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/8 15:51
 * @updateDate 2021/2/8 15:51
 **/
@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private UsersService usersService = null;


    @GetMapping("list")
    public PagedResult list(Users users, Integer page) {
        PagedResult pagedResult = usersService.queryUsers(users, page == null ? 1 : page, 10);
        return pagedResult;
    }

    @PostMapping("login")
    public IMoocJSONResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        if (org.apache.commons.lang3.StringUtils.isBlank(username) || org.apache.commons.lang3.StringUtils.isBlank(password)) {
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        } else if (usersService.checkUsers(username, password)) {
            String token = UUID.randomUUID().toString();
            AdminUser adminUser = new AdminUser(token, username, "");
            request.getSession().setAttribute("sessionUser", adminUser);
            return IMoocJSONResult.ok(adminUser);
        }
        return IMoocJSONResult.errorMsg("登录失败，请重试...");
    }

    @GetMapping("logout")
    public IMoocJSONResult logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("sessionUser");
        return IMoocJSONResult.ok("登出成功");
    }
}

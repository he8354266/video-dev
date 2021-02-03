package com.imooc.controller.interceptor;

import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2611:57
 */
public class MiniInterceptor implements HandlerInterceptor {

    @Autowired
    public RedisOperator redisOperator;
    private static final String USER_REDIS_SESSION = "user-redis-session";

    /**
     * 拦截请求，在controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String userId = httpServletRequest.getHeader("headerUserId");
        String userToken = httpServletRequest.getHeader("headerUserToken");
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String uniqueToken = redisOperator.get(USER_REDIS_SESSION + ":" + userId);
            System.out.println(uniqueToken);
            if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
                System.out.println("请登录。。。。");
                returnErrorResponse(httpServletResponse, IMoocJSONResult.errorMsg("请登录..."));
            return false;
            }else{
                if(!uniqueToken.equals(userToken)){
                    System.out.println("账号被挤出...");
                    returnErrorResponse(httpServletResponse,  IMoocJSONResult.errorTokenMsg("账号被挤出..."));
                    return false;
                }
            }
        }else {
            System.out.println("请登录。。。。。");
            returnErrorResponse(httpServletResponse, IMoocJSONResult.errorMsg("请登录..."));
            return false;
        }
        return true;
    }

    public void returnErrorResponse(HttpServletResponse response, IMoocJSONResult result) throws IOException {
        OutputStream outputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            outputStream = response.getOutputStream();
            outputStream.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

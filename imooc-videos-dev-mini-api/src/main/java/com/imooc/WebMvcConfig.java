package com.imooc;

import com.imooc.controller.interceptor.MiniInterceptor;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2614:36
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").addResourceLocations("file:C:/imooc_videos_dev/");

    }

    @Bean
    public MiniInterceptor miniInterceptor() {
        return new MiniInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**").addPathPatterns("/video/upload", "/video/uploadCover",
//                "/video/userLike", "/video/userUnLike","/video/getVideoComments",
//                "/video/saveComment", "/bgm/**").excludePathPatterns("/user/queryPublisher");
        super.addInterceptors(registry);
    }
}

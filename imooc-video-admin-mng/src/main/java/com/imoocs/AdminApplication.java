package com.imoocs;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/815:38
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/8 15:38
 * @updateDate 2021/2/8 15:38
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.mapper")
@ComponentScan(basePackages= {"com.imoocs"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}

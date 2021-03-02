package com.easypoi;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/239:28
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/23 9:28
 * @updateDate 2021/2/23 9:28
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.easypoi.mapper")
public class EasyPoiController {
    public static void main(String[] args) {
        SpringApplication.run(EasyPoiController.class, args);
    }
}

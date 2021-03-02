package com.imoocs.bean;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/1815:51
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/18 15:51
 * @updateDate 2021/2/18 15:51
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {
    private String usertoken;
    private String username;
    private String password;
}

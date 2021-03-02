package com.imooc.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/816:02
 */

import com.imooc.pojo.Users;
import com.imooc.utils.PagedResult;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/8 16:02
 * @updateDate 2021/2/8 16:02
 **/
public interface UsersService {
    PagedResult queryUsers(Users users, Integer page, Integer pageSize);
}

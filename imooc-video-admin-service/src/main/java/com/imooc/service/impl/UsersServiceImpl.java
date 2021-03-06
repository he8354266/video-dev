package com.imooc.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/816:08
 */

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.UsersService;
import com.imooc.utils.PagedResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/8 16:08
 * @updateDate 2021/2/8 16:08
 **/
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper = null;

    @Override
    public PagedResult queryUsers(Users users, Integer page, Integer pageSize) {
        String username = "";
        String nickname = "";
        if (users != null) {
            username = users.getUsername();
            nickname = users.getNickname();
        }
        PageHelper.startPage(page, pageSize);
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        if (StringUtils.isNotBlank(username)) {
            criteria.andLike("username", "%" + username + "%");
        }
        List<Users> userList = usersMapper.selectByExample(userExample);
        PageInfo<Users> pageList = new PageInfo<>(userList);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(userList);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }
}

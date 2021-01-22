package com.imooc.service.impl;

import com.imooc.mapper.UsersFansMapper;
import com.imooc.mapper.UsersLikeVideosMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.UsersReportMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.UsersFans;
import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.UsersReport;
import com.imooc.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2117:37
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper = null;

    @Autowired
    private UsersFansMapper usersFansMapper = null;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper = null;
    @Autowired
    private UsersReportMapper usersReportMapper = null;

    @Autowired
    private Sid sid;

    @Override
    public boolean queryUsernameIsExist(String username) {
        Example example = new Example(Users.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<Users> users = usersMapper.selectByExample(example);
        return users.size() > 0 ? true : false;
    }

    @Override
    public void saveUser(Users user) {
        user.setId(sid.nextShort());
        usersMapper.insert(user);
    }

    @Override
    public Users queryUserForLogin(String username, String password) {
        return null;
    }

    @Override
    public void updateUserInfo(Users user) {
        Example example = new Example(Users.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Users users = new Users();
        users.setId(userId);
        Users users1 = usersMapper.selectOne(users);
        return users1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isUserLikeVideo(String userId, String videoId) {
        Example example = new Example(UsersLikeVideos.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);
        List<UsersLikeVideos> usersLikeVideosList = usersLikeVideosMapper.selectByExample(example);
        return usersLikeVideosList.size() > 0 && !usersLikeVideosList.isEmpty() ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveUserFanRelation(String userId, String fanId) {
        UsersFans usersFans = new UsersFans();
        usersFans.setId(sid.nextShort());
        usersFans.setUserId(userId);
        usersFans.setFanId(fanId);
        usersFansMapper.insert(usersFans);
        usersMapper.addFansCount(userId);
        usersMapper.addFollersCount(userId);
    }

    @Override
    public void deleteUserFanRelation(String userId, String fanId) {
        UsersFans usersFans = new UsersFans();
        usersFans.setUserId(userId);
        usersFans.setFanId(fanId);
        usersFansMapper.delete(usersFans);
        usersMapper.reduceFansCount(userId);
        usersMapper.reduceFollersCount(userId);
    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {
        Example example = new Example(UsersFans.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);
        List<UsersFans> list = usersFansMapper.selectByExample(example);
        return list.size() > 0 && list != null && !list.isEmpty() ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void reportUser(UsersReport userReport) {
        userReport.setId(sid.nextShort());
        userReport.setCreateDate(new Date());
        usersReportMapper.insert(userReport);
    }
}

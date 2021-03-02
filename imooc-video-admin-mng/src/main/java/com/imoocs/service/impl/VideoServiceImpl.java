package com.imoocs.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/1816:38
 */

import com.imooc.mapper.BgmMapper;
import com.imooc.mapper.UsersReportMapperCustom;
import com.imooc.mapper.VideosMapper;
import com.imooc.pojo.Bgm;
import com.imoocs.service.VideoService;
import com.imooc.utils.PagedResult;
import com.imoocs.utils.ZKCurator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/18 16:38
 * @updateDate 2021/2/18 16:38
 **/
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper = null;
    @Autowired
    private BgmMapper bgmMapper = null;
    @Autowired
    private Sid sid = null;
    @Autowired
    private ZKCurator zkCurator;

    @Autowired
    private UsersReportMapperCustom usersReportMapperCustom;
    @Override
    public void addBgm(Bgm bgm) {

    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public void deleteBgm(String id) {

    }

    @Override
    public PagedResult queryReportList(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public void updateVideoStatus(String videoId, Integer status) {

    }
}

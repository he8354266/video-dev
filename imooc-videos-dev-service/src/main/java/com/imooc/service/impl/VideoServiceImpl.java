package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.*;
import com.imooc.pojo.Comments;
import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.CommentsVO;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import com.imooc.utils.TimeAgoUtils;
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
 * @date 2021/1/259:09
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper = null;
    @Autowired
    private UsersMapper usersMapper = null;
    @Autowired
    private SearchRecordsMapper searchRecordsMapper = null;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper = null;
    @Autowired
    private CommentsMapper commentMapper = null;

    @Autowired
    private CommentsMapperCustom commentMapperCustom = null;

    @Autowired
    private VideosMapperCustom videosMapperCustom = null;

    @Autowired
    private Sid sid = null;

    @Override
    public String saveVideo(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        videosMapper.insert(video);
        return video.getId();
    }

    @Override
    public void updateVideo(String videoId, String coverPath) {
        Videos videos = new Videos();
        videos.setId(videoId);
        videos.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(videos);
    }

    @Override
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);
        PageInfo<VideosVO> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRows(list);
        pagedResult.setRecords(pagedResult.getTotal());
        pagedResult.setTotal(pageList.getPages());
        return pagedResult;
    }

    @Override
    public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);
        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setRows(list);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Override
    public List<String> getHotwords() {
        return searchRecordsMapper.getHotwords();
    }

    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setId(sid.nextShort());
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        usersLikeVideosMapper.insert(usersLikeVideos);
        //视频喜欢数量增加
        videosMapperCustom.addVideoLikeCount(videoId);

        usersMapper.addReceiveLikeCount(userId);
    }

    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        Example example = new Example(UsersLikeVideos.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);
        usersLikeVideosMapper.deleteByExample(example);

        //视频喜欢数量累减
        videosMapperCustom.reduceVideoLikeCount(videoId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        comment.setId(sid.nextShort());
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CommentsVO> list = commentMapperCustom.queryComments(videoId);
        for (CommentsVO c : list) {

            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
            System.out.println(c);
        }
        PageInfo<CommentsVO> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setRows(list);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }
}

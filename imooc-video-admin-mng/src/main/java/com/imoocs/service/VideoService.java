package com.imoocs.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/1816:23
 */

import com.imooc.pojo.Bgm;
import com.imooc.utils.PagedResult;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/18 16:23
 * @updateDate 2021/2/18 16:23
 **/
public interface VideoService {
    public void addBgm(Bgm bgm);

    public PagedResult queryBgmList(Integer page, Integer pageSize);

    public void deleteBgm(String id);

    public PagedResult queryReportList(Integer page, Integer pageSize);

    public void updateVideoStatus(String videoId, Integer status);
}

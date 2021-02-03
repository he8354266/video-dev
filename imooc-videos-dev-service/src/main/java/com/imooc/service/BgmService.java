package com.imooc.service;

import com.imooc.pojo.Bgm;
import com.imooc.utils.PagedResult;

import java.util.List;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2217:15
 */
public interface BgmService {

    /**
     * @Description: 查询背景音乐列表
     */
    public List<Bgm> queryBgmList();

    /**
     * @Description: 根据id查询bgm信息
     */
    public Bgm queryBgmById(String bgmId);

    public PagedResult queryBgmPage(Integer page, Integer pageSize);
}

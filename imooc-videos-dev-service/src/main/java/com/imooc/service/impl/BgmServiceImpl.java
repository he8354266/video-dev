package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.BgmMapper;
import com.imooc.pojo.Bgm;
import com.imooc.service.BgmService;
import com.imooc.utils.PagedResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/1/2217:29
 */
@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;
    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Bgm queryBgmById(String bgmId) {
        return bgmMapper.selectByPrimaryKey(bgmId);
    }

    @Override
    public PagedResult queryBgmPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        PageInfo<Bgm> pageInfo = new PageInfo<>();
        List<Bgm> bgms = bgmMapper.selectAll();
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRows(bgms);
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfo.getPageNum());
        pagedResult.setRecords(pageInfo.getTotal());
        return pagedResult;
    }
}

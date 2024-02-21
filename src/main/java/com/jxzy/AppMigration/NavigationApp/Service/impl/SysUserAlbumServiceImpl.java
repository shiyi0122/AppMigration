package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserAlbumService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserAlbumMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserAlbum;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/15 14:16
 */
@Service
public class SysUserAlbumServiceImpl implements SysUserAlbumService {

    @Autowired
    SysUserAlbumMapper sysUserAlbumMapper;

    /**
     * 添加
     * @param sysUserAlbum
     * @return
     */
    @Override
    public int addSysUserAlbum(SysUserAlbum sysUserAlbum) {

        sysUserAlbum.setId(IdUtils.getSeqId());
         sysUserAlbum.setDate(DateUtil.crutDate());
        sysUserAlbum.setCreateTime(DateUtil.currentDateTime());
        sysUserAlbum.setUpdateTime(DateUtil.currentDateTime());
        int i = sysUserAlbumMapper.insert(sysUserAlbum);
        return i;
    }

    /**
     *  我的相册列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult sysUserAlbumAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysUserAlbum> list = sysUserAlbumMapper.list(search);
        if (list.size()>0){
            PageInfo<SysUserAlbum> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;

    }
}

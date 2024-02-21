package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysSpotLabelService;
import com.jxzy.AppMigration.NavigationApp.dao.SysSpotLabelMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLabel;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 10:19
 */
@Service
public class SysSpotLabelServiceImpl implements SysSpotLabelService {

    @Autowired
    SysSpotLabelMapper sysSpotLabelMapper;

    @Override
    public int addSysSpotLabel(SysSpotLabel sysSpotLabel) {

        sysSpotLabel.setId(IdUtils.getSeqId());
        sysSpotLabel.setCreateTime(DateUtil.currentDateTime());
        sysSpotLabel.setUpdateTime(DateUtil.currentDateTime());
        int i = sysSpotLabelMapper.insertSelective(sysSpotLabel);
        return i;
    }

    @Override
    public int editSysSpotLabel(SysSpotLabel sysSpotLabel) {

        sysSpotLabel.setUpdateTime(DateUtil.currentDateTime());
        int i = sysSpotLabelMapper.updateSelective(sysSpotLabel);
        return i;

    }

    @Override
    public int delSysSpotLabel(Long id) {

       int i = sysSpotLabelMapper.deleteByPrimaryKey(id);

       return i;
    }

    @Override
    public PageDataResult getSysSpotLabelListList(Integer pageNum, Integer pageSize, String content) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysSpotLabel> list = sysSpotLabelMapper.list(content);
        if (list.size()>0){
            PageInfo<SysSpotLabel> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    @Override
    public List<SysSpotLabel> sysSpotLabelDrop() {

        List<SysSpotLabel> list = sysSpotLabelMapper.sysSpotLabelDrop();

        return list;
    }
}

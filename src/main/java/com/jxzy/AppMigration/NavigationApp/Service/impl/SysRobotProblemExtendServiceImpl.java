package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysRobotProblemExtendService;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotCorpusMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotProblemExtendMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotCorpus;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotProblemExtend;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/11/3 9:05
 */
@Service
public class SysRobotProblemExtendServiceImpl implements SysRobotProblemExtendService {

    @Autowired
    SysRobotProblemExtendMapper sysRobotProblemExtendMapper;

    @Autowired
    SysRobotCorpusMapper sysRobotCorpusMapper;


    @Override
    public int addSysRobotProblemExtend(SysRobotProblemExtend sysRobotProblemExtend) {
        SysRobotCorpus sysRobotCorpus = sysRobotCorpusMapper.selectByPrimaryKey(sysRobotProblemExtend.getCorpusId());
        sysRobotProblemExtend.setExtendId(IdUtils.getSeqId());
        sysRobotProblemExtend.setExtendCorpusPinyin(Tinypinyin.tinypinyin(sysRobotProblemExtend.getExtendCorpusProblem()));
        sysRobotProblemExtend.setExtendType("1");
        sysRobotProblemExtend.setCreateDate(DateUtil.currentDateTime());
        sysRobotProblemExtend.setUpdateDate(DateUtil.currentDateTime());
        sysRobotProblemExtend.setScenicSpotId(Long.parseLong(sysRobotCorpus.getGenericType()));
        int i =sysRobotProblemExtendMapper.insertSelective(sysRobotProblemExtend);
        return i;

    }

    /**
     * 资源详情修改
     * @param sysRobotProblemExtend
     * @return
     */
    @Override
    public int editSysRobotProblemExtend(SysRobotProblemExtend sysRobotProblemExtend) {

        sysRobotProblemExtend.setExtendCorpusPinyin(Tinypinyin.tinypinyin(sysRobotProblemExtend.getExtendCorpusProblem()));
        sysRobotProblemExtend.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotProblemExtendMapper.updateByPrimaryKeySelective(sysRobotProblemExtend);


    }

    /**
     * 删除
     * @param sysRobotProblemExtend
     * @return
     */
    @Override
    public int delSysRobotProblemExtend(SysRobotProblemExtend sysRobotProblemExtend) {


       int i =  sysRobotProblemExtendMapper.deleteByPrimaryKey(sysRobotProblemExtend.getExtendId());

       return i;

    }

    @Override
    public PageDataResult getSysRobotProblemExtendList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
        List<SysRobotProblemExtend> list =  sysRobotProblemExtendMapper.getSysRobotProblemExtendList(pageDTO.getId());

        if (list.size()>0){

            PageInfo<SysRobotProblemExtend> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);

        }
        return pageDataResult;
    }
}

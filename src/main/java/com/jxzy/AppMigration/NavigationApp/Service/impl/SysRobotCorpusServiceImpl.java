package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysRobotCorpusService;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotCorpusMapper;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotShopsExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotCorpus;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/7 19:18
 */
@Service
public class SysRobotCorpusServiceImpl implements SysRobotCorpusService {

    @Autowired
    SysRobotCorpusMapper sysRobotCorpusMapper;

    @Override
    public  List<SysRobotCorpus> getSysRobotCorpus(String name) {

       List<SysRobotCorpus> sysRobotCorpus = sysRobotCorpusMapper.getSysRobotCorpus(name);

       return sysRobotCorpus;
    }

    /**
     * 后台管理--语音语义列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysRobotCorpusList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotCorpus> list = sysRobotCorpusMapper.getSysRobotCorpusList(search);
        if (list.size()>0){
            PageInfo<SysRobotCorpus> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理--添加语音语义
     * @param sysRobotCorpus
     * @return
     */
    @Override
    public int addSysRobotCorpus(SysRobotCorpus sysRobotCorpus) {
        sysRobotCorpus.setCorpusId(IdUtils.getSeqId());
        sysRobotCorpus.setGenericType("2019924");
        sysRobotCorpus.setPinYinProblem(Tinypinyin.tinypinyin(sysRobotCorpus.getCorpusProblem()));
        sysRobotCorpus.setCreateDate(DateUtil.currentDateTime());
        sysRobotCorpus.setUpdateDate(DateUtil.currentDateTime());
        int i = sysRobotCorpusMapper.insertSelective(sysRobotCorpus);
        return i;
    }

    /**
     * 后台管理--修改语音语义
     * @param sysRobotCorpus
     * @return
     */
    @Override
    public int editSysRobotCorpus(SysRobotCorpus sysRobotCorpus) {

        sysRobotCorpus.setUpdateDate(DateUtil.currentDateTime());
        sysRobotCorpus.setPinYinProblem(Tinypinyin.tinypinyin(sysRobotCorpus.getCorpusProblem()));
        int i = sysRobotCorpusMapper.updateByPrimaryKeySelective(sysRobotCorpus);

        return i;
    }

    /**
     * 删除语音语义
     * @param id
     * @return
     */
    @Override
    public int delSysRobotCorpus(Long id) {

        int i = sysRobotCorpusMapper.delSysRobotCorpus(id);
        return i;
    }


    /**
     * 语音语义管理
     * @param search
     * @return
     */
    @Override
    public List<SysRobotCorpus> uploadExcelRobotCorpus(Map<String, Object> search) {

        List<SysRobotCorpus> list = sysRobotCorpusMapper.getSysRobotCorpusList(search);

        return list;
    }
}

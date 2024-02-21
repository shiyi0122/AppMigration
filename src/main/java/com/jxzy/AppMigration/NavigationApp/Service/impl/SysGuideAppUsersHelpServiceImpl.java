package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.db.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersHelpService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersHelpMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysGuideAppUsersHelpServiceImpl implements SysGuideAppUsersHelpService {
    @Autowired
    private SysGuideAppUsersHelpMapper sysGuideAppUsersHelpMapper;

    /**
     * 获取使用帮助列表
     * @param:
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    public List<SysGuideAppUsersHelp> queryUserHelpList() {
        return sysGuideAppUsersHelpMapper.queryUserHelpList();
    }

    /**
     * 查询使用帮助
     * @param: helpId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    public SysGuideAppUsersHelp queryUserHelp(Long helpId) {
        return sysGuideAppUsersHelpMapper.selectByPrimaryKey(helpId);
    }

    /**
     * 使用帮助搜索
     * @param: helpName
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    public List<SysGuideAppUsersHelp> queryUserHelpData(String helpTitle) {
        return sysGuideAppUsersHelpMapper.queryUserHelpData(helpTitle);
    }

    /**
     *  后台管理系统接口--列表查询
     * @param search
     * @return
     */
    @Override
    public PageDataResult getUsersHelpBySearch(Map<String, Object> search,Integer pageNum,Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysGuideAppUsersHelp> list = sysGuideAppUsersHelpMapper.getUsersHelpBySearch(search);

        if (list.size()>0){
            PageInfo<SysGuideAppUsersHelp> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 添加使用帮助
     * @param sysGuideAppUsersHelp
     * @return
     */
    @Override
    public int addAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp) {

        sysGuideAppUsersHelp.setHelpId(IdUtils.getSeqId());
        sysGuideAppUsersHelp.setCreateDate(DateUtil.currentDateTime());
        sysGuideAppUsersHelp.setUpdateDate(DateUtil.currentDateTime());
        int i = sysGuideAppUsersHelpMapper.insertSelective(sysGuideAppUsersHelp);
        return i;
    }

    /**
     * 修改使用帮助
     * @param sysGuideAppUsersHelp
     * @return
     */
    @Override
    public int editAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp) {

        sysGuideAppUsersHelp.setUpdateDate(DateUtil.currentDateTime());
        int i = sysGuideAppUsersHelpMapper.updateByPrimaryKeySelective(sysGuideAppUsersHelp);
        return i ;
    }
    /**
     * 删除使用帮助
     * @param id
     * @return
     */
    @Override
    public int delAppUsersHelp(String id) {

        int i = sysGuideAppUsersHelpMapper.deleteByPrimaryKey(Long.parseLong(id));
        return  i ;
    }

}

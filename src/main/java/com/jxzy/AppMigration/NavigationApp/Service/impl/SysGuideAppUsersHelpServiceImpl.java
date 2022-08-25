package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersHelpService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersHelpMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}

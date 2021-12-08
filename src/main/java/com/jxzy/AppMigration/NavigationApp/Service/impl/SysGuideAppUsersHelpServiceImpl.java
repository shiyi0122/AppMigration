package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersHelpService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersHelpMapper;
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
}

package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysConfigsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysConfigsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysConfigsServiceImpl implements SysConfigsService {
    @Autowired
    private SysConfigsMapper sysConfigsMapper;

    /**
     * 关于我们
     * @param: configsId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysConfigs
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    public SysConfigs queryAboutUs(long configsId) {
        return sysConfigsMapper.selectByPrimaryKey(configsId);
    }
}

package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysRobotMapResService;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotMapResMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRobotMapResServiceImpl implements SysRobotMapResService {
    @Autowired
    private SysRobotMapResMapper sysRobotMapResMapper;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址

    /**
     * 查询地图资源
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    public List<SysRobotMapRes> queryMapRes(Map<String, Object> search) {
        List<SysRobotMapRes> res = sysRobotMapResMapper.queryMapRes(search);
        for (int i = 0; i < res.size(); i++) {
            res.get(i).setResUrl(DOMAIN_NAME + res.get(i).getResUrl());
        }
        return res;
    }
}

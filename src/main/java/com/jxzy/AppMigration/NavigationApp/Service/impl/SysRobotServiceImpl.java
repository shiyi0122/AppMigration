package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysRobotService;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRobotServiceImpl implements SysRobotService {
    @Autowired
    private SysRobotMapper sysRobotMapper;


    /**
     * 根据机器人ID查询机器人或者查询所有机器人
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/3 0003
     */
    public List<SysRobot> queryRobotList(Map<String, Object> search) {
        return sysRobotMapper.queryRobotList(search);
    }
}

package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotBroadcastServiceImpl implements SysScenicSpotBroadcastService {
    @Autowired
    private SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址
    /**
     *
     * @param: pageNum 当前页
     * @param: pageSize当前页总条数
     * @param: search  map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcast(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcast> broadcast = sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcast(search);
        for (int i =0;i<broadcast.size();i++) {
            List<SysScenicSpotBroadcastExtendWithBLOBs> sysScenicSpotBroadcastExtend = broadcast.get(i).getSysScenicSpotBroadcastExtend();
            if (sysScenicSpotBroadcastExtend.size() > 0) {
                for (int j = 0; j < sysScenicSpotBroadcastExtend.size(); j++) {
                    sysScenicSpotBroadcastExtend.get(j).setPictureUrl(DOMAIN_NAME+sysScenicSpotBroadcastExtend.get(j).getPictureUrl());
                }
            }
        }
        return sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcast(search);
    }

    /**
     * 查询景区停靠点
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotBroadcast> queryScenicSpotStop(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotBroadcastMapper.queryScenicSpotStop(search);
    }
}

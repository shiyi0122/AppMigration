package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRecommendedRouteService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRecommendedRouteMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotRecommendedRouteServiceImpl implements SysScenicSpotRecommendedRouteService {
    @Autowired
    private SysScenicSpotRecommendedRouteMapper sysScenicSpotRecommendedRouteMapper;
    /**
     * 查询景区景点路线列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotRecommendedRoute> queryScenicSpotRecommendedRouteList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotRecommendedRouteMapper.queryScenicSpotRecommendedRouteList(search);
    }
}

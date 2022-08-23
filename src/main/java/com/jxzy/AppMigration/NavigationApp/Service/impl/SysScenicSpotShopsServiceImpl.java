package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotShopsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotShopsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotShopsServiceImpl implements SysScenicSpotShopsService {

    @Autowired
    private SysScenicSpotShopsMapper sysScenicSpotShopsMapper;

    /**
     * 查询商品店铺详情
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    public List<SysScenicSpotShops> queryScenicShopsList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotShopsMapper.queryScenicShopsList(search);
    }
}

package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotServiceResService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotServiceResMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotServiceResServiceImpl implements SysScenicSpotServiceResService {
    @Autowired
    private SysScenicSpotServiceResMapper sysScenicSpotServiceResMapper;
    /**
     * 查询洗手间列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotServiceRes> queryToiletList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        return sysScenicSpotServiceResMapper.queryToiletList(search);
    }
}

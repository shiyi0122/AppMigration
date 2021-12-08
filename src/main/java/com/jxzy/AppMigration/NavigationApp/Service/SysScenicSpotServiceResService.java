package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotServiceResService {
    /**
     * 查询洗手间列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotServiceRes> queryToiletList(int pageNum, int pageSize, Map<String, Object> search);
}

package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBindingService {
    /**
     * 获取景区和城市列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/1 0001
     */
    List<SysScenicSpotBinding> queryCityAndScenicSpotLists(int pageNum, int pageSize, Map<String, Object> search);
}

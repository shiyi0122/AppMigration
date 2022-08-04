package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection;

import java.util.Map;

public interface SysUserScenicFabulousCollectionService {

    /**
     * 查询用户景点点赞或收藏
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    SysUserScenicFabulousCollection queryUserFabulousCollection(Map<String, Object> search);

    /**
     * 创建用户景点点赞或收藏
     * @param: scenics
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int insetUserFabulousCollection(SysUserScenicFabulousCollection scenics);

    /**
     * 更新用户景点点赞或收藏
     * @param: scenic
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int updateUserFabulousCollection(SysUserScenicFabulousCollection scenic);
}

package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;

import java.util.List;
import java.util.Map;

public interface SysGuideAppUsersMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysGuideAppUsers record);

    int insertSelective(SysGuideAppUsers record);

    SysGuideAppUsers selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysGuideAppUsers record);

    int updateByPrimaryKey(SysGuideAppUsers record);

    /**
     * 查询令牌失效性
     * @param: longinTokenId
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    SysGuideAppUsers getToken(String longinTokenId);

    /**
     * 唯一标识查询用户信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    SysGuideAppUsers getPhoneSign(Map<String, Object> search);

    SysGuideAppUsers getUid(Long uid);

    SysGuideAppUsers selectPhoneByUser(String phone);

    List<SysGuideAppUsers> selectBySearch(Map<String, Object> search);
}
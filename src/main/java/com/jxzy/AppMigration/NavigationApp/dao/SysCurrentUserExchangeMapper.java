package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysCurrentUserExchange;

import java.util.List;
import java.util.Map;

public interface SysCurrentUserExchangeMapper {
    int deleteByPrimaryKey(Long exchangeId);

    int insert(SysCurrentUserExchange record);

    int insertSelective(SysCurrentUserExchange record);

    SysCurrentUserExchange selectByPrimaryKey(Long exchangeId);

    int updateByPrimaryKeySelective(SysCurrentUserExchange record);

    int updateByPrimaryKey(SysCurrentUserExchange record);

    /**
     * 查询兑奖信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    SysCurrentUserExchange exchangePrize(Map<String, Object> search);

    /**
     * 查询奖品列表（分页）
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    List<SysCurrentUserExchange> queryExchangePrizeList(Map<String, Object> search);
}
package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysCurrentUserExchange;

import java.util.List;
import java.util.Map;

public interface SysCurrentUserExchangeService {
    /**
     * 查询兑奖信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    SysCurrentUserExchange exchangePrize(Map<String, Object> search);

    /**
     * 更改兑奖状态
     * @param: exchange
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    int updateExchangePrizeState(SysCurrentUserExchange exchange);

    /**
     * 查询奖品列表（分页）
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    List<SysCurrentUserExchange> queryExchangePrizeList(int pageNum, int pageSize, Map<String, Object> search);
}

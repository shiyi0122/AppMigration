package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysCurrentUserExchangeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysCurrentUserExchangeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysCurrentUserExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysCurrentUserExchangeServiceImpl implements SysCurrentUserExchangeService {
    @Autowired
    private SysCurrentUserExchangeMapper sysCurrentUserExchangeMapper;

    /**
     * 查询兑奖信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    public SysCurrentUserExchange exchangePrize(Map<String, Object> search) {
        return sysCurrentUserExchangeMapper.exchangePrize(search);
    }

    /**
     * 更改兑奖状态
     * @param: exchange
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    public int updateExchangePrizeState(SysCurrentUserExchange exchange) {
        exchange.setExchangeState("1");
        return sysCurrentUserExchangeMapper.updateByPrimaryKeySelective(exchange);
    }

    /**
     * 查询奖品列表（分页）
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    public List<SysCurrentUserExchange> queryExchangePrizeList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysCurrentUserExchangeMapper.queryExchangePrizeList(search);
    }
}

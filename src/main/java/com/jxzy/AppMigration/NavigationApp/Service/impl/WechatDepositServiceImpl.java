package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jxzy.AppMigration.NavigationApp.Service.WechatDepositService;
import com.jxzy.AppMigration.NavigationApp.dao.SysCurrenUserMapper;
import com.jxzy.AppMigration.NavigationApp.dao.WechatDepositMapper;
import com.jxzy.AppMigration.NavigationApp.entity.APIReturnResult;
import com.jxzy.AppMigration.NavigationApp.entity.SysCurrenUser;
import com.jxzy.AppMigration.NavigationApp.entity.WechatDeposit;
import com.jxzy.AppMigration.NavigationApp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/8/7 11:22
 */
@Service
public class WechatDepositServiceImpl implements WechatDepositService {

    @Autowired
    WechatDepositMapper wechatDepositMapper;

    @Autowired
    SysCurrenUserMapper sysCurrenUserMapper;


//    @Override
//    public Map<String, Object> addWechatDeposit(String userId, String scenicSpotId) {
//
//        WechatDeposit wechatDeposit = new WechatDeposit();
//
//
//
//
//        return new HashMap<>();
//    }
    @DS("master2")
    @Override
    public int insertSelective(WechatDeposit wechatDeposit) {

       int i =  wechatDepositMapper.insertSelective(wechatDeposit);

       return i;
    }

    /**
     * 根据订单编号查询订单
     * @param tradeNo
     * @return
     */
    @DS("master2")
    @Override
    public WechatDeposit getTransactLogsById(String tradeNo) {

        WechatDeposit wechatDeposit = wechatDepositMapper.getTransactLogsById(tradeNo);

        return wechatDeposit;
    }

    /**
     * 押金支付回调
     * @param out_trade_no
     * @param objectId
     * @param type
     * @param payMoney
     * @return
     */
    @DS("master2")
    @Override
    public APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney) {

        WechatDeposit transactLogsById = wechatDepositMapper.getTransactLogsById(out_trade_no);

        try{
            if (StringUtils.isEmpty(transactLogsById)){
                return APIReturnResult.error("没有查到消费记录信息！");
            }
            transactLogsById.setDepositState("30");
            transactLogsById.setReturnResultCode("SUCCESS");
            wechatDepositMapper.updateWechatDeposit(transactLogsById);
            SysCurrenUser sysCurrenUser = sysCurrenUserMapper.selectUserIdByUser(transactLogsById.getDepositUserId());
            sysCurrenUser.setDepositPayState("10");
            sysCurrenUser.setDepositPayTime(DateUtil.currentDateTime());
            sysCurrenUser.setScenicSpotId("");
            sysCurrenUserMapper.updateByPrimaryKeySelective(sysCurrenUser);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 修改押金订单
     * @param wechatDeposit
     * @return
     */
    @DS("master2")
    @Override
    public int updateWechatDeposit(WechatDeposit wechatDeposit) {

        wechatDeposit.setUpdateDate(DateUtil.currentDateTime());
        int i = wechatDepositMapper.updateWechatDeposit(wechatDeposit);

        return i;
    }
}

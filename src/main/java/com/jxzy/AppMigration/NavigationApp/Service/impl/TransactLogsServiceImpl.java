package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jxzy.AppMigration.NavigationApp.Service.BaseService;
import com.jxzy.AppMigration.NavigationApp.Service.TransactLogsService;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessTransactLogsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessUserLiveDeviceLogMapper;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessUserLookTimeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.APIReturnResult;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrder;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessTransactLogs;
import com.jxzy.AppMigration.NavigationApp.util.DateUtil;
import com.jxzy.AppMigration.NavigationApp.util.IDBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 *  @Description: 交易记录接口实现类
 *  @Date: 2020-01-09 19:27
 *  @version: 1.0
 *  @Copyright: 2019 http://www.fenghuaapp.com/ Inc. All rights reserved.
 *  注意：本内容仅限于风华正茂科技(北京)有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@Transactional
@Service("transactLogsService")
public class TransactLogsServiceImpl implements TransactLogsService {

    @Autowired
    private BusinessTransactLogsMapper businessTransactLogsMapper;

    @Autowired
    private BaseService baseService;

    @Autowired
    private BusinessUserLiveDeviceLogMapper businessUserLiveDeviceLogMapper;

    @Autowired
    private BusinessUserLookTimeMapper businessUserLookTimeMapper;

    @DS("master2")
    @Override
    public BusinessTransactLogs getTransactLogsById(String id) {
        return businessTransactLogsMapper.selectByPrimaryKey(id);
    }

    /***
     * 支付宝或者微信支付成功的业务处理
     * @param out_trade_no 订单编号
     * @param objectId 对象id  1竞拍保证金 2限时购保证金 不为空
     * @param type  0注册保证金 1竞拍保证金 2限时购保证金 3订阅, 4直播支付
     * @param payMoney 支付金额
     * @return
     */
    @DS("master2")
    @Override
    public APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney)throws Exception {
        BusinessTransactLogs log = getTransactLogsById(out_trade_no);//查询预支付订单
        if(log == null ){
            return APIReturnResult.error("没有查到消费记录信息！");
        }
        type = String.valueOf(log.getType());
        BusinessOrder order = new BusinessOrder();
        if ("4".equals(type)){//4直播支付订单
            dealOrderInfo(log,order);
            businessUserLiveDeviceLogMapper.editIfPayState(log.getLiveId());

//            BusinessUserLiveDeviceLog businessUserLiveDeviceLog = businessUserLiveDeviceLogMapper.selectByPrimaryKey(log.getLiveId());
//            Map<String, Object> search = new HashMap<>();
//            search.put("userId",businessUserLiveDeviceLog.getUserId());
//            search.put("robotCode",businessUserLiveDeviceLog.getRobotCode());
//            BusinessUserLookTime businessUserLookTime = businessUserLookTimeMapper.selectBySearch(search);
//            if (StringUtils.isEmpty(businessUserLookTime)){//用户之前无此机器人购买记录
//                BusinessUserLookTime businessUserLookTime1 = new BusinessUserLookTime();
//                businessUserLookTime1.setId(IDBuilder.getSeqId());
//                businessUserLookTime1.setViewingTime(businessUserLiveDeviceLog.getWatchedDuration());
//                businessUserLookTime1.setUserId(businessUserLiveDeviceLog.getUserId());
//                businessUserLookTime1.setRobotCode(businessUserLiveDeviceLog.getRobotCode());
//                businessUserLookTime1.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
//                businessUserLookTime1.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
//                businessUserLookTimeMapper.insertSelective(businessUserLookTime1);
//            }else{//用户之前购买过此机器人直播
//               Long allTime =  Long.parseLong(businessUserLiveDeviceLog.getWatchedDuration()) + Long.parseLong(businessUserLookTime.getViewingTime());
//                businessUserLookTime.setViewingTime(allTime.toString());
//                businessUserLookTimeMapper.updateSelective(businessUserLookTime);
//            }
        }
        baseService.insertObj(order);//保存我的订单消息
        log.setStatus(1);//订单修改成已完成状态
        log.setUpdateTime(DateUtil.date2String(new Date()));
        baseService.updateObj(log);
        return null;
    }
    @DS("master2")
    @Override
    public Integer getTransactLogsCountByPhone(String phone) {
        return businessTransactLogsMapper.getTransactLogsCountByPhone(phone);
    }
    @DS("master2")
    @Override
    public Integer getTransactLogsCountBySearch(Map<String, String> search) {
        return businessTransactLogsMapper.getTransactLogsCountBySearch(search);
    }

    /***
     * 封装订单表数据
     * @param log
     * @param order
     * @throws Exception
     */

    public void dealOrderInfo(BusinessTransactLogs log, BusinessOrder order)throws Exception {
        order.setId(IDBuilder.getSeqId());
        order.setOrderAmount(log.getPrice());
        order.setOrderNumber(log.getId());
        order.setPayType(log.getPayType());
        order.setUserId(log.getUserId());
        order.setCreateTime(DateUtil.date2String(new Date()));
        order.setUpdateTime(DateUtil.date2String(new Date()));
        order.setOrderType(log.getType());

    }
}

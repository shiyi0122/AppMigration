package com.jxzy.AppMigration.NavigationApp.Service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessOrderCommodityMapper;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessOrderUserInformationMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.Service.BusinessOrderService;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessOrderMapper;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/24 17:09
 */
@Service
public class BusinessOrderServiceImpl implements BusinessOrderService {

    @Autowired
    BusinessOrderMapper businessOrderMapper;

    @Autowired
    BusinessOrderCommodityMapper businessOrderCommodityMapper;

    @Autowired
    BusinessOrderUserInformationMapper businessOrderUserInformationMapper;
    /**
     * 添加订单
     * @param
     * @return
     */
    @Override
    public int addBusinessOrderY(BusinessOrderY businessOrderY) {

        String date = DateUtil.crutDate();
        List<BusinessOrderCommodity> commodityList = businessOrderY.getCommodityList();
        List<BusinessOrderUserInformation> userInformationList = businessOrderY.getUserInformationList();

        businessOrderY.setOrderId(IdUtils.getSeqId());
        businessOrderY.setOrderNumber(IdUtils.getYYGOrderNumber(businessOrderY.getPhone()));
        businessOrderY.setOrderStartTime(DateUtil.currentDateTime());
        businessOrderY.setCreateTime(DateUtil.currentDateTime());
        businessOrderY.setUpdateTime(DateUtil.currentDateTime());
        int i =  businessOrderMapper.addBusinessOrderY(businessOrderY);

        for (BusinessOrderCommodity businessOrderCommodity : commodityList) {

            businessOrderCommodity.setId(IdUtils.getSeqId());
            businessOrderCommodity.setCreateTime(DateUtil.currentDateTime());
            businessOrderCommodity.setUpdateTime(DateUtil.currentDateTime());
            businessOrderCommodity.setOrderId(businessOrderY.getOrderId());
            businessOrderCommodityMapper.insert(commodityList);

        }

        for (BusinessOrderUserInformation businessOrderUserInformation : userInformationList) {

            businessOrderUserInformation.setId(IdUtils.getSeqId());
            businessOrderUserInformation.setCreateTime(DateUtil.currentDateTime());
            businessOrderUserInformation.setUpdateTime(DateUtil.currentDateTime());
            businessOrderUserInformation.setOrderId(businessOrderY.getOrderId());
            businessOrderUserInformation.setEffectiveStartDate(date);
            businessOrderUserInformation.setEffectiveEndDate(DateUtil.addDay(date,Integer.parseInt(businessOrderUserInformation.getEffectiveDays())));
            businessOrderUserInformationMapper.insert(businessOrderUserInformation);

        }
        return i;
    }

    @Override
    public BusinessOrderY getBusinessOrderYId(Map<String, Object> search) {

        BusinessOrderY businessOrderY = businessOrderMapper.selectById(search.get("id").toString());

        List<BusinessOrderCommodity> orderCommodityList = businessOrderCommodityMapper.selectByOrderId(search.get("id").toString());

        List<BusinessOrderUserInformation> userInformationList = businessOrderUserInformationMapper.getBusinessOrderId(search.get("id").toString());

        businessOrderY.setCommodityList(orderCommodityList);
        businessOrderY.setUserInformationList(userInformationList);

        return businessOrderY;
    }

    /**
     * 根据用户id获取门票列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getBusinessOrderYByUserIdList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<BusinessOrderY> list = businessOrderMapper.selectBySearch(search);

        for (BusinessOrderY businessOrderY : list) {
           List<BusinessOrderUserInformation> userInformationList =  businessOrderUserInformationMapper.getBusinessOrderId(businessOrderY.getOrderId().toString());
            businessOrderY.setUserInformationList(userInformationList);
            businessOrderY.setNumber(String.valueOf(userInformationList.size()));
        }

        if (list.size()>0){
            PageInfo<BusinessOrderY> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;

    }

    /***
     * 支付宝或者微信支付成功的业务处理
     * @param out_trade_no 订单编号
     * @param objectId 对象id  1竞拍保证金 2限时购保证金 不为空
     * @param type  0注册保证金 1竞拍保证金 2限时购保证金 3订阅, 4直播支付
     * @param payMoney 支付金额
     * @return
     */
    @Override
    public APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney) {
        BusinessOrderY log = getTransactLogsById(out_trade_no);//查询预支付订单
        if(log == null ){
            return APIReturnResult.error("没有查到消费记录信息！");
        }

        log.setOrderStatus("1");//订单修改成已完成状态
        log.setPaymentTime(DateUtil.currentDateTime());
        businessOrderMapper.updateSelective(log);
        return null;

    }



    /**
     * 根据订单编号，查询订单
     * @param orderNumber
     * @return
     */
    @Override
    public BusinessOrderY getTransactLogsById(String orderNumber) {

        BusinessOrderY businessOrderY = businessOrderMapper.selectByOrderNumber(orderNumber);

        return businessOrderY;
    }

}

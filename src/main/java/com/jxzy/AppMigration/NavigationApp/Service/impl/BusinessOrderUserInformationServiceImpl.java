package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.BusinessOrderUserInformationService;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessOrderUserInformationMapper;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderUserInformation;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/5 14:39
 */
@Service
public class BusinessOrderUserInformationServiceImpl implements BusinessOrderUserInformationService {

    @Autowired
    BusinessOrderUserInformationMapper businessOrderUserInformationMapper;

    /**
     * 预约门票
     * @param businessOrderUserInformation
     * @return
     */
    @Override
    public int addBusinessBookingTickets(BusinessOrderUserInformation businessOrderUserInformation) {

        String date = DateUtil.crutDate();
        businessOrderUserInformation.setId(IdUtils.getSeqId());
        businessOrderUserInformation.setCreateTime(DateUtil.currentDateTime());
        businessOrderUserInformation.setUpdateTime(DateUtil.currentDateTime());
        businessOrderUserInformation.setEffectiveStartDate(date);
        businessOrderUserInformation.setEffectiveEndDate(DateUtil.addDay(date,Integer.parseInt(businessOrderUserInformation.getEffectiveDays())));

        int insert = businessOrderUserInformationMapper.insert(businessOrderUserInformation);
        return insert;
    }

    /**
     * 查询我的预约门票
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getBusinessBookingTicketsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<BusinessOrderUserInformation> list = businessOrderUserInformationMapper.selectBySearch(search);

        if (list.size()>0){
            PageInfo<BusinessOrderUserInformation> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;

    }

    /**
     * 根据门票id获取门票详情
     * @param search
     * @return
     */
    @Override
    public BusinessOrderUserInformation getBusinessBookingTicketsDetails(Map<String, Object> search) {

        BusinessOrderUserInformation businessOrderUserInformation = businessOrderUserInformationMapper.selectById(search);

        return businessOrderUserInformation;

    }

    /**
     * 取消预约
     * @param id
     * @return
     */
    @Override
    public int cancellationReservation(String id) {

        int i =  businessOrderUserInformationMapper.cancellationReservation(id);
        return  i;
    }

    /**
     * 定时任务，晚上12点判断门票是否过期
     */
    @Override
    public void getAdmissionfeeTime() {

        String date = DateUtil.crutDate();
        List<BusinessOrderUserInformation> list = businessOrderUserInformationMapper.getBusinessOrderIsUse("1");

        for (BusinessOrderUserInformation businessOrderUserInformation : list) {

            int i = DateUtil.compareTwoDateYYYYMMDD(DateUtil.formatStr2Date(date, "yyyy-MM-dd"), DateUtil.formatStr2Date(businessOrderUserInformation.getEffectiveEndDate(), "yyyy-MM-dd"));

            if (i > 1){
                businessOrderUserInformation.setIsUse("3");
            }
            businessOrderUserInformationMapper.update(businessOrderUserInformation);
        }

    }
}

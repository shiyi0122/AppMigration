package com.jxzy.AppMigration.NavigationApp.Service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.jxzy.AppMigration.NavigationApp.Service.BusinessDurationOptionService;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessDurationOptionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessDurationOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/5/17 19:07
 */
@Service
public class BusinessDurationOptionServiceImpl implements BusinessDurationOptionService {

    @Autowired
    BusinessDurationOptionMapper businessDurationOptionMapper;

    /**
     * 获取直播时长商品选项
     * @return
     */
    @DS("master2")
    @Override
    public List<BusinessDurationOption> getDurationOptionList(String scenicSpotId) {

       List<BusinessDurationOption> list =  businessDurationOptionMapper.getDurationOptionList("1",scenicSpotId);
       if (list.size()  <= 0){
           List<BusinessDurationOption> listN = businessDurationOptionMapper.getSpotIdIsNullList("1");
           return listN;
       }
       return list;

    }

    /**
     * 获取体验时长
     * @return
     */
    @DS("master2")
    @Override
    public BusinessDurationOption getExperienceDuration(String scenicSpotId) {

        BusinessDurationOption businessDurationOption =  businessDurationOptionMapper.getExperienceDuration("2",scenicSpotId);

        if (StringUtils.isEmpty(businessDurationOption)){
            List<BusinessDurationOption> listN =  businessDurationOptionMapper.getSpotIdIsNullList("2");
            return listN.get(0);
        }

        return businessDurationOption;
    }

}

package com.jxzy.AppMigration.NavigationApp.dao;


import com.jxzy.AppMigration.NavigationApp.entity.BusinessDurationOption;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/5/17 19:08
 */
public interface BusinessDurationOptionMapper {

    List<BusinessDurationOption> getDurationOptionList(String type, String scenicSpotId);

    BusinessDurationOption getExperienceDuration(String type , String scenicSpotId);

    List<BusinessDurationOption> getSpotIdIsNullList(String type);

    BusinessDurationOption selectByPrimaryKey(String optionId);


}

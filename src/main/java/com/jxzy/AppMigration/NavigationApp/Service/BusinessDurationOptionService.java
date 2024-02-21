package com.jxzy.AppMigration.NavigationApp.Service;



import com.jxzy.AppMigration.NavigationApp.entity.BusinessDurationOption;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/5/17 19:07
 */
public interface BusinessDurationOptionService {
    List<BusinessDurationOption> getDurationOptionList(String scenicSpotId);


    BusinessDurationOption getExperienceDuration(String scenicSpotId);


}

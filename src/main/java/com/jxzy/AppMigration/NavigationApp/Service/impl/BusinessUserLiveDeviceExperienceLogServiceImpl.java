package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jxzy.AppMigration.NavigationApp.Service.BusinessUserLiveDeviceExperienceLogService;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessDurationOptionMapper;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessUserLiveDeviceExperienceLogMapper;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessUserLookTimeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessDurationOption;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessUserLiveDeviceExperienceLog;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessUserLookTime;
import com.jxzy.AppMigration.NavigationApp.util.DateUtil;
import com.jxzy.AppMigration.NavigationApp.util.IDBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/18 13:28
 * 直播体验时长记录相关接口
 */
@Service
public class BusinessUserLiveDeviceExperienceLogServiceImpl implements BusinessUserLiveDeviceExperienceLogService {

    @Autowired
    BusinessUserLiveDeviceExperienceLogMapper businessUserLiveDeviceExperienceLogMapper;

    @Autowired
    BusinessDurationOptionMapper businessDurationOptionMapper;

    @Autowired
    BusinessUserLookTimeMapper businessUserLookTimeMapper;

    /**
     * 根据机器人编号，和用户id，获取剩余体验直播观看时长
     * @param robotCode
     * @param userId
     * @return
     */
    @DS("master2")
    @Override
    public String getRobotResidueTime(String robotCode, String userId) {

        String s = DateUtil.crutDate();
        Map<String, Object> search = new HashMap<>();
        search.put("nowDay",s);
        search.put("robotCode",robotCode);
        search.put("userId",userId);
        BusinessUserLiveDeviceExperienceLog businessUserLiveDeviceExperienceLog = businessUserLiveDeviceExperienceLogMapper.getRobotResidueTime(search);
        BusinessDurationOption experienceDuration = businessDurationOptionMapper.getExperienceDuration("2",null);

        if (StringUtils.isEmpty(businessUserLiveDeviceExperienceLog)){
            BusinessUserLiveDeviceExperienceLog businessUserLiveDeviceExperienceLogN = new BusinessUserLiveDeviceExperienceLog();
            businessUserLiveDeviceExperienceLogN.setId(IDBuilder.getSeqId());
            businessUserLiveDeviceExperienceLogN.setRobotCode(robotCode);
            businessUserLiveDeviceExperienceLogN.setUserId(userId);
            businessUserLiveDeviceExperienceLogN.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
            businessUserLiveDeviceExperienceLogN.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
            businessUserLiveDeviceExperienceLogN.setRecordingTime(DateUtil.crutDate());
            businessUserLiveDeviceExperienceLogMapper.insertSelective(businessUserLiveDeviceExperienceLogN);

            return experienceDuration.getViewingDuration();
        }else{
            if (Long.parseLong(experienceDuration.getViewingDuration()) > Long.parseLong(businessUserLiveDeviceExperienceLog.getWatchedDuration())){
                //还有试看时间
                Long time = Long.parseLong(experienceDuration.getViewingDuration()) - Long.parseLong(businessUserLiveDeviceExperienceLog.getWatchedDuration());
                return time.toString() ;
            }else{
                return "0";
            }
        }

    }

    /**
     * 添加观看时间后修改的逻辑
     * @param robotCode
     * @param userId
     * @return
     */
    @DS("master2")
    @Override
    public Map<String, Object>  getRobotResidueTimeN(String robotCode, String userId) {
        String s = DateUtil.crutDate();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> search = new HashMap<>();
        search.put("nowDay",s);
        search.put("robotCode",robotCode);
        search.put("userId",userId);
        BusinessUserLookTime businessUserLookTime = businessUserLookTimeMapper.selectBySearch(search);

        if (StringUtils.isEmpty(businessUserLookTime)){//用户没有购买时长，查看是否有体验时长

            BusinessUserLiveDeviceExperienceLog businessUserLiveDeviceExperienceLog = businessUserLiveDeviceExperienceLogMapper.getRobotResidueTime(search);
            BusinessDurationOption experienceDuration = businessDurationOptionMapper.getExperienceDuration("2",null);
            if (StringUtils.isEmpty(businessUserLiveDeviceExperienceLog)){
                map.put("type","1");//体验时长
                map.put("time",experienceDuration.getViewingDuration());
                return map;
            }else{
                if (Long.parseLong(experienceDuration.getViewingDuration()) > Long.parseLong(businessUserLiveDeviceExperienceLog.getWatchedDuration())){
                    //还有试看时间
                    Long time = Long.parseLong(experienceDuration.getViewingDuration()) - Long.parseLong(businessUserLiveDeviceExperienceLog.getWatchedDuration());
                    map.put("type","1");//体验时长
                    map.put("time",time.toString());
                    return map;
                }else{
                    map.put("type","1");//体验时长
                    map.put("time","0");
                    return map;
                }
            }
        }else{//用户购买过此直播

            String viewingTime = businessUserLookTime.getViewingTime();
            map.put("type","2");//用户购买时长
            map.put("time",viewingTime);
            return map;
        }
    }
}

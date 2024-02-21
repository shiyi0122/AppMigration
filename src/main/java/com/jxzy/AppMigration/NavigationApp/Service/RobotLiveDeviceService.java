package com.jxzy.AppMigration.NavigationApp.Service;

import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/25 11:17
 * 机器人直播相关接口
 */
public interface  RobotLiveDeviceService {


    PageDataResult getRobotLiveDeviceList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    Map<String, Object> ifRobotLivePay(String robotCode, String userId);
    Map<String, Object> ifRobotLivePayN(String robotCode, String userId,String optionId);


    int editRobotLivePayTime(String robotCode, String userId, String time);
    int editRobotLivePayTimeN(String robotCode,String userId,String time);

    int addRobotLivePeople(String robotCode, String userId);

    String getRobotLivePeopleNumber(String robotCode, String userId);

    APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney);


    PageInfo<BusinessUserLiveDeviceLog> myBuyLiveBroadcast(Map<String, String> search);


    List<SysRobotLiveDevice> getRobotDevice(Map<String, String> search);


    List<SysScenicSpotBinding> getSpotDevice(Map<String, String> search);


    List<SysScenicSpotBinding> getCityDevice();


    PageInfo<SysRobotLiveDevice> getRecommendRobotDevice(Map<String, Object> search,Integer pageNum,Integer pageSize);


    int updateDeviceStatus(List<SysRobotLiveDevice> liveDevices);


    List<BusinessOnLineUser>  robotLiveBroadcastPush(String robotCode);

    String getBusinessUserById(Long userId);

}

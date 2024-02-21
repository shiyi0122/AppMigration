package com.jxzy.AppMigration.NavigationApp.controller;

/**
 * @Author zhang
 * @Date 2023/5/25 11:15
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessDurationOptionMapper;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessUserLiveDeviceExperienceLogMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.JsonUtils;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.WeChatGtRobotAppPush;
import com.jxzy.AppMigration.NavigationApp.util.jwt.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机器人直播相关接口
 */

@Api(tags = "机器人直播相关接口")
@RestController
@RequestMapping("robotLiveDevice")
public class RobotLiveDeviceController {


    @Autowired
    RobotLiveDeviceService robotLiveDeviceService;

    @Autowired
    BusinessDurationOptionService businessDurationOptionService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @Autowired
    SysGuideAppUsersService sysGuideAppUsersService;

    @Autowired
    BusinessUserLiveDeviceExperienceLogService businessUserLiveDeviceExperienceLogService;

    @ApiOperation("查询直播机器人列表")
    @GetMapping("getRobotLiveDeviceList")
    @ResponseBody
    public PageDataResult getRobotLiveDeviceList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }else{
            search.put("spotId","");
        }

        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid", pageDTO.getUid());
        }

        pageDataResult = robotLiveDeviceService.getRobotLiveDeviceList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }


    /**
     * 生成直播购买订单
     * @param token
     * @param robotCode
     * @param userId
     * @return
     * @throws Exception
     */

    @ApiOperation("生成直播购买订单")
    @GetMapping("ifRobotLivePay")
    @ResponseBody
    public APIReturnResult ifRobotLivePay(String token, String robotCode, String userId,String optionId)throws Exception{

        Map<String, Object>  map  = robotLiveDeviceService.ifRobotLivePayN(robotCode,userId,optionId);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",map);
        return APIReturnResult.ok("获取成功",dataMap);

    }



    /**
     * 修改退出直播时间
     * @param token
     * @param robotCode
     * @param userId
     * @return
     * @throws Exception
     */
    @ApiOperation("修改退出直播时间")
    @GetMapping("editRobotLivePayTime")
    @ResponseBody
    public APIReturnResult editRobotLivePayTime(String token, String robotCode,String userId,String time)throws Exception{

        int i  = robotLiveDeviceService.editRobotLivePayTimeN(robotCode,userId,time);
        Map<String,Object> dataMap = new HashMap<>();
        if (i > 0){
            dataMap.put("data",i);
            return APIReturnResult.ok("修改成功",dataMap);
        }else{
            dataMap.put("data",i);
            return APIReturnResult.ok("修改失败",dataMap);
        }
    }

    /**
     * 添加机器人直播在线人数
     * @param token
     * @param robotCode
     * @param userId
     * @param
     * @return
     * @throws Exception
     */
    @ApiOperation("添加机器人直播在线人数")
    @GetMapping("addRobotLivePeople")
    @ResponseBody
    public APIReturnResult addRobotLivePeople(String token, String robotCode,String userId)throws Exception{

        int i  = robotLiveDeviceService.addRobotLivePeople(robotCode,userId);
        Map<String,Object> dataMap = new HashMap<>();
        if (i > 0){
            dataMap.put("data",i);
            return APIReturnResult.ok("添加成功",dataMap);
        }else{
            dataMap.put("data",i);
            return APIReturnResult.ok("添加失败",dataMap);
        }

    }

//    /**
//     * 减去机器人直播在线人数
//     * @param token
//     * @param robotCode
//     * @param userId
//     * @param
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("reduceRobotLivePeople")
//    public APIReturnResult reduceRobotLivePeople(String token, String robotCode,String userId){
//
//        int i  = sysRobotLiveDeviceService.reduceRobotLivePeople(robotCode,userId);
//        Map<String,Object> dataMap = new HashMap<>();
//        if (i > 0){
//            dataMap.put("data",i);
//            return APIReturnResult.ok("减少成功",dataMap);
//        }else{
//            dataMap.put("data",i);
//            return APIReturnResult.ok("减少失败",dataMap);
//        }
//
//    }

    /**
     * 获取在线人数
     * @param token
     * @param robotCode
     * @param userId
     * @return
     */
    @ApiOperation("获取在线人数")
    @GetMapping("getRobotLivePeopleNumber")
    @ResponseBody
    public APIReturnResult getRobotLivePeopleNumber(String token, String robotCode,String userId){

        String peopleNumber  = robotLiveDeviceService.getRobotLivePeopleNumber(robotCode,userId);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",peopleNumber);
        return APIReturnResult.ok("获取成功",dataMap);

    }

    /**
     * 我的购买直播
     * @param token
     * @param
     * @param userId
     * @return
     */
    @ApiOperation("我的购买直播")
    @GetMapping("myBuyLiveBroadcast")
    @ResponseBody
    public APIReturnResult myBuyLiveBroadcast(String token,Integer pageNum,Integer pageSize,String userId,String scenicSpotName){

        Map<String, String> search = new HashMap<>();
        if (search == null) {
            search = new HashMap<>();
        }
        if (!StringUtils.isEmpty(scenicSpotName)){
            search.put("scenicSpotName",scenicSpotName);
        }
        search.put("userId",userId);

        search.put("pageNum",pageNum.toString());
        search.put("pageSize",pageSize.toString());
        PageInfo<BusinessUserLiveDeviceLog> page  = robotLiveDeviceService.myBuyLiveBroadcast(search);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("pages", page.getPages());
        dataMap.put("pageNum", page.getPageNum());
        dataMap.put("list", page);
        return APIReturnResult.ok("获取成功",dataMap);

    }


    /**
     * 获取直播购买时长选项列表
     */
    @ApiOperation("获取直播购买时长选项列表")
    @GetMapping("getDurationOptionList")
    @ResponseBody
    public APIReturnResult getDurationOptionList(String token,String scenicSpotId) {

        List<BusinessDurationOption> list = businessDurationOptionService.getDurationOptionList(scenicSpotId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        return APIReturnResult.ok("获取成功", map);

    }

    /**
     * 获取体验时长时间
     * @return
     */
    @ApiOperation("获取体验时长时间")
    @GetMapping("getExperienceDuration")
    @ResponseBody
    public APIReturnResult getExperienceDuration(String token,String scenicSpotId) {

        BusinessDurationOption businessDurationOption = businessDurationOptionService.getExperienceDuration(scenicSpotId);
        Map<String, Object> map = new HashMap<>();
        map.put("data", businessDurationOption);
        return APIReturnResult.ok("获取成功", map);

    }

    /**
     * 获取用户此机器人直播剩余体验时长
     * @return
     */
    @ApiOperation("获取用户此机器人直播剩余体验时长")
    @GetMapping("getRobotResidueTime")
    @ResponseBody
    public APIReturnResult getRobotResidueTime(String token,String robotCode,String userId) {

        Map<String, Object>  returnMap = businessUserLiveDeviceExperienceLogService.getRobotResidueTimeN(robotCode,userId);
        Map<String, Object> map = new HashMap<>();
        map.put("data", returnMap);
        return APIReturnResult.ok("获取成功", map);
    }


    /***
     * 获取机器人有直播设备的城市
     * @param token
     * @return
     */
    @ApiOperation("获取机器人有直播设备的城市")
    @GetMapping("city_device")
    @ResponseBody
    public APIReturnResult city_device(String token)throws Exception{
        Map<String,String> search = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(token)){
            Long userId = JwtUtils.getUserIdByToken(token);
            search.put("userId",userId == null?"":userId.toString());
        }
        List<SysScenicSpotBinding> list = robotLiveDeviceService.getCityDevice();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("list",list);

        return APIReturnResult.ok("获取成功",dataMap);
    }



    /***
     * 根据地区id获取地区下有摄像设备的景区
     * @param token
     * @return
     */
    @ApiOperation("根据地区id获取地区下有摄像设备的景区")
    @GetMapping("spot_device")
    @ResponseBody
    public APIReturnResult spot_device(String token,String scenicSpotFid)throws Exception{
        Map<String,String> search = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(token)){
            Long userId = JwtUtils.getUserIdByToken(token);
            search.put("userId",userId == null?"":userId.toString());
        }
        search.put("scenicSpotFid",scenicSpotFid);
        List<SysScenicSpotBinding> list = robotLiveDeviceService.getSpotDevice(search);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("list",list);
        return APIReturnResult.ok("获取成功",dataMap);
    }

    /***
     * 根据景区id获取景区下有摄像设备的机器人
     * @param token
     * @return
     */
    @ApiOperation("根据景区id获取景区下有摄像设备的机器人")
    @GetMapping("robot_device")
    @ResponseBody
    public APIReturnResult robot_device(String token,String scenicSpotId)throws Exception{
        Map<String,String> search = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(token)){
            Long userId = JwtUtils.getUserIdByToken(token);
            search.put("userId",userId == null?"":userId.toString());
        }
        search.put("scenicSpotId",scenicSpotId);

        List<SysRobotLiveDevice>  list = robotLiveDeviceService.getRobotDevice(search);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("list",list);
        return APIReturnResult.ok("获取成功",dataMap);
    }


    /***
     * 推荐机器人摄像设备列表接口
     * @param token
     * @return
     */
    @ApiOperation("推荐机器人摄像设备列表接口")
    @GetMapping("recommend_robot_device")
    @ResponseBody
    public APIReturnResult recommend_robot_device(String token,String scenicSpotId,Integer pageNum,Integer pageSize)throws Exception{
        Map<String,Object> search = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(token)){
            Long userId = JwtUtils.getUserIdByToken(token);
            search.put("userId",userId == null?"":userId.toString());
        }
        search.put("spotId",scenicSpotId);
        PageInfo<SysRobotLiveDevice>  list = robotLiveDeviceService.getRecommendRobotDevice(search,pageNum,pageSize);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("list",list);
        return APIReturnResult.ok("获取成功",dataMap);
    }

    /***
     * 更新设备状态
     * @return
     */
    @ApiOperation("更新设备状态")
    @GetMapping("updateDeviceStatus")
    @ResponseBody
    public APIReturnResult updateDeviceStatus(String token,String data)throws Exception{

        Map<String,String> search = new HashMap<>();
        List<SysRobotLiveDevice> liveDevices = JSON.parseArray(data, SysRobotLiveDevice.class);
        robotLiveDeviceService.updateDeviceStatus(liveDevices);
        return APIReturnResult.ok("更新成功");

    }


    /**
     * 机器人直播接收推送景点播报
     * @param robotCode 机器人id
     * @param broadcastId
     * @param content  推送内容
     * @param type  内容类型，1文字，2链接
     */
    @ApiOperation("机器人直播接收推送景点播报")
    @GetMapping("robotLiveBroadcastPush")
    @ResponseBody
    public void robotLiveBroadcastPush(String robotCode,String broadcastId,String content,String type){

        List<BusinessOnLineUser>  businessOnLineUserList =  robotLiveDeviceService.robotLiveBroadcastPush(robotCode);

//        List<BusinessOnLineUser>  businessOnLineUserList = new ArrayList<>();
//        BusinessOnLineUser businessOnLineUser1 = new BusinessOnLineUser();
//        businessOnLineUser1.setType("1");
//        businessOnLineUser1.setUserId(63010200667588l);
//        businessOnLineUser1.setRobotCode("2000008");
//        businessOnLineUserList.add(businessOnLineUser1);
//
//        BusinessOnLineUser businessOnLineUser2 = new BusinessOnLineUser();
//        businessOnLineUser2.setType("2");
//        businessOnLineUser2.setUserId(91710175381888l);
//        businessOnLineUser2.setRobotCode("2000008");
//        businessOnLineUserList.add(businessOnLineUser2);

        Map<String, Object> map = new HashMap<>();
        map.put("content",content);
        map.put("type",type);

//        String s = JSON.toJSONString(map);
        String s = JsonUtils.toString(map);
        try {
            for (BusinessOnLineUser businessOnLineUser : businessOnLineUserList) {

                if ("1".equals(businessOnLineUser.getType())){//招商

                    String gtId = robotLiveDeviceService.getBusinessUserById(businessOnLineUser.getUserId());
                    String isSuccess = WeChatGtRobotAppPush.singlePushBusinessApp(gtId ,s,"直播机器人景点播报推送");

                }else if ("2".equals(businessOnLineUser.getType())){//游娱go

                    SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersService.userDetails(businessOnLineUser.getUserId().toString());
                    String isSuccess = WeChatGtRobotAppPush.singlePushBusinessApp(sysGuideAppUsers.getUserClientGtId(),s, "直播机器人景点播报推送");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

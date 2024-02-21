package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.BaseService;
import com.jxzy.AppMigration.NavigationApp.Service.RobotLiveDeviceService;
import com.jxzy.AppMigration.NavigationApp.dao.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.*;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCfRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.naming.ldap.PagedResultsControl;
import java.util.*;

/**
 * @Author zhang
 * @Date 2023/5/25 11:18
 */
@Service
public class RobotLiveDeviceServiceImpl implements RobotLiveDeviceService {

    @Autowired
    RobotLiveDeviceMapper robotLiveDeviceMapper;

    @Autowired
    BusinessUserLiveDeviceLogMapper businessUserLiveDeviceLogMapper;

    @Autowired
    BusinessOnlineUserMapper businessOnlineUserMapper;

    @Autowired
    BusinessDurationOptionMapper businessDurationOptionMapper;

    @Autowired
    BusinessUserLiveDeviceExperienceLogMapper businessUserLiveDeviceExperienceLogMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    BusinessUserLookTimeMapper businessUserLookTimeMapper;

    @Autowired
    BusinessOrderMapper businessOrderMapper;

    @Autowired
    BusinessUserMapper businessUserMapper;

    @Autowired
    BusinessTransactLogsMapper businessTransactLogsMapper;

    @Autowired
    private BaseService baseService;
    /**
     *
     * 直播机器人列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @DS("master2")
    @Override
    public PageDataResult getRobotLiveDeviceList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotLiveDevice> recommendRobotDevice = robotLiveDeviceMapper.getRecommendRobotDevice(search);

        if (recommendRobotDevice.size() > 0){
            PageInfo<SysRobotLiveDevice> pageInfo = new PageInfo<>(recommendRobotDevice);
            pageDataResult.setData(recommendRobotDevice);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 判断直播是否需要付费
     * @param robotCode
     * @param userId
     * @return
     */
    @DS("master2")
    @Override
    public Map<String, Object> ifRobotLivePay(String robotCode, String userId) {

        BusinessUserLiveDeviceLog businessUserLiveDeviceLogN = new BusinessUserLiveDeviceLog();

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> search = new HashMap<>();
        String s = DateUtil.crutDate();
        String date = DateUtil.date2String(DateUtil.crutDateTime()) ;
        search.put("robotCode",robotCode);
        search.put("userId",userId);
        search.put("nowDay",s);
        BusinessUserLiveDeviceLog businessUserLiveDeviceLog =  businessUserLiveDeviceLogMapper.selectBySearchOne(search);

        try {
            if (StringUtils.isEmpty(businessUserLiveDeviceLog)){//未找到支付记录，需要支付
                businessUserLiveDeviceLogN.setId(IDBuilder.getSeqId());
                businessUserLiveDeviceLogN.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                businessUserLiveDeviceLogN.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                businessUserLiveDeviceLogN.setExitTime(DateUtil.date2String(DateUtil.crutDateTime()));
                businessUserLiveDeviceLogN.setRobotCode(robotCode);
                businessUserLiveDeviceLogN.setUserId(Long.parseLong(userId));
                businessUserLiveDeviceLogN.setDataSide("2");
                businessUserLiveDeviceLogMapper.insertSelective(businessUserLiveDeviceLogN);
                map.put("id",businessUserLiveDeviceLogN.getId());
                map.put("state",2);
            }else{
                if("1".equals(businessUserLiveDeviceLog.getIfPay())){
                    //间隔时长，
                    long l = DateUtil.timeConversion(businessUserLiveDeviceLog.getExitTime(),date);
                    if (l > 10){//有支付订单，但已过10分钟，需要重新支付
                        businessUserLiveDeviceLogN.setId(IDBuilder.getSeqId());
                        businessUserLiveDeviceLogN.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                        businessUserLiveDeviceLogN.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                        businessUserLiveDeviceLogN.setExitTime(DateUtil.date2String(DateUtil.crutDateTime()));
                        businessUserLiveDeviceLogN.setRobotCode(robotCode);
                        businessUserLiveDeviceLogN.setDataSide("2");
                        businessUserLiveDeviceLogN.setUserId(Long.parseLong(userId) );
                        businessUserLiveDeviceLogMapper.insertSelective(businessUserLiveDeviceLogN);
                        map.put("id",businessUserLiveDeviceLogN.getId());
                        map.put("state",2);
                    } else{
                        map.put("id",businessUserLiveDeviceLog.getId());   //查询到已支付记录，且没超过10分钟,可以继续观看
                        map.put("state",1);
                    }
                }else{
                    map.put("id",businessUserLiveDeviceLog.getId()); //找到观看记录，但未支付，需支付
                    map.put("state",2);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;


    }

    /**
     * 生成直播订单，返回订单id，(需要计算观看时长逻辑的开发)
     * @param robotCode
     * @param userId
     * @return
     */
    @DS("master2")
    @Override
    public  Map<String, Object> ifRobotLivePayN(String robotCode, String userId,String optionId ) {

        Map<String, Object> map = new HashMap<>();
        BusinessUserLiveDeviceLog businessUserLiveDeviceLog = new BusinessUserLiveDeviceLog();
        BusinessDurationOption businessDurationOption = businessDurationOptionMapper.selectByPrimaryKey(optionId);
        businessUserLiveDeviceLog.setId(IDBuilder.getSeqId());
        businessUserLiveDeviceLog.setUserId(Long.parseLong(userId));
        businessUserLiveDeviceLog.setRobotCode(robotCode);
        businessUserLiveDeviceLog.setWatchedDuration(businessDurationOption.getViewingDuration());
        businessUserLiveDeviceLog.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
        businessUserLiveDeviceLog.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));

        businessUserLiveDeviceLog.setDataSide("2");
        businessUserLiveDeviceLogMapper.insertSelective(businessUserLiveDeviceLog);
        map.put("id",businessUserLiveDeviceLog.getId());

        return map;
    }




    /**
     * 修改退出直播时间
     * @param robotCode
     * @param userId
     * @param time
     * @return
     */
    @DS("master2")
    @Override
    public int editRobotLivePayTime(String robotCode, String userId, String time) {

        int i =0;
        String s = DateUtil.crutDate();
        Map<String, Object> search = new HashMap<>();
        search.put("robotCode",robotCode);
        search.put("userId",userId);
        search.put("nowDay",s);
        BusinessUserLiveDeviceLog businessUserLiveDeviceLog = businessUserLiveDeviceLogMapper.selectBySearchOne(search);

        //获取后台设置体验观看时长
        BusinessDurationOption experienceDuration = businessDurationOptionMapper.getExperienceDuration("2",null);

        //获取体验观看时长
        BusinessUserLiveDeviceExperienceLog robotResidueTime = businessUserLiveDeviceExperienceLogMapper.getRobotResidueTime(search);
        if (StringUtils.isEmpty(robotResidueTime)){

            BusinessUserLiveDeviceExperienceLog businessUserLiveDeviceExperienceLog = new BusinessUserLiveDeviceExperienceLog();
            businessUserLiveDeviceExperienceLog.setId(IDBuilder.getSeqId());
            businessUserLiveDeviceExperienceLog.setUserId(userId);
            businessUserLiveDeviceExperienceLog.setRobotCode(robotCode);
            businessUserLiveDeviceExperienceLog.setWatchedDuration(time);
            businessUserLiveDeviceExperienceLog.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
            businessUserLiveDeviceExperienceLog.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
            businessUserLiveDeviceExperienceLog.setRecordingTime(DateUtil.crutDate());
            businessUserLiveDeviceExperienceLog.setDataSide("2");
            i = businessUserLiveDeviceExperienceLogMapper.insertSelective(businessUserLiveDeviceExperienceLog);

        }else{

            //取出当天观看体验时长
            String watchedDuration = robotResidueTime.getWatchedDuration();
            Long allTime = Long.parseLong(watchedDuration) + Long.parseLong(time);
            if (Long.parseLong(experienceDuration.getViewingDuration()) <= allTime){
                //已超出体验观看时间
                robotResidueTime.setWatchedDuration(experienceDuration.getViewingDuration());
                robotResidueTime.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                i = businessUserLiveDeviceExperienceLogMapper.updateSelective(robotResidueTime);

                //更新退出直播时的时间
                if (StringUtils.isEmpty(businessUserLiveDeviceLog)){
                    return 0;
                }else{
                    businessUserLiveDeviceLog.setUpdateTime(DateUtil.currentDateTime());
                    businessUserLiveDeviceLog.setExitTime(DateUtil.currentDateTime());
                    i = businessUserLiveDeviceLogMapper.updateSelective(businessUserLiveDeviceLog);
                }
            }else{
                //未超出体验时间
                robotResidueTime.setWatchedDuration(allTime.toString());
                robotResidueTime.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                i = businessUserLiveDeviceExperienceLogMapper.updateSelective(robotResidueTime);
            }
        }

        businessOnlineUserMapper.deleteOnlineUser(robotCode, Long.parseLong(userId));
        String key = "zb"+robotCode;
        BusinessUserLivePeopleNumber businessUserLivePeopleNumber = new BusinessUserLivePeopleNumber();
        boolean hasKey = redisUtil.exists(key);
        if (hasKey) {
            Object result = redisUtil.get(key);
            JSONObject robot = JSONObject.fromObject(result);
            Object peopleNumber = JSONObject.toBean(robot, BusinessUserLivePeopleNumber.class);
            JSONObject objJson = JSONObject.fromObject(peopleNumber);
            businessUserLivePeopleNumber = (BusinessUserLivePeopleNumber) JSONObject.toBean(objJson, BusinessUserLivePeopleNumber.class);
            String onLineUsers = businessUserLivePeopleNumber.getOnLineUsers();
            Long onLineUser = 0l;
            if (Long.parseLong(onLineUsers) > 0){
                onLineUser =  Long.parseLong(onLineUsers) - 1;
            }

            businessUserLivePeopleNumber.setOnLineUsers(onLineUser.toString());
            boolean set = redisUtil.set(key, JSONObject.fromObject(businessUserLivePeopleNumber).toString());
        }
        return i;



    }

    /**
     * 更新退出直播时间(添加观看逻辑后修改的接口)
     * @param robotCode
     * @param userId
     * @param time
     * @return
     */
    @DS("master2")
    @Override
    public int editRobotLivePayTimeN(String robotCode, String userId, String time) {


        int i =0;
        String s = DateUtil.crutDate();
        Map<String, Object> search = new HashMap<>();
        search.put("robotCode",robotCode);
        search.put("userId",userId);
        search.put("nowDay",s);

        BusinessUserLookTime businessUserLookTime = businessUserLookTimeMapper.selectBySearch(search);

        if (StringUtils.isEmpty(businessUserLookTime)){//用户没有购买直播观看时长

            BusinessUserLiveDeviceLog businessUserLiveDeviceLog = businessUserLiveDeviceLogMapper.selectBySearchOne(search);
            //获取后台设置体验观看时长
            BusinessDurationOption experienceDuration = businessDurationOptionMapper.getExperienceDuration("2",null);
            //获取用户体验观看时长
            BusinessUserLiveDeviceExperienceLog robotResidueTime = businessUserLiveDeviceExperienceLogMapper.getRobotResidueTime(search);
            if (StringUtils.isEmpty(robotResidueTime)){
                BusinessUserLiveDeviceExperienceLog businessUserLiveDeviceExperienceLog = new BusinessUserLiveDeviceExperienceLog();
                businessUserLiveDeviceExperienceLog.setId(IDBuilder.getSeqId());
                businessUserLiveDeviceExperienceLog.setUserId(userId);
                businessUserLiveDeviceExperienceLog.setRobotCode(robotCode);
                businessUserLiveDeviceExperienceLog.setWatchedDuration(time);
                businessUserLiveDeviceExperienceLog.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                businessUserLiveDeviceExperienceLog.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                businessUserLiveDeviceExperienceLog.setRecordingTime(DateUtil.crutDate());
                businessUserLiveDeviceExperienceLog.setDataSide("2");
                i = businessUserLiveDeviceExperienceLogMapper.insertSelective(businessUserLiveDeviceExperienceLog);

            }else{
                String watchedDuration = robotResidueTime.getWatchedDuration();
                //总使用时间
                Long allTime = Long.parseLong(watchedDuration) + Long.parseLong(time);
                if (Long.parseLong(experienceDuration.getViewingDuration()) <= allTime){
                    //已超出体验观看时间
                    robotResidueTime.setWatchedDuration(experienceDuration.getViewingDuration());
                    robotResidueTime.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                    i = businessUserLiveDeviceExperienceLogMapper.updateSelective(robotResidueTime);

//                    //更新退出直播时的时间
//                    if (StringUtils.isEmpty(businessUserLiveDeviceLog)){
//                        return 0;
//                    }else{
////                        businessUserLiveDeviceLog.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
//                        businessUserLiveDeviceLog.setExitTime(DateUtil.date2String(DateUtil.crutDateTime()));
//                        i = businessUserLiveDeviceLogMapper.updateSelective(businessUserLiveDeviceLog);
//                    }
                }else{

                    //未超出体验时间
                    robotResidueTime.setWatchedDuration(allTime.toString());
                    robotResidueTime.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                    i = businessUserLiveDeviceExperienceLogMapper.updateSelective(robotResidueTime);

                }
            }
        }else{ //用户购买了直播观看时长
            String viewingTime = businessUserLookTime.getViewingTime().trim();
            if (Long.parseLong(viewingTime) >= Long.parseLong(time)){

                Long residueTime =   Long.parseLong(viewingTime) - Long.parseLong(time);
                businessUserLookTime.setViewingTime(residueTime.toString());
                businessUserLookTime.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                i = businessUserLookTimeMapper.updateSelective(businessUserLookTime);

            }else{

                businessUserLookTime.setViewingTime("0");
                businessUserLookTime.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                i = businessUserLookTimeMapper.updateSelective(businessUserLookTime);

            }

        }
        //减去在线观看人数
        businessOnlineUserMapper.deleteOnlineUser(robotCode, Long.parseLong(userId));
        String key = "zb"+robotCode;
        BusinessUserLivePeopleNumber businessUserLivePeopleNumber = new BusinessUserLivePeopleNumber();
        boolean hasKey = redisUtil.exists(key);
        if (hasKey) {
            Object result = redisUtil.get(key);
            JSONObject robot = JSONObject.fromObject(result);
            Object peopleNumber = JSONObject.toBean(robot, BusinessUserLivePeopleNumber.class);
            JSONObject objJson = JSONObject.fromObject(peopleNumber);
            businessUserLivePeopleNumber = (BusinessUserLivePeopleNumber) JSONObject.toBean(objJson, BusinessUserLivePeopleNumber.class);
            String onLineUsers = businessUserLivePeopleNumber.getOnLineUsers();
            Long onLineUser = Long.parseLong(onLineUsers) - 1;
            businessUserLivePeopleNumber.setOnLineUsers(onLineUser.toString());
            boolean set = redisUtil.set(key, JSONObject.fromObject(businessUserLivePeopleNumber).toString());
        }
        return i;
    }

    /**
     *  添加直播在线人数
     * @param robotCode
     * @param userId
     * @return
     */
    @DS("master2")
    @Override
    public int addRobotLivePeople(String robotCode, String userId) {

        BusinessOnLineUser businessOnLineUser = new BusinessOnLineUser();
        businessOnLineUser.setId(IDBuilder.getSeqId());
        businessOnLineUser.setType("2");
        businessOnLineUser.setRobotCode(robotCode);
        businessOnLineUser.setUserId(Long.parseLong(userId));
        businessOnLineUser.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
        businessOnLineUser.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
        businessOnlineUserMapper.insertSelective(businessOnLineUser);

        String key = "zb"+robotCode;
        BusinessUserLivePeopleNumber businessUserLivePeopleNumber = new BusinessUserLivePeopleNumber();
        boolean hasKey = redisUtil.exists(key);
        if (hasKey){
            Object result =  redisUtil.get(key);
            JSONObject robot = JSONObject.fromObject(result);
            Object peopleNumber = JSONObject.toBean(robot, BusinessUserLivePeopleNumber.class);
            JSONObject objJson = JSONObject.fromObject(peopleNumber);
            businessUserLivePeopleNumber = (BusinessUserLivePeopleNumber) JSONObject.toBean(objJson,BusinessUserLivePeopleNumber.class);
            String onLineUsers = businessUserLivePeopleNumber.getOnLineUsers();
            Long onLineUser = Long.parseLong(onLineUsers) + 1;
            businessUserLivePeopleNumber.setOnLineUsers(onLineUser.toString());

            boolean set = redisUtil.set(key, JSONObject.fromObject(businessUserLivePeopleNumber).toString());
            if (set){
                return 1;
            }else{
                return 0;
            }
        }else{
            businessUserLivePeopleNumber.setOnLineUsers("1");
            boolean set = redisUtil.set(key, JSONObject.fromObject(businessUserLivePeopleNumber).toString());
            if (set){
                return 1;
            }else{
                return 0;
            }
        }
    }

    /**
     * 获取在线人数
     * @param robotCode
     * @param userId
     * @return
     */
    @Override
    public String getRobotLivePeopleNumber(String robotCode, String userId) {

        String key = "zb"+robotCode;
        BusinessUserLivePeopleNumber businessUserLivePeopleNumber = new BusinessUserLivePeopleNumber();
//        boolean hasKey = redisUtilNew.exists(key);

        boolean hasKey = true;
        if (hasKey){
            Object result =  redisUtil.get(key);
            JSONObject robot = JSONObject.fromObject(result);
            Object peopleNumber = JSONObject.toBean(robot, BusinessUserLivePeopleNumber.class);
            JSONObject objJson = JSONObject.fromObject(peopleNumber);
            businessUserLivePeopleNumber = (BusinessUserLivePeopleNumber) JSONObject.toBean(objJson,BusinessUserLivePeopleNumber.class);
            String onLineUsers = businessUserLivePeopleNumber.getOnLineUsers();
            return onLineUsers;
        }else{
            return "0";
        }
    }

    /**
     * 支付回调
     * @param out_trade_no
     * @param objectId
     * @param type
     * @param payMoney
     * @return
     */
    @DS("master2")
    @Override
    public APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney) {
        BusinessTransactLogs log = getTransactLogsById(out_trade_no);//查询预支付订单
        if(log == null ){
            return APIReturnResult.error("没有查到消费记录信息！");
        }
        type = String.valueOf(log.getType());
        BusinessOrder order = new BusinessOrder();
        try {
            if ("4".equals(type)){//4直播支付订单
                dealOrderInfo(log,order);
                businessUserLiveDeviceLogMapper.editIfPayState(log.getLiveId());
                BusinessUserLiveDeviceLog businessUserLiveDeviceLog = businessUserLiveDeviceLogMapper.selectByPrimaryKey(log.getLiveId());
                Map<String, Object> search = new HashMap<>();
                search.put("userId",businessUserLiveDeviceLog.getUserId());
                search.put("robotCode",businessUserLiveDeviceLog.getRobotCode());
                BusinessUserLookTime businessUserLookTime = businessUserLookTimeMapper.selectBySearch(search);
                if (StringUtils.isEmpty(businessUserLookTime)){//用户之前无此机器人购买记录
                    BusinessUserLookTime businessUserLookTime1 = new BusinessUserLookTime();
                    businessUserLookTime1.setId(IDBuilder.getSeqId());
                    businessUserLookTime1.setViewingTime(businessUserLiveDeviceLog.getWatchedDuration());
                    businessUserLookTime1.setUserId(businessUserLiveDeviceLog.getUserId());
                    businessUserLookTime1.setRobotCode(businessUserLiveDeviceLog.getRobotCode());
                    businessUserLookTime1.setCreateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                    businessUserLookTime1.setUpdateTime(DateUtil.date2String(DateUtil.crutDateTime()));
                    businessUserLookTime1.setDataSide("2");
                    businessUserLookTimeMapper.insertSelective(businessUserLookTime1);
                }else{//用户之前购买过此机器人直播
                    if (!StringUtils.isEmpty(businessUserLiveDeviceLog.getWatchedDuration()) && !StringUtils.isEmpty(businessUserLookTime.getViewingTime())){
                        Long allTime =  Long.parseLong(businessUserLiveDeviceLog.getWatchedDuration().trim()) + Long.parseLong(businessUserLookTime.getViewingTime().trim());
                        businessUserLookTime.setViewingTime(allTime.toString());
                        businessUserLookTimeMapper.updateSelective(businessUserLookTime);
                    }
                }
            }
            businessOrderMapper.addBusinessOrderH(order);//保存我的订单消息
//            baseService.insertObj(order);//保存我的订单消息
            log.setStatus(1);//订单修改成已完成状态
            log.setUpdateTime(DateUtil.date2String(new Date()));
            businessTransactLogsMapper.updateByPrimaryKeySelective(log);
//            baseService.updateObj(log);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我购买的直播
     * @param search
     * @return
     */

    @DS("master2")
    @Override
    public PageInfo<BusinessUserLiveDeviceLog> myBuyLiveBroadcast(Map<String, String> search) {
        PageHelper.startPage(Integer.parseInt(search.get("pageNum")),Integer.parseInt(search.get("pageSize")));
        List<BusinessUserLiveDeviceLog> list =  businessUserLiveDeviceLogMapper.selectBySearchList(search);
        PageInfo<BusinessUserLiveDeviceLog> businessUserLiveDeviceLogPageInfo = new PageInfo<>(list);
        return businessUserLiveDeviceLogPageInfo;
    }

    /**
     *  根据景区id获取景区中有直播设备的机器人
     * @param search
     * @return
     */
    @DS("master2")
    @Override
    public List<SysRobotLiveDevice> getRobotDevice(Map<String, String> search) {

        List<SysRobotLiveDevice> list = robotLiveDeviceMapper.getRobotDevice(search);
        SysRobotGPS sysRobotGPS = new SysRobotGPS();

        for (SysRobotLiveDevice robots : list) {
            String robotCode = robots.getRobotCode();
            boolean hasKey = redisUtil.exists(robotCode);
            if (hasKey) {
                Object result = redisUtil.get(robotCode);
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                robots.setRobotGpsSmallApp(sysRobotGPS.getRobotGpsSmallApp());
            } else {
//                SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
//                sysRobotGPS = robotGPS;
            }
        }
        return list;

    }

    /***
     * 根据地区id获取地区下有摄像设备的景区
     *
     * @return
     */
    @DS("master2")
    @Override
    public List<SysScenicSpotBinding> getSpotDevice(Map<String, String> search) {

        List<SysScenicSpotBinding> list =  robotLiveDeviceMapper.getSpotDevice(search);
        return list;

    }
    /***
     * 获取有机器人直播设备的城市
     *
     * @return
     */
    @DS("master2")
    @Override
    public List<SysScenicSpotBinding> getCityDevice() {

        List<SysScenicSpotBinding> list = robotLiveDeviceMapper.getCityDevice();

        return list;
    }

    /***
     * 推荐机器人摄像设备列表接口
     * @return
     */
    @DS("master2")
    @Override
    public PageInfo<SysRobotLiveDevice> getRecommendRobotDevice(Map<String, Object> search,Integer pageNum,Integer pageSize) {

        SysRobotGPS sysRobotGPS = new SysRobotGPS();
        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotLiveDevice> list = robotLiveDeviceMapper.getRecommendRobotDevice(search);
        PageInfo<SysRobotLiveDevice> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }

    /**
     * 更新设备状态
     * @param liveDevices
     * @return
     */
    @DS("master2")
    @Override
    public int updateDeviceStatus(List<SysRobotLiveDevice> liveDevices) {

        int i = 0;
        for (SysRobotLiveDevice sysRobotLiveDevice : liveDevices) {

            i = robotLiveDeviceMapper.updateByDeviceIdSelective(sysRobotLiveDevice);

        }
        return i;
    }

    /**
     * 获取当前再看人数
     * @param robotCode
     * @param
     * @return
     */
    @DS("master2")
    @Override
    public  List<BusinessOnLineUser>  robotLiveBroadcastPush(String robotCode) {

        List<BusinessOnLineUser> businessOnLineUserList = businessOnlineUserMapper.selectByRobotCode(robotCode,"");

        return businessOnLineUserList;
    }
    @DS("master2")
    @Override
    public String getBusinessUserById(Long userId) {

        String gtId = businessUserMapper.getBusinessUserById(userId);

        return gtId;

    }

//    @DS("master2")
//    public BusinessOrder getTransactLogsById(String orderNumber) {
//
//        BusinessOrder businessOrder= businessOrderMapper.selectByOrderNumberZS(orderNumber);
//
//        return businessOrder;
//    }

    /**
     * 根据订单编号，查询订单
     * @return
     */
    @DS("master2")
    public BusinessTransactLogs getTransactLogsById(String id) {
        return businessTransactLogsMapper.selectByPrimaryKey(id);
    }

    /***
     * 封装订单表数据dd
     * @param log
     * @param order
     * @param
     * @throws Exception
     */
    public void dealOrderInfo(BusinessTransactLogs log,BusinessOrder order)throws Exception{
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

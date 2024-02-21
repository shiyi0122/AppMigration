package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRobotService;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.RedisUtil;
import com.jxzy.AppMigration.NavigationApp.util.ToolUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/9/23 10:40
 */
@Service
public class SysScenicSpotRobotServiceImpl implements SysScenicSpotRobotService {

    @Autowired
    SysRobotMapper sysRobotMapper;

    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;
    @Autowired
    private RedisUtil redisUtil;



    @DS("master2")
    @Override
    public List<SysRobotGPS> sysScenicSpotRobotList(Map<String, Object> search) {
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        String coordinateRange = (String) search.get("coordinateRange");
        List<SysRobot> robotList = sysRobotMapper.getRobotGpsList(search);
        List<SysRobotGPS> robotLists = new ArrayList<SysRobotGPS>();
        SysRobotGPS sysRobotGPS = new SysRobotGPS();

        if (!StringUtils.isEmpty(coordinateRange)) {
            String[] split = coordinateRange.split(",");

            for (SysRobot robots : robotList) {
                String robotCode = robots.getRobotCode();
                boolean hasKey = redisUtil.exists(robotCode);
                if (hasKey) {

                    Object result = redisUtil.get(robotCode);
                    JSONObject robot = JSONObject.fromObject(result);
                    Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                    JSONObject objJson = JSONObject.fromObject(sysRobot);
                    sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);

                } else {
                    SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                    sysRobotGPS = robotGPS;

                }

                if (ToolUtil.isNotEmpty(sysRobotGPS)) {
                    sysRobotGPS.setRobotRunState(robots.getRobotRunState());
                    sysRobotGPS.setClientVersion(robots.getClientVersion());
                    sysRobotGPS.setRobotRemarks(robots.getRobotRemarks());
                    sysRobotGPS.setScenicSpotName(robots.getScenicSpotName());

                    if (StringUtils.isEmpty(sysRobotGPS.getRobotGpsGpgga())) {
                        continue;
                    }
                    from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf(sysRobotGPS.getRobotGpsGpgga().split(",")[0]), Double.valueOf(sysRobotGPS.getRobotGpsGpgga().split(",")[1]));
                    Double distance1 = LngLonUtil.calculateWithSdk(from, to);

                    if (distance1 <= 50) {
                        robotLists.add(sysRobotGPS);
                    }

                }
            }
        } else {
            for (SysRobot robots : robotList) {
                String robotCode = robots.getRobotCode();
                boolean hasKey = redisUtil.exists(robotCode);
                if (hasKey) {

                    Object result = redisUtil.get(robotCode);
                    JSONObject robot = JSONObject.fromObject(result);
                    Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                    JSONObject objJson = JSONObject.fromObject(sysRobot);
                    sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                } else {

                    SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                    sysRobotGPS = robotGPS;

                }
                if (ToolUtil.isNotEmpty(sysRobotGPS)) {
                    sysRobotGPS.setRobotRunState(robots.getRobotRunState());
                    sysRobotGPS.setClientVersion(robots.getClientVersion());
                    sysRobotGPS.setRobotRemarks(robots.getRobotRemarks());
                    sysRobotGPS.setScenicSpotName(robots.getScenicSpotName());

                }


            }

        }

        return robotLists;
    }

    /**
     * 根据景区id，获取机器人列表
     * @param scenicSpotFid
     * @return
     */
    @DS("master2")
    @Override
    public List<SysUserGps> sysScenicSpotUserAndRobotList(Long scenicSpotFid) {

        Map<String, Object> search = new HashMap<>();
        search.put("scenicSpotId",scenicSpotFid);
        List<SysUserGps> sysRobotGpsList = new ArrayList<>();
        SysUserGps sysUserGps = new SysUserGps();
        List<SysRobot> robotList = sysRobotMapper.getRobotGpsList(search);
        SysRobotGPS sysRobotGPS = new SysRobotGPS();
        for (SysRobot robots : robotList){
            String robotCode = robots.getRobotCode();
            Set keys = redisUtil.keys(robotCode);
            if(keys.size()>0){
                Object result =  redisUtil.get(robotCode);
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson,SysRobotGPS.class);
            }else{
                SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                sysRobotGPS = robotGPS;
            }
            if (!StringUtils.isEmpty(sysRobotGPS)){
                if (!StringUtils.isEmpty(sysRobotGPS.getRobotGpsSmallApp())){
                    String[] split = sysRobotGPS.getRobotGpsSmallApp().split(",");

                    if("0".equals(split[0]) || "0".equals(split[0])){
                        continue;
                    }
                    sysUserGps =  new SysUserGps();
                    sysUserGps.setLongitude(split[0]);
                    sysUserGps.setLatitude(split[1]);
                    sysRobotGpsList.add(sysUserGps);
                }

            }
        }
        return sysRobotGpsList;


    }
}

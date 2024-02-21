package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.io.unit.DataUnit;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRobotService;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserActualGpsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserActualGpsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.RedisUtil;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author zhang
 * @Date 2023/3/11 10:53
 */
@Service
public class SysUserActualGpsServiceImpl implements SysUserActualGpsService {


    @Autowired
    SysUserActualGpsMapper sysUserActualGpsMapper;
    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;
    @Autowired
    SysScenicSpotRobotService sysScenicSpotRobotService;

    @Autowired
    SysRobotMapper sysRobotMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取用户定位列表
     *
     * @param id
     * @param type
     * @return
     */
    @Override
    public List<SysUserGps> getUserGpsList(Long id, Long type, String cityName) {

        List<SysUserGps> sysUserGpss = new ArrayList<>();
        SysUserGps sysUserGps = new SysUserGps();
        if (type == 1) {//城市
            List<SysScenicSpotBinding> sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFnameT(cityName);
            if (sysScenicSpotBinding.size() == 0) {
                List<SysRobot> robotList = sysRobotMapper.getRobotGpsSpotList(null);
                SysRobotGPS sysRobotGPS = new SysRobotGPS();
                for (SysRobot robots : robotList) {
                    String robotCode = robots.getRobotCode();
                    Set keys = redisUtil.keys(robotCode);
                    if (keys.size() > 0) {
                        Object result = redisUtil.get(robotCode);
                        JSONObject robot = JSONObject.fromObject(result);
                        Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                        JSONObject objJson = JSONObject.fromObject(sysRobot);
                        sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                    } else {
                        SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                        sysRobotGPS = robotGPS;
                    }
                    if (!StringUtils.isEmpty(sysRobotGPS)) {
                        if (!StringUtils.isEmpty(sysRobotGPS.getRobotGpsSmallApp()) && !StringUtils.isEmpty(sysRobotGPS.getRobotGpsGpgga())) {
                            System.out.println(sysRobotGPS.getRobotCode());
                            String[] split = sysRobotGPS.getRobotGpsSmallApp().split(",");
                            String[] splitGps = sysRobotGPS.getRobotGpsGpgga().split(",");
                            if ("0".equals(split[0]) || "0".equals(split[0]) || "0".equals(splitGps[0])) {
                                continue;
                            }
                            sysUserGps = new SysUserGps();
                            sysUserGps.setLongitude(split[0]);
                            sysUserGps.setLatitude(split[1]);
                            sysUserGps.setLongitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[0]);
                            sysUserGps.setLatitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[1]);
                            sysUserGps.setScenicSpotId(robots.getScenicSpotId());
                            sysUserGpss.add(sysUserGps);
                        }
                    }
                }

            } else {
                List<SysUserActualGps> list = sysUserActualGpsMapper.getUserGpsCityIdList(sysScenicSpotBinding.get(0).getScenicSpotFid());

                for (SysUserActualGps sysUserActualGps : list) {
                    if (sysUserActualGps.getUserCoordinatesGaode() != null) {
                        sysUserGps.setLongitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[0]);
                        sysUserGps.setLatitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[1]);
                    } else {
                        continue;
                    }
//                    sysUserGps.setLongitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[0]);
//                    sysUserGps.setLatitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[1]);
                    sysUserGps.setLongitudeGps(sysUserActualGps.getUserCoordinatesGps().split(",")[0]);
                    sysUserGps.setLatitudeGps(sysUserActualGps.getUserCoordinatesGps().split(",")[1]);
                    sysUserGps.setScenicSpotId(sysUserActualGps.getSpotId());
                    sysUserGpss.add(sysUserGps);
                }
                List<SysScenicSpotBinding> bindingList = sysScenicSpotBindingMapper.getBindingIdBySpotList(sysScenicSpotBinding.get(0).getScenicSpotFid());
                for (SysScenicSpotBinding scenicSpotBinding : bindingList) {
                    List<SysRobot> robotList = sysRobotMapper.getRobotGpsSpotList(scenicSpotBinding.getScenicSpotFid());
                    SysRobotGPS sysRobotGPS = new SysRobotGPS();
                    for (SysRobot robots : robotList) {
                        String robotCode = robots.getRobotCode();
                        Set keys = redisUtil.keys(robotCode);
                        if (keys.size() > 0) {
                            Object result = redisUtil.get(robotCode);
                            JSONObject robot = JSONObject.fromObject(result);
                            Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                            JSONObject objJson = JSONObject.fromObject(sysRobot);
                            sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                        } else {
                            SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                            sysRobotGPS = robotGPS;
                        }
                        if (!StringUtils.isEmpty(sysRobotGPS)) {
                            if (!StringUtils.isEmpty(sysRobotGPS.getRobotGpsSmallApp())) {
                                String[] split = sysRobotGPS.getRobotGpsSmallApp().split(",");

                                if ("0".equals(split[0]) || "0".equals(split[0])) {
                                    continue;
                                }
                                sysUserGps = new SysUserGps();
                                sysUserGps.setLongitude(split[0]);
                                sysUserGps.setLatitude(split[1]);
                                sysUserGps.setLongitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[0]);
                                sysUserGps.setLatitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[1]);
                                sysUserGps.setScenicSpotId(robots.getScenicSpotId());
                                sysUserGpss.add(sysUserGps);
                            }
                        }
                    }
                }
            }
            return sysUserGpss;

        } else if (type == 2) {//景区

            List<SysUserActualGps> list = sysUserActualGpsMapper.getUserGpsSpotIdList(id);

            for (SysUserActualGps sysUserActualGps : list) {
                sysUserGps.setLongitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[0]);
                sysUserGps.setLatitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[1]);
                sysUserGps.setLongitudeGps(sysUserActualGps.getUserCoordinatesGps().split(",")[0]);
                sysUserGps.setLatitudeGps(sysUserActualGps.getUserCoordinatesGps().split(",")[1]);

                sysUserGpss.add(sysUserGps);
            }

            List<SysRobot> robotList = sysRobotMapper.getRobotGpsSpotList(id);
            SysRobotGPS sysRobotGPS = new SysRobotGPS();
            for (SysRobot robots : robotList) {
                String robotCode = robots.getRobotCode();
                Set keys = redisUtil.keys(robotCode);
                if (keys.size() > 0) {
                    Object result = redisUtil.get(robotCode);
                    JSONObject robot = JSONObject.fromObject(result);
                    Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                    JSONObject objJson = JSONObject.fromObject(sysRobot);
                    sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                } else {
                    SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                    sysRobotGPS = robotGPS;
                }
                if (!StringUtils.isEmpty(sysRobotGPS)) {
                    if (!StringUtils.isEmpty(sysRobotGPS.getRobotGpsSmallApp())) {
                        String[] split = sysRobotGPS.getRobotGpsSmallApp().split(",");

                        if ("0".equals(split[0]) || "0".equals(split[0])) {
                            continue;
                        }
                        sysUserGps = new SysUserGps();
                        sysUserGps.setLongitude(split[0]);
                        sysUserGps.setLatitude(split[1]);
                        sysUserGps.setLongitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[0]);
                        sysUserGps.setLatitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[1]);
                        sysUserGpss.add(sysUserGps);
                    }

                }
            }
            return sysUserGpss;
        } else {//景点

            List<SysUserActualGps> list = sysUserActualGpsMapper.getUserGpsBroadcastIdList(id);

            for (SysUserActualGps sysUserActualGps : list) {
                sysUserGps.setLongitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[0]);
                sysUserGps.setLatitude(sysUserActualGps.getUserCoordinatesGaode().split(",")[1]);
                sysUserGps.setLongitudeGps(sysUserActualGps.getUserCoordinatesGps().split(",")[0]);
                sysUserGps.setLatitudeGps(sysUserActualGps.getUserCoordinatesGps().split(",")[1]);

                sysUserGpss.add(sysUserGps);
            }

            List<SysRobot> robotList = sysRobotMapper.getRobotGpsSpotList(id);
            SysRobotGPS sysRobotGPS = new SysRobotGPS();
            for (SysRobot robots : robotList) {
                String robotCode = robots.getRobotCode();
                Set keys = redisUtil.keys(robotCode);
                if (keys.size() > 0) {
                    Object result = redisUtil.get(robotCode);
                    JSONObject robot = JSONObject.fromObject(result);
                    Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                    JSONObject objJson = JSONObject.fromObject(sysRobot);
                    sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                } else {
                    SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                    sysRobotGPS = robotGPS;
                }
                if (!StringUtils.isEmpty(sysRobotGPS)) {
                    if (!StringUtils.isEmpty(sysRobotGPS.getRobotGpsSmallApp())) {
                        String[] split = sysRobotGPS.getRobotGpsSmallApp().split(",");

                        if ("0".equals(split[0]) || "0".equals(split[0])) {
                            continue;
                        }
                        sysUserGps = new SysUserGps();
                        sysUserGps.setLongitude(split[0]);
                        sysUserGps.setLatitude(split[1]);
                        sysUserGps.setLongitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[0]);
                        sysUserGps.setLatitudeGps(sysRobotGPS.getRobotGpsGpgga().split(",")[1]);

                        sysUserGpss.add(sysUserGps);
                    }
                }
            }
            return sysUserGpss;
        }
    }

    /**
     * 更新用户实时坐标
     *
     * @param
     * @return
     */
    @Override
    public int editUserGps(SysUserActualGps sysUserActualGps) {

        SysUserActualGps sysUserActualGpsN = sysUserActualGpsMapper.getUserIdByGps(sysUserActualGps.getUserId());

        if (StringUtils.isEmpty(sysUserActualGpsN)) {

            List<SysScenicSpotBinding> sysScenicSpotBindingsProvinces = sysScenicSpotBindingMapper.selectSpotByFnameT(sysUserActualGps.getProvincesName());

            if (sysScenicSpotBindingsProvinces.size() > 0) {
                sysUserActualGps.setProvincesId(sysScenicSpotBindingsProvinces.get(0).getScenicSpotFid());
            }
            List<SysScenicSpotBinding> sysScenicSpotBindingsCity = sysScenicSpotBindingMapper.selectSpotByFnameT(sysUserActualGps.getCityName());
            if (sysScenicSpotBindingsCity.size() > 0) {
                sysUserActualGps.setCityId(sysScenicSpotBindingsCity.get(0).getScenicSpotFid());
            }
            List<SysScenicSpotBinding> sysScenicSpotBindingsArea = sysScenicSpotBindingMapper.selectSpotByFnameT(sysUserActualGps.getCityName());
            if (sysScenicSpotBindingsArea.size() > 0) {
                sysUserActualGps.setAreaId(sysScenicSpotBindingsArea.get(0).getScenicSpotFid());
            }

            sysUserActualGps.setId(IdUtils.getSeqId());
            sysUserActualGps.setCreateDate(DateUtil.currentDateTime());
            sysUserActualGps.setUpdateDate(DateUtil.currentDateTime());
            int i = sysUserActualGpsMapper.insert(sysUserActualGps);
            return i;
        } else {

            List<SysScenicSpotBinding> sysScenicSpotBindingsProvinces = sysScenicSpotBindingMapper.selectSpotByFnameT(sysUserActualGps.getProvincesName());

            if (sysScenicSpotBindingsProvinces.size() > 0) {
                sysUserActualGps.setProvincesId(sysScenicSpotBindingsProvinces.get(0).getScenicSpotFid());
            }
            List<SysScenicSpotBinding> sysScenicSpotBindingsCity = sysScenicSpotBindingMapper.selectSpotByFnameT(sysUserActualGps.getCityName());
            if (sysScenicSpotBindingsCity.size() > 0) {
                sysUserActualGps.setCityId(sysScenicSpotBindingsCity.get(0).getScenicSpotFid());
            }
            List<SysScenicSpotBinding> sysScenicSpotBindingsArea = sysScenicSpotBindingMapper.selectSpotByFnameT(sysUserActualGps.getCityName());
            if (sysScenicSpotBindingsArea.size() > 0) {
                sysUserActualGps.setAreaId(sysScenicSpotBindingsArea.get(0).getScenicSpotFid());
            }
            sysUserActualGpsN.setUpdateDate(DateUtil.currentDateTime());
            int i = sysUserActualGpsMapper.update(sysUserActualGpsN);
            return i;
        }

    }

    @Override
    public List<UserRoleText> listByPhone(String userPhone) {

        return sysUserActualGpsMapper.listByPhone(userPhone);
    }

    @Override
    public LockText getLock() {
        return sysUserActualGpsMapper.getLock();
    }

    @Override
    public int editLock(String onOff) {
        if (("").equals(onOff) || onOff == null){
            return 0;
        }
        return sysUserActualGpsMapper.editLock(onOff);
    }

    @Override
    public LockText getLockNew() {
        return sysUserActualGpsMapper.getLockNew();
    }
}

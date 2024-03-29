package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotUserFootprintService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotUserFootprintMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserFootprint;
import com.jxzy.AppMigration.NavigationApp.util.DateUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/16 16:35
 */
@Service
public class SysScenicSpotUserFootprintServiceImpl implements SysScenicSpotUserFootprintService {

    @Autowired
    SysScenicSpotUserFootprintMapper sysScenicSpotUserFootprintMapper;

    /**
     * 添加用户足迹
     * 张
     * @param sysScenicSpotUserFootprint
     * @return
     */
    @Override
    public int addSpotUserFootPrint(SysScenicSpotUserFootprint sysScenicSpotUserFootprint) {


        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();
        Map<String, Object> search = new HashMap<>();
        search.put("spotId",sysScenicSpotUserFootprint.getSpotId());
        search.put("userId",sysScenicSpotUserFootprint.getUserId());

        List<SysScenicSpotUserFootprint> list =  sysScenicSpotUserFootprintMapper.selectBySearch(search);
        //取最后足迹
//        SysScenicSpotUserFootprint userFootprint = list.get(list.size() - 1);

        int i = 0;
        try{
            if (!StringUtils.isEmpty(list) && list.size()>0){
                SysScenicSpotUserFootprint userFootprint = list.get(list.size() - list.size());
                if (DateUtil.getMinutes(userFootprint.getEndTime(),DateUtil.currentDateTime())>30){

                    sysScenicSpotUserFootprint.setId(IdUtils.getSeqId());
                    sysScenicSpotUserFootprint.setStartTime(DateUtil.currentDateTime());
                    sysScenicSpotUserFootprint.setEndTime(DateUtil.currentDateTime());

                    i = sysScenicSpotUserFootprintMapper.addScenicSpotUserFootprint(sysScenicSpotUserFootprint);
                    return i;
                }else{
                    String[] split2 = sysScenicSpotUserFootprint.getSpotCoordinate().split("!");

                    String[] split = userFootprint.getSpotCoordinate().split("!");
                    if (split.length>0){
                        String s = split[split.length - 1];
                        String[] split1 = s.split(",");
                        String spotCoordinate = userFootprint.getSpotCoordinate();
                        //循环传进的坐标
                        for (String s1 : split2) {

                            String[] split3 = s1.split(",");
                            from=new Point2D.Double(Double.valueOf(split1[0]),Double.valueOf(split1[1]));
                            to=new Point2D.Double(Double.valueOf(split3[0]),Double.valueOf(split3[1]));
                            Double  distance1 = LngLonUtil.calculateWithSdk(from, to);
                            if (distance1 > 0.005){
//                                spotCoordinate = userFootprint.getSpotCoordinate();
                                spotCoordinate = spotCoordinate +"!"+s1;
                                return i;
                            }else{
                                continue;
                            }
                        }

                        userFootprint.setSpotCoordinate(spotCoordinate);
                        userFootprint.setEndTime(DateUtil.currentDateTime());
                        i = sysScenicSpotUserFootprintMapper.editScenicSpotUserFootprint(userFootprint);
                        return i;

                    }else{
                        userFootprint.setSpotCoordinate(sysScenicSpotUserFootprint.getSpotCoordinate());
                        userFootprint.setEndTime(DateUtil.currentDateTime());
                        i = sysScenicSpotUserFootprintMapper.editScenicSpotUserFootprint(userFootprint);
                        return i;
                    }
                }
            }else{
                sysScenicSpotUserFootprint.setId(IdUtils.getSeqId());
                sysScenicSpotUserFootprint.setStartTime(DateUtil.currentDateTime());
                sysScenicSpotUserFootprint.setEndTime(DateUtil.currentDateTime());
                i = sysScenicSpotUserFootprintMapper.addScenicSpotUserFootprint(sysScenicSpotUserFootprint);
                return i;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
         return i;
    }

    /**
     * 查询用户当天足迹
     * 张
     * @param spotId
     * @param uid
     * @return
     */
    @Override
    public List<SysScenicSpotUserFootprint> getSpotUserFootPrint(String spotId, String uid) {

        Map<String, Object> search = new HashMap<>();
        String startTime = "";
        search.put("spotId",spotId);
        search.put("userId",uid);
//      search.put("time",DateUtil.crutDate());
        PageHelper.startPage(1,10);
        List<SysScenicSpotUserFootprint> list = sysScenicSpotUserFootprintMapper.selectBySearch(search);

        if (list.size()>0){
            startTime = list.get(0).getStartTime();
            search.put("time",startTime.substring(0,10));
            List<SysScenicSpotUserFootprint> sysScenicSpotUserFootprints = sysScenicSpotUserFootprintMapper.selectBySearch(search);
            return  sysScenicSpotUserFootprints;
        }

        return list;
    }

    /**
     * 查询用户当天足迹
     * @param spotId
     * @param uid
     * @return
     */
    @Override
    public List<SysScenicSpotUserFootprint> getSpotUserFootSameDay(String spotId, String uid) {

        HashMap<String, Object> search = new HashMap<>();
        String date = DateUtil.crutDate();
        search.put("time",date);
        search.put("spotId",spotId);
        search.put("userId",uid);
        List<SysScenicSpotUserFootprint> sysScenicSpotUserFootprints = sysScenicSpotUserFootprintMapper.selectBySearch(search);
        return sysScenicSpotUserFootprints;

    }
}


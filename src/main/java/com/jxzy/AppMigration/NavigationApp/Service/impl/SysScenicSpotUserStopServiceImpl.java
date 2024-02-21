package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotUserStopService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotUserStopMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserStop;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/17 11:08
 */
@Service
public class SysScenicSpotUserStopServiceImpl implements SysScenicSpotUserStopService {

    @Autowired
    SysScenicSpotUserStopMapper sysScenicSpotUserStopMapper;

    /**
     * 添加驻足时长
     * 张
     * @param sysScenicSpotUserStop
     * @return
     */
    @Override
    public int addSpotUserStop(SysScenicSpotUserStop sysScenicSpotUserStop) {

        String type = sysScenicSpotUserStop.getType();

        Map<String, Object> search = new HashMap<>();
        search.put("spotId",sysScenicSpotUserStop.getSpotId());
        search.put("userId",sysScenicSpotUserStop.getUserId());
        search.put("time", DateUtil.crutDate());
        if ("1".equals(type)){
           List<SysScenicSpotUserStop> list = sysScenicSpotUserStopMapper.selectBySearch(search);
           if (list.size()>0 && !StringUtils.isEmpty(list)){
               SysScenicSpotUserStop sysScenicSpotUserStopNew = list.get(0);
               sysScenicSpotUserStopNew.setStartTime(sysScenicSpotUserStop.getStartTime());
               int i = sysScenicSpotUserStopMapper.exitSpotUserStop(sysScenicSpotUserStopNew);
               return i;
           }else{
               sysScenicSpotUserStop.setId(IdUtils.getSeqId());
               sysScenicSpotUserStop.setCreateTime(DateUtil.currentDateTime());
               sysScenicSpotUserStop.setUpdateTime(DateUtil.currentDateTime());
               int i = sysScenicSpotUserStopMapper.addSpotUserStop(sysScenicSpotUserStop);
                return i;
           }
        }else if ("2".equals(type)){
            try{
                List<SysScenicSpotUserStop> list = sysScenicSpotUserStopMapper.selectBySearch(search);
                if (list.size()>0 && !StringUtils.isEmpty(list)){
                    SysScenicSpotUserStop sysScenicSpotUserStopNew = list.get(0);
                    sysScenicSpotUserStopNew.setEndTime(sysScenicSpotUserStop.getEndTime());
                    Long minutes = com.jxzy.AppMigration.NavigationApp.util.DateUtil.getMinutes(sysScenicSpotUserStopNew.getStartTime(), sysScenicSpotUserStopNew.getEndTime());
                    String time = sysScenicSpotUserStopNew.getTime();
                    Long minutesAll =  Long.parseLong(time) + minutes;
                    sysScenicSpotUserStopNew.setTime(minutesAll.toString());
                    int i = sysScenicSpotUserStopMapper.exitSpotUserStop(sysScenicSpotUserStopNew);
                    return i;
                }else{
                    return 0;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 查询上次驻足时间
     * zhang
     * @param uid
     * @param spotId
     * @return
     */
    @Override
    public List<SysScenicSpotUserStop> getSpotUserStop(String uid, String spotId) {

        Map<String, Object> search = new HashMap<>();
        String starTime = "";
        search.put("uid",uid);
        search.put("spotId",spotId);

        PageHelper.startPage(1,10);
        List<SysScenicSpotUserStop> list = sysScenicSpotUserStopMapper.selectBySearch(search);
        if (list.size()>0){
             starTime = list.get(0).getStartTime();
             search.put("time",starTime.substring(0,10));
             List<SysScenicSpotUserStop> sysScenicSpotUserStops = sysScenicSpotUserStopMapper.selectBySearch(search);
             return sysScenicSpotUserStops;
        }else{
            return list;
        }
    }

    /**
     * 后台管理——足迹列表查询
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysScenicSpotUserStopList(Integer pageNum,Integer pageSize,Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotUserStop> list = sysScenicSpotUserStopMapper.getSysScenicSpotUserStopList(search);
        for (SysScenicSpotUserStop sysScenicSpotUserStop : list) {
            Long userId = sysScenicSpotUserStop.getUserId();
            Long spotId = sysScenicSpotUserStop.getSpotId();
            Long time = sysScenicSpotUserStopMapper.getSysScenicSpotUserTime(userId,spotId);
            sysScenicSpotUserStop.setTime(time.toString());

        }
        if (list.size()>0){
            PageInfo<SysScenicSpotUserStop> page = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)page.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理--估计用户id，和景区id，获取景区景点的驻足时长
     * @param spotId
     * @param userId
     * @return
     */
    @Override
    public List<SysScenicSpotUserStop> getSysScenicSpotUserStopFootprint(Long spotId, Long userId) {

        List<SysScenicSpotUserStop> list = sysScenicSpotUserStopMapper.getSysScenicSpotUserStopFootprint(spotId,userId);

        return list;
    }

    /**
     * 后台管理--删除景点足迹
     * @param id
     * @return
     */
    @Override
    public int delSysScenicSpotUserStopFootprint(Long id) {

       int i = sysScenicSpotUserStopMapper.deleteByPrimaryKey(id);
        return i;

    }
}

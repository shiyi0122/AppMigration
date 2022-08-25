package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastSaturationService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastSaturationMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastSaturation;
import net.bytebuddy.asm.Advice;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 *
 * @Date 2022/8/22 16:35
 * 景点饱和度相关接口
 */
@Service
public class SysScenicSpotBroadcastSaturationServiceImpl implements SysScenicSpotBroadcastSaturationService {

    @Autowired
    SysScenicSpotBroadcastSaturationMapper sysScenicSpotBroadcastSaturationMapper;
    @Autowired
    SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;


    /**
     * 添加景点人数，
     * 张
     * @param sysScenicSpotBroadcastSaturation
     * @return
     */
    @Override
    public int addSaturation(SysScenicSpotBroadcastSaturation sysScenicSpotBroadcastSaturation) {

        Map<String, Object> search = new HashMap<>();
        SysScenicSpotBroadcastSaturation sysScenicSpotBroadcastSaturationNew= new SysScenicSpotBroadcastSaturation();
        search.put("spotId",sysScenicSpotBroadcastSaturation.getSpotId());
        search.put("broadcastId",sysScenicSpotBroadcastSaturation.getBroadcastId());
        List<SysScenicSpotBroadcastSaturation> list = sysScenicSpotBroadcastSaturationMapper.selectBySearch(search);
        if (!StringUtils.isEmpty(list) && list.size()>0){
            sysScenicSpotBroadcastSaturationNew = list.get(0);
            Long peopleCount = sysScenicSpotBroadcastSaturationNew.getPeopleCount();
            peopleCount = peopleCount + 1;
            sysScenicSpotBroadcastSaturationNew.setPeopleCount(peopleCount);
        }
        int i = sysScenicSpotBroadcastSaturationMapper.addBroadSaturation(sysScenicSpotBroadcastSaturationNew);
        return i;
    }

    /**
     *
     * 根据景区查询景点饱和度
     * @param spotId
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcastSaturation> getBroadcastSaturationList(String spotId) {

        Map<String, Object> search = new HashMap<>();
        search.put("spotId",spotId);
//        List<SysScenicSpotBroadcast> spotBroadcastList = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);
        List<SysScenicSpotBroadcastSaturation> sysScenicSpotBroadcastSaturations = sysScenicSpotBroadcastSaturationMapper.selectBySearch(search);

        for (SysScenicSpotBroadcastSaturation sysScenicSpotBroadcastSaturation : sysScenicSpotBroadcastSaturations) {
            if (sysScenicSpotBroadcastSaturation.getPeopleCount() > 0 && sysScenicSpotBroadcastSaturation.getBearPeople() > 0){
                Long peopleCount = sysScenicSpotBroadcastSaturation.getPeopleCount();
                Long bearPeople = sysScenicSpotBroadcastSaturation.getBearPeople();
                Double bhd = ((double)peopleCount / (double)bearPeople) * 100;
                BigDecimal b = new BigDecimal(bhd);
                Double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                sysScenicSpotBroadcastSaturation.setSaturation(df.toString());
            }else{
                if (sysScenicSpotBroadcastSaturation.getPeopleCount() == 0){
                    sysScenicSpotBroadcastSaturation.setSaturation("0");
                }else if (sysScenicSpotBroadcastSaturation.getBearPeople() == 0){
                    sysScenicSpotBroadcastSaturation.setSaturation("100");
                }
            }
        }

        return sysScenicSpotBroadcastSaturations;
    }

    /**
     * 景点人数减一，
     * 张
     * @param broadcastId
     * @return
     */
    @Override
    public int reduceSaturation(Long broadcastId) {

        Map<String, Object> search = new HashMap<>();
        search.put("broadcastId",broadcastId);
        List<SysScenicSpotBroadcastSaturation> sysScenicSpotBroadcastSaturations = sysScenicSpotBroadcastSaturationMapper.selectBySearch(search);
        if (StringUtils.isEmpty(sysScenicSpotBroadcastSaturations)){
            return 0;
        }else{
            SysScenicSpotBroadcastSaturation sysScenicSpotBroadcastSaturation = sysScenicSpotBroadcastSaturations.get(0);
            Long peopleCount = sysScenicSpotBroadcastSaturation.getPeopleCount();
            if (peopleCount>0){
                peopleCount = peopleCount -1 ;
                sysScenicSpotBroadcastSaturation.setPeopleCount(peopleCount);
                int i = sysScenicSpotBroadcastSaturationMapper.editBroadSaturation(sysScenicSpotBroadcastSaturation);
                return i;
            }
            return 1;
        }
    }
}

package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBindingMapper {
    int deleteByPrimaryKey(Long scenicSpotFid);

    int insert(SysScenicSpotBinding record);

    int insertSelective(SysScenicSpotBinding record);

    SysScenicSpotBinding selectByPrimaryKey(Long scenicSpotFid);

    int updateByPrimaryKeySelective(SysScenicSpotBinding record);

    int updateByPrimaryKey(SysScenicSpotBinding record);

    /**
     * 获取景区和城市列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/1 0001
     */
    List<SysScenicSpotBinding> queryCityAndScenicSpotLists(Map<String, Object> search);


    SysScenicSpotBinding selectSpotByFname(String cityName);


    List<SysScenicSpot> selectBySearch(Map<String, Object> search);


    /**
     * 后台管理——归属地下拉选
     * @return
     */
    List<SysScenicSpotBinding> placeDrop();


    SysScenicSpotBinding getSpotIdAndSpotFid(Long scenicSpotFid, Long scenicSpotPid);

    List<SysScenicSpotBinding> getSpotBindingProvince(String pid);

    List<SysScenicSpotBinding> getSpotBindingCity(String pid);

    List<SysScenicSpotBinding> getSpotBindingArea(String pid);

    List<SysScenicSpotBinding> getScenicSpotBindingList(@Param("sysScenicSpotBinding") SysScenicSpotBinding sysScenicSpotBinding);


    SysScenicSpotBinding selectSpotByFnameN(String cityName, Integer type);


    List<SysScenicSpotBinding> getHotCity();


    List<SysScenicSpotBinding> getCityAll();


    List<SysScenicSpotBinding> getAreaAll();


    List<SysScenicSpotBinding> selectSpotByFnameList(String content);

    List<SysScenicSpotBinding> getProvinceCity();


    List<SysScenicSpotBinding> getBindingIdBySpotList(Long scenicSpotFid);

    List<SysScenicSpotBinding> selectSpotNameLikeByFname(String placeOfOwnership);

    List<SysScenicSpotBinding> selectSpotByFnameT(String spotBindingName);

    Integer selectFidByList(Long scenicSpotFid);


    SysScenicSpotBinding selectByPrimaryKeyAndName(Long scenicSpotId, String scenicSpotName);


}
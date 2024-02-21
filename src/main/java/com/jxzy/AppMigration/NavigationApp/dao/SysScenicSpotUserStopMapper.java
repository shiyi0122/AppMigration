package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserStop;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/17 11:10
 */



public interface SysScenicSpotUserStopMapper {


    List<SysScenicSpotUserStop> selectBySearch(Map<String, Object> search);


    int exitSpotUserStop(SysScenicSpotUserStop sysScenicSpotUserStopNew);


    int addSpotUserStop(SysScenicSpotUserStop sysScenicSpotUserStop);


    Integer getSpotIdUserCount(Long scenicSpotId);

    Integer getSpotIdTimeSum(Long scenicSpotId);

    List<SysScenicSpotUserStop> getSysScenicSpotUserStopList(Map<String, Object> search);

    Long getSysScenicSpotUserTime(@Param("userId")Long userId,@Param("spotId")Long spotId);

    List<SysScenicSpotUserStop> getSysScenicSpotUserStopFootprint(@Param("userId")Long spotId, @Param("spotId")Long userId);

    int deleteByPrimaryKey(Long id);

}

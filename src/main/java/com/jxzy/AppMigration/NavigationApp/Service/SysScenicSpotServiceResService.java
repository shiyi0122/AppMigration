package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotServiceResService {
    /**
     * 查询洗手间列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotServiceRes> queryToiletList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 后台管理——卫生间搜索
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSpotServiceResList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 后台管理——添加卫生间
     * @param sysScenicSpotServiceRes
     * @return
     */
    int addSpotServiceRes(SysScenicSpotServiceRes sysScenicSpotServiceRes);


    /**
     * 后台管理——修改卫生间信息
     * @param sysScenicSpotServiceRes
     * @return
     */
    int editSpotServiceRes(SysScenicSpotServiceRes sysScenicSpotServiceRes);

    /**
     * 后台管理+—删除卫生间信息
     * @param serviceId
     * @return
     */
    int delSpotServiceRes(Long serviceId);
    /**
     * 修改卫生间启用禁用状态
     * @param serviceId
     * @return
     */
    int editSpotServiceResState(Long serviceId, String serviceState);


    /**
     * 根据景区id和服务项名称查询数据
     * @param scenicSpotId
     * @param serviceId
     * @return
     */
    SysScenicSpotServiceRes getSpotIdAndServiceResId(Long scenicSpotId, Long serviceId);

    int  insert(SysScenicSpotServiceRes sysScenicSpotServiceRes);

    SysScenicSpotServiceRes getSpotServiceResId(Long serviceId);

    List<SysScenicSpotServiceRes> getServiceResExcelPoi(Map<String, Object> search);


}

package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBindingService {
    /**
     * 获取景区和城市列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/1 0001
     */
    List<SysScenicSpotBinding> queryCityAndScenicSpotLists(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 根据景区id，和父id查询归属信息
     * @param scenicSpotFid
     * @param scenicSpotPid
     * @return
     */
    SysScenicSpotBinding getSpotIdAndSpotFid(Long scenicSpotFid, Long scenicSpotPid);

    /**
     * 定时任务添加归属信息
     * @param sysScenicSpotBinding
     * @return
     */
    int insert(SysScenicSpotBinding sysScenicSpotBinding);

    SysScenicSpotBinding getSpotBindingId(Long scenicSpotFid);

    int edit(SysScenicSpotBinding sysScenicSpotBinding);

    /**
     * 后台添加归属省
     * @param sysScenicSpotBinding
     * @return
     */
    int addScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding);
    /**
     * 后台修改归属省
     * @param sysScenicSpotBinding
     * @return
     */
    int editScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding);
    /**
     * 后台删除归属地
     * @param
     * @return
     */
    int delScenicSpotBinding(Long scenicSpotFid);
    /**
     * 归属省下拉选
     * @return
     */
    List<SysScenicSpotBinding> getSpotBindingProvince(String pid);
    /**
     * 归属市下拉选
     * @return
     */
    List<SysScenicSpotBinding> getSpotBindingCity(String pid);
    /**
     * 归属区下拉选
     * @return
     */
    List<SysScenicSpotBinding> getSpotBindingArea(String pid);

    /**
     * 后台归属地列表查询
     * @param pageNum
     * @param pageSize
     * @param sysScenicSpotBinding
     * @return
     */
    PageDataResult getScenicSpotBindingList(Integer pageNum, Integer pageSize, SysScenicSpotBinding sysScenicSpotBinding);

    /**
     * 多级联动下拉选
     * @return
     */
    List<SysScenicSpotBinding> getMulitistageDrop();


    List<SysScenicSpotBinding> getHotCity();

    List<SysScenicSpotBinding> getCityAll();


    List<SysScenicSpotBinding> getAreaAll();

    List<SysScenicSpotBinding> getCityArea(String content);

    List<SysScenicSpotBinding> getProvinceCity();


    SysScenicSpotBinding selectSpotByFnameT(String cityName);


    List<SysScenicSpotBinding> getBindingIdBySpotList(Long scenicSpotFid);


}

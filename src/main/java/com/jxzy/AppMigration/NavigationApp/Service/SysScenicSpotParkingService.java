package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotParkingExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParking;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParkingWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotParkingService {
    /**
     * 获取景区停靠点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/27 0027
     */
    List<SysScenicSpotBroadcast> getScenicSpotParkingList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 后台管理——景区停放点列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult selectBySearch(Integer pageNum, Integer pageSize, Map<String, Object> search);


    /**
     * 后台管理——添加景区停放点
     * @param
     * @return
     */
    int addSpotParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);

    /**
     * 后台管理——修改景区停放点
     * @param
     * @return
     */
    int editSpotParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);


    /**
     * 后台管理——删除景区停放点
     * @param parkingId
     * @return
     */
    int delSpotParking(Long parkingId);

    /**
     * 后台管理——修改景区停放点状态
     * @param parkingId
     * @param type
     * @return
     */
    int exitSpotParkingType(Long parkingId, String type);

    /**
     * 查询景区中的停车厂
     * @param pageNum
     * @param pageSize
     * @param scenicSpotId
     * @return
     */
    List<SysScenicSpotBroadcast> queryScenicParkingLotLists(int pageNum, int pageSize, String scenicSpotId);

    /**
     * 查询景区中的出入口
     * @param pageNum
     * @param pageSize
     * @param scenicSpotId
     * @return
     */
    List<SysScenicSpotBroadcast> queryScenicSpotEntranceLists(int pageNum, int pageSize, String scenicSpotId);

    /**
     * 出入口导出
     * @param search
     * @return
     */
    List<SysScenicSpotParkingExcel> uploadExcelSpotEntrance(Map<String, Object> search);


    SysScenicSpotParkingWithBLOBs selectByParkingName(String parkingName);


    int importScenicSpot(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);


    int editImportScenicSpot(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);

    SysScenicSpotParkingWithBLOBs getSpotIdAndParkingName(Long parkingScenicSpotId, String parkingName);


    int insert(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);


    SysScenicSpotParkingWithBLOBs getSpotParkingId(Long parkingId);

}

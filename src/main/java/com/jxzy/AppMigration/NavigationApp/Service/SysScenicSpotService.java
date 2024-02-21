package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotFilesExcel;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotService {


    List<SysScenicSpot> queryScenicSpotList();

    PageDataResult currentCity(String lng,String lat,String content,String spotBindingName, Integer sort, Integer pageNum, Integer pageSize);

    PageDataResult searchSpot(PageDTO pageDTO);

    SysScenicSpot spotDetails(String spotId, String lat, String lng);

    /**
     * 根据景区ID查询景区数据
     * @param: parseLong
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    SysScenicSpot queryScenicSpotData(long scenicSpotId);

    /**
     * 更新景区热度
     * @param: scenicSpot
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    int updateScenicSpotHeat(SysScenicSpot scenicSpot);


    /**
     * 查询景区排行
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    List<SysScenicSpot> queryScenicSpotRankingList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 获取全国景区列表
     * 张
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataResult currentCityAll(Integer sort,String content,String spotBindingName, Integer pageNum, Integer pageSize);


    /**
     * 后台管理——景区档案——景区查询
     * @param
     * @param pageNum
     * @param pageSize
     * @param
     * @return
     */
    PageDataResult adminSysScenicSpotList(Integer pageNum, Integer pageSize,Map<String,Object> search);


    /**
     * 后台管理——景区档案——添加
     * @param sysScenicSpot
     * @return
     */
    int adminAddSysScenicSpot(SysScenicSpot sysScenicSpot);

    /**
     * 后台管理——景区档案——修改
     * @param sysScenicSpot
     * @return
     */
    int adminEditSysScenicSpot(SysScenicSpot sysScenicSpot);

    /**
     * 后台管理——景区档案——删除
     * @param spotId
     * @return
     */
    int adminDelSysScenicSpot(String spotId);

    /**
     * 后台管理——景区管理——查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult adminSysScenicSpotFilesList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    /**
     * 后台管理——景区下拉选
     * @return
     */
    List<SysScenicSpot> scenicSpotDrop();


    /**
     * 后台刊——归属地下拉选
     * @return
     */
    List<SysScenicSpotBinding> placeDrop();


    /**
     * 后台管理——景区图片添加
     * @param file
     * @param
     * @return
     */
    int addSpotBanners(MultipartFile[] file, SysScenicSpotBanner sysScenicSpotBanner);

    /**
     * 后台管理——查询景区轮播图
     * @param spotId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataResult getSpotFilesBanners(Long spotId, Integer pageNum, Integer pageSize);

    /**
     * 后台管理——删除景区轮播图
     * @param id
     * @return
     */
    int delSpotFilesBanner(String id);

    /**
     * 添加景区图片
     * @param
     * @param sysScenicSpot
     * @return
     */
    int addSpotFiles( SysScenicSpot sysScenicSpot);

    /**
     * 后台管理——删除景区信息与图片
     * @param spotId
     * @return
     */
    int delSpotFiles(String spotId);

    /**
     * 后台管理——编辑景区坐标与图片
     * @param
     * @param sysScenicSpot
     * @return
     */
    int editSpotFiles( SysScenicSpot sysScenicSpot);

    /**
     * 后台管理——景区轮播图修改
     * @param file
     * @param
     * @return
     */
    int editSpotBanners(MultipartFile[] file, SysScenicSpotBanner sysScenicSpotBanner);

    /**
     * 后台管理—— 修改轮播图启用禁用状态
     * @param id
     * @param type
     * @return
     */
    int editSpotFilesState(Long id, Integer type);

    /**
     * 后台管理——上传景区图片
     * @param file
     * @param spotId
     * @return
     */
    int addSpotPicture(MultipartFile file, Long spotId);

    /**
     * 后台管理——上传景区音频讲解
     * @param file
     * @param scenicSpotId
     * @return
     */
    int adminAddSysScenicSpotAudio(MultipartFile file, Long scenicSpotId);

    /**
     * 根据坐标获取是否在景区内
     * @param
     * @return GPS_COORDINATE
     */
    Map<String,Object>  spotGpsGoordinate(String lng, String lat);

    /**
     * 根据id查询景区
     * @param spotId
     * @return
     */
    SysScenicSpot selectById(Long spotId);

    /**
     * 根据坐标和景区id获取是否在景区内
     * @param lng
     * @param lat
     * @param spotId
     * @return
     */
    Map<String, Object> spotIdGpsCoordinate(String lng, String lat, Long spotId);


    /**
     * 修改景区启用禁用状态
     * @param id
     * @param type
     * @return
     */
    int editSpotState(Long id, Integer type);

    /**
     *景区管理 -- 导出
     * @param search
     * @return
     */
    List<SysScenicSpotFilesExcel> getOrderVoExcelPoi(Map<String, Object> search);

    /**
     * 景区管理 --导入(添加)
     * @param sysScenicSpot
     * @return
     */
    int importScenicSpot(SysScenicSpot sysScenicSpot);

    /**
     * 景区管理 -- 导入（修改）
     * @param sysScenicSpot
     * @return
     */
    int editImportScenicSpot(SysScenicSpot sysScenicSpot);

    /**
     * 景区档案导出
     * @param search
     * @return
     */
    List<SysScenicSpotExcel> uploadExcelSpot(Map<String, Object> search);

    /**
     * 根据坐标和景区id获取是否在景点内
     * @param lng
     * @param lat
     * @param spotId
     * @param broadcastId
     * @return
     */
    int broadcastIdGpsCoordinate(String lng, String lat, Long spotId, Long broadcastId);

    /**
     * 据景区，和坐标，判断坐标是否在景点范围内
     * @param lng
     * @param lat
     * @param spotId
     * @return
     */
    Map<String ,Object> spotIdBroadcastGpsCoordinate(String lng, String lat, Long spotId);

    /**
     * 根据名称查询景区
     * @param scenicSpotName
     * @return
     */
    SysScenicSpot selectBySpotName(String scenicSpotName);

    /**
     * 定时任务中从后台管理中获取最新景区列表添加
     * @param sysScenicSpot
     * @return
     */
    int insert(SysScenicSpot sysScenicSpot);

    /**
     * 获取推荐景区
     * @return
     */
    List<SysScenicSpot> getRecommendSpot();

    List<SysScenicSpot> currentCityAllN(Integer sort, String content, String spotBindingName, Integer pageNum, Integer pageSize);

    List<SysScenicSpot> currentCityN(String lng, String lat, String content, String spotBindingName, Integer sort, Integer pageNum, Integer pageSize);

    SysScenicSpot selectByIdAndName(Long scenicSpotId, String scenicSpotName);

    List<SysScenicSpot> getRecommendSpotNew();

    List<SysScenicSpotAdmissionFee> getSpotIdAdmissionTicket(String spotId);

    SysScenicSpot selectSpotIdByDetail(String scenicSpotId);


//    /**
//     * 批量景区添加拼音
//     */
//    void adminPinyin();

}

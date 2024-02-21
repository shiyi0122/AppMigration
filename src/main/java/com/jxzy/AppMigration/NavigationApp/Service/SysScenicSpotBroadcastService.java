package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotBroadcastExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBroadcastService {
    /**
     * 获取景点播报列表
     * @param: pageNum 当前页
     * @param: pageSize当前页总条数 
     * @param: search  map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcast(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询景区停靠点
     * @param: search map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotBroadcast> queryScenicSpotStop(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询景点排行
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcastList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询景点详情
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs
     * @author: qushaobei
     * @date: 2022/8/5 0005
     */
    List<SysScenicSpotBroadcastExtendWithBLOBs> queryscenicSpotContent(Map<String, Object> search);

    /**
     * 获取景点列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSpotBroadcastList(Integer pageNum, Integer pageSize, Integer sort, Map<String, Object> search);

    /**
     * 景点热度加一
     * @param id
     * @return
     */
    int addHotSpotBroadcast(Long id);

    /**
     * 后台管理——景点搜索
     * @param pageDTO
     * @return
     */
    PageDataResult getBroadcastList(PageDTO pageDTO);

    /**
     * 后台管理--景点资源详情
     * @param broadcastId
     * @return
     */
    PageDataResult  getBroadcastDetails(String broadcastId);


    /**
     * 后台管理--添加景点
     * @param broadcast
     * @return
     */
    int addBroadcast(SysScenicSpotBroadcast broadcast);

    /**
     * 后台管理——删除景点
     * @param broadcastId
     * @param scenicSpotId
     * @return
     */
    int delBroadcast(Long broadcastId, Long scenicSpotId);

    /**
     * 编辑景点
     * @param broadcast
     * @return
     */
    int editBroadcast(SysScenicSpotBroadcast broadcast);

    /**
     * 景点内容新增（文字）
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int addBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    /**
     * 景点内容新增（音频）
     * @param
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int addBroadcastContentExtendAudio( SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);


    /**
     * 上传景点图片
     * @param file
     * @param
     * @return
     */
    int addBroadcastPicture(MultipartFile file, Long broadcastId);


    /**
     * 景点内容修改（图片文字）
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int exitBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    /**
     * 景点内容修改（视频音频）
     * @param
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int exitBroadcastContentExtendAudio( SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    /**
     * 删除景点音频
     * @param broadcastResId
     * @return
     */
    int delBroadcastContentExtendAudio(String broadcastResId);

    /**
     * 景点管理导出
     * @param search
     * @return
     */
    List<SysScenicSpotBroadcastExcel> uploadExcelSpotBroadcast(Map<String, Object> search);


    void upText();


    void editspotpicture(Long spotId);


    /**
     * 根据景点id，查询景点
     * @param broadcastId
     * @return
     */
    SysScenicSpotBroadcast getSpotBroadcastId(Long broadcastId);

    /**
     * 景点管理，--导入（添加）
     * @param sysScenicSpotBroadcast
     * @return
     */
    int importScenicSpotBroadcast(SysScenicSpotBroadcast sysScenicSpotBroadcast);


    /**
     * 景点管理--导入（修改）
     * @param sysScenicSpotBroadcast
     * @return
     */
    int editImportScenicSpot(SysScenicSpotBroadcast sysScenicSpotBroadcast);

    SysScenicSpotBroadcast getSpotBroadcastName(String broadcastName);

    /**
     * 根据景区id和景点名称 查询景点
     * @param scenicSpotId
     * @param broadcastName
     * @return
     */
    SysScenicSpotBroadcast getSpotIdAndBroadcastName(Long scenicSpotId, String broadcastName);

    /**
     * 定时任务中从后台管理获取景点，添加
     * @param sysScenicSpotBroadcast
     * @return
     */
    int insert(SysScenicSpotBroadcast sysScenicSpotBroadcast);

    List<SysScenicSpotBroadcast> getAllSysBroadcast(Map<String, Object> search);

    PageDataResult getAllSysBroadcastPage(Map<String, Object> search);


    int addBroadcastAudio(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);


    int editBroadcastAudio(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int addBroadcastVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int editBroadcastVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int delBroadcastType(Long broadcastId, String type);

    List<SysScenicSpotBroadcast> getSpotIdByBroadcastDropDown(String spotId);

}

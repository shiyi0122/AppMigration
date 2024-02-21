package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategy;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyContent;
import com.jxzy.AppMigration.NavigationApp.entity.UserOperationLog;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserOperationLogDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 18:41
 */
public interface SysStrategyService {

    int addSysStrategy(MultipartFile file, SysStrategy sysStrategy);

    PageDataResult getSysStrategyAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    SysStrategy getSysStrategyAppDetails(Map<String, Object> search);


    int addSysStrategyNumber(Long id);

    PageDataResult getSysStrategyList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int editSysStrategyToExamine(Long id, String state);

    int addSysStrategyNoFile(SysStrategy sysStrategy);

    int editSysStrategyNoFile(SysStrategy sysStrategy);


    /**
     * 我的收藏
     * @param uid
     * @param type
     * @return
     */
    PageDataResult myCollection(String uid, String type,Integer pageNum,Integer pageSize);

    /**
     * 我的喜欢
     * @param uid
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataResult myLike(String uid, String type, Integer pageNum, Integer pageSize);

    /**
     * app端全局搜索
     * @param search
     * @return
     */
    PageDataResult getAllSysStrategy(Map<String, Object> search);

    /**
     * app端上传游记动态
     * @param sysStrategy
     * @return
     */
    int addAppSysStrategyNoFile(SysStrategy sysStrategy);

    /**
     * 删除游记，攻略，广场
     * @param id
     * @return
     */
    int delSysStrategy( Long id);

    /**
     * 添加游记内容
     * @param sysStrategyContent
     * @return
     */
    int addSysStrategyContent(SysStrategyContent sysStrategyContent);


    int delSysStrategyContent(Long id);


    PageDataResult getSysStrategyContentList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<SysStrategy> getAllSysStrategyN(Map<String, Object> search);

    PageDataResult myIntroduction(String uid, String type, Integer pageNum, Integer pageSize);


    int editSysStrategyContent(SysStrategyContent sysStrategyContent);

    int addAppSysStrategyNoFileNew(SysStrategy sysStrategy);

    int delSysStrategyContentPic(Long id);

    int updateSysStrategyContentList(List<SysStrategyContent> sysStrategyContentList);


    List<UserOperationLog> getUserOperation(UserOperationLogDTO userOperationLogDTO);

    int addUserOperation(UserOperationLogDTO userOperationLogDTO);

    List<UserOperationLog> getUserOperationAll(UserOperationLogDTO userOperationLogDTO);
}

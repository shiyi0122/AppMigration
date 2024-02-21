package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategy;
import com.jxzy.AppMigration.NavigationApp.entity.UserOperationLog;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserOperationLogDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 18:42
 * 游记，攻略，广场 相关接口
 */
public interface SysStrategyMapper {
    int insertSelective(SysStrategy sysStrategy);

    List<SysStrategy> list(Map<String, Object> search);

    SysStrategy getSysStrategyAppDetails(Map<String, Object> search);

    Integer getUserStrategyNumber(Long userId, String type);

    int addSysStrategyNumber(Long id);

    List<SysStrategy> getSysStrategyList(Map<String, Object> search);


    int editSysStrategyToExamine(Long id, String state);

    int delete(Long id);

    int updateSelective(SysStrategy sysStrategy);

    SysStrategy selectById(Long sysStrategyId);


    List<SysStrategy> myList(Map<String, Object> search);

    List<UserOperationLog> selectByUser(UserOperationLogDTO userOperationLogDTO);

    int addUserOperation(UserOperationLogDTO userOperationLogDTO);

    List<UserOperationLog> getUserOperationAll(UserOperationLogDTO userOperationLogDTO);

    int updateByPhone(SysGuideAppUsers users);
}

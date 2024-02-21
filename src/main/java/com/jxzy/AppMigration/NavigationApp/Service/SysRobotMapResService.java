package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysRobotMapResService {
    /**
     * 查询地图资源
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    List<SysRobotMapRes> queryMapRes(Map<String, Object> search);

    /**
     * 后台管理--地图查询列表
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataResult getSysRobotMapResList(Map<String, Object> search, Integer pageNum, Integer pageSize);

    /**
     * 后台管理--修改地图启用禁用
     * @param id
     * @param state
     * @return
     */
    int editSysRobotMapResState(Long id, String state);

    /**
     * 后台管理--删除地图相应资源
     * @param id
     * @return
     */
    int delSysRobotMapRes(Long id);

    /**
     * 后台管理--添加地图资源
     * @param file
     * @param sysRobotMapRes
     * @return
     */
    int addRobotMapRes(MultipartFile file, SysRobotMapRes sysRobotMapRes);
}

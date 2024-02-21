package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysHeadMounted;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/25 11:25
 */
public interface SysHeadMountedService {

    PageDataResult getSysHeadMountedList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int editSysHeadMountedState(Long id, String state);

    int addSysHeadMounted(MultipartFile file, SysHeadMounted sysHeadMounted);

    int addSysHeadMountedN(SysHeadMounted sysHeadMounted);

    int editSysHeadMounted(MultipartFile file, SysHeadMounted sysHeadMounted);


    int editSysHeadMountedN(SysHeadMounted sysHeadMounted);


    int delSysHeadMounted(Long id);

    PageDataResult adminSysHeadMountedList(Integer pageNum, Integer pageSize, Map<String, Object> search);



}

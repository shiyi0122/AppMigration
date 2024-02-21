package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 19:05
 */
public interface SysGoodThingsService {

    int addSysGoodThings(MultipartFile file, SysGoodThingsShop sysGoodThingsShop);

    int editSysGoodThings(MultipartFile file, SysGoodThingsShop sysGoodThingsShop);


    int delSysGoodThings(Long id);


    PageDataResult getSysGoodThingsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    PageDataResult getSysGoodThingsAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);


}

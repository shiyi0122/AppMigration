package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainment;
import com.jxzy.AppMigration.NavigationApp.entity.SysGame;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 17:18
 */
public interface SysEntertainmentService {
    int addSysEntertainment(MultipartFile file, SysEntertainment sysEntertainment);

    int editSysEntertainment(MultipartFile file, SysEntertainment sysEntertainment);

    int delSysEntertainment(Long id);

    PageDataResult getSysEntertainmentList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    PageDataResult getSysEntertainmentAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int addSysEntertainmentN(SysEntertainment sysEntertainment);

    int editSysEntertainmentN(SysEntertainment sysEntertainment);

    PageDataResult getAllSysEntertainmentShop(Map<String, Object> search);

    List<SysEntertainment> getAllSysEntertainmentShopN(Map<String, Object> search);


    List<SysEntertainment> uploadExcelEntertainment(Map<String, Object> search);

    SysEntertainment getSysEntertainmentIdDetails(Map<String, Object> search);

    int addGiveTheThumbsUp(Long id, String type, String uid);

    int delGiveTheThumbsUp(Long id, String type, String uid);

}

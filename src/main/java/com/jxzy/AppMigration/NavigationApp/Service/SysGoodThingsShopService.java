package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 18:06
 */
public interface SysGoodThingsShopService {

    int addSysGoodThingsShop(MultipartFile file, SysGoodThingsShop sysGoodThingsShop);


    int editSysGoodThingsShop(MultipartFile file, SysGoodThingsShop sysGoodThingsShop);


    int delSysGoodThingsShop(Long id);

    PageDataResult getSysGoodThingsShopList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int addSysGoodThingsShopBanner(MultipartFile[] file, String id);


    PageDataResult getSysGoodThingsShopAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addGiveTheThumbsUp(Long id, String type, String uid);

    int delGiveTheThumbsUp(Long id, String type, String uid);

    int addSysGoodThingsShopN(SysGoodThingsShop sysGoodThingsShop);

    int editSysGoodThingsShopN(SysGoodThingsShop sysGoodThingsShop);

    PageDataResult getAllSysThingsShop(Map<String, Object> search);

    List<SysGoodThingsShop> getAllSysThingsShopN(Map<String, Object> search);

    List<SysGoodThingsShop> uploadExcelGoodThings(Map<String, Object> search);

}

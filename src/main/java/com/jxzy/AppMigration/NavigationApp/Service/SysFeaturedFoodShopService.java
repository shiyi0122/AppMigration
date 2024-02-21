package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodShop;
import com.jxzy.AppMigration.NavigationApp.entity.SysHotel;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/9 17:32
 */
public interface SysFeaturedFoodShopService {
    int addSysFeaturedFoodShop(MultipartFile file, SysFeaturedFoodShop sysFeaturedFoodShop);


    int editSysFeaturedFoodShop(MultipartFile file, SysFeaturedFoodShop sysFeaturedFoodShop);


    int delSysFeaturedFoodShop(Long id);

    PageDataResult getSysFeaturedFoodShopList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addSysFeaturedFoodShopBanner(MultipartFile[] file, String id);


    PageDataResult getSysFeaturedFoodShopAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addGiveTheThumbsUp(Long id, String type,String uid);

    int delGiveTheThumbsUp(Long id, String type, String uid);

    int addSysFeaturedFoodShopN(SysFeaturedFoodShop sysFeaturedFoodShop);


    int editSysFeaturedFoodShopN(SysFeaturedFoodShop sysFeaturedFoodShop);

    int addSysFeaturedFoodShopBannerN(Long id,String url);


    List<SysFeaturedFoodShop>uploadExcelFeaturedFood(Map<String, Object> search);

}

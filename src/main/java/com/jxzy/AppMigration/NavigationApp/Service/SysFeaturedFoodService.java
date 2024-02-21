package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFood;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodShop;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 13:25
 */
public interface SysFeaturedFoodService {


    int addSysFeaturedFood(MultipartFile file, SysFeaturedFood sysFeaturedFood);


    int editSysFeaturedFood(MultipartFile file, SysFeaturedFood sysFeaturedFood);


    int delSysFeaturedFood(Long id);

    PageDataResult getSysFeaturedFoodList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    PageDataResult getSysFeaturedFoodAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addSysFeaturedFoodN(SysFeaturedFood sysFeaturedFood);

    int editSysFeaturedFoodN(SysFeaturedFood sysFeaturedFood);

    PageDataResult getAllSysFeaturedFoodShop(Map<String, Object> search);

    List<SysFeaturedFoodShop> getAllSysFeaturedFoodShopN(Map<String, Object> search);


}

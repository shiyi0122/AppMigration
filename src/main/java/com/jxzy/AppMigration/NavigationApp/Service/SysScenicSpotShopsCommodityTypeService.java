package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsCommodityType;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/9/6 17:49
 */
public interface SysScenicSpotShopsCommodityTypeService {

    PageDataResult getShopsCommodityTypeList(Integer pageNum, Integer pageSize, String content);


    int addShopsCommodityType(MultipartFile file, SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType);


    int editShopsCommodityType(MultipartFile file, SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType);


    int delShopsCommodityType(Long typeId);


    List<SysScenicSpotShopsCommodityType> shopsCommodityTypeDrop();



}

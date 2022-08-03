package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBanner;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/30 13:48
 */
public interface SysScenicSpotBannerService {
    List getScenicSpotBanner(Map<String, Object> search);

    int addScenicSpotBanner(MultipartFile file, SysScenicSpotBanner sysScenicSpotBanner);


    int editScenicSpotBanner(MultipartFile file, SysScenicSpotBanner sysScenicSpotBanner);


}

package com.jxzy.AppMigration.NavigationApp.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumBanner;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollection;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/8/8 10:23
 */
public interface SysMuseumBannerService extends IService<SysMuseumBanner> {

    //    List<SysMuseumBanner> getMuseumBannerList(Map<String, Object> search);
    List<SysMuseumBanner> getMuseumBannerList(Map<String, Object> search);

    /**
     * @param photo
     * @param museumBanner
     * @return -4:不支持的文件大小，-2:不支持的文件类型
     */
    int save(MultipartFile photo, SysMuseumBanner museumBanner);

    /**
     * @param photo
     * @param museumBanner
     * @return -4:不支持的文件大小,-2:不支持的文件类型
     */
    int edit(MultipartFile photo, SysMuseumBanner museumBanner);

    IPage<SysMuseumBanner> page(IPage<SysMuseumBanner> page, LambdaQueryWrapper<SysMuseumBanner> smblqw, SysMuseumBanner sysMuseumBanner);


}

package com.jxzy.AppMigration.NavigationApp.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollection;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/25 19:23
 */
public interface SysMuseumCollectionService extends IService<SysMuseumCollection> {
    int save(MultipartFile photo,MultipartFile video, SysMuseumCollection sysMuseumCollection);
    int edit(MultipartFile photo,MultipartFile video,SysMuseumCollection sysMuseumCollection);
    /**
     * 根据博物馆名称和藏品名称模糊查询
     * @param map
     * @return
     */
    PageInfo<SysMuseumCollection> getCollectionByCondition(Map<String,Object> map, Integer pageNum, Integer pageSize);


}

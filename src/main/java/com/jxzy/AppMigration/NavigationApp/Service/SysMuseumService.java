package com.jxzy.AppMigration.NavigationApp.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollection;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollectionBroadcast;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/20 11:14
 */
public interface SysMuseumService extends IService<SysMuseum> {
    /**
     * 保存SysMuseum有图片
     *
     * @param
     * @param sysMuseum
     * @return
     */
    int save(MultipartFile photo, SysMuseum sysMuseum);

    int update(MultipartFile file,SysMuseum sysMuseum);

    List<SysMuseum> getMuseumInfo(Integer pageNum, Integer pageSize, SysMuseum sysMuseum);

    PageDataResult getSysMuseumList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    SysMuseum getSysMuseumIdDetails(Map<String, Object> search);

    SysMuseumCollectionBroadcast getCollectionIdBroadcast(Map<String, Object> search);

    Map<String, Object> getGpsMuseum(String lng, String lat);
    List<SysMuseumCollection> getMuseumCollectionIdDetails(Map<String,Object> search);

}

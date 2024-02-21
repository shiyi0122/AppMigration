package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserAlbum;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/15 14:16
 */
public interface SysUserAlbumService {
    int addSysUserAlbum(SysUserAlbum sysUserAlbum);

    PageDataResult sysUserAlbumAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);


}

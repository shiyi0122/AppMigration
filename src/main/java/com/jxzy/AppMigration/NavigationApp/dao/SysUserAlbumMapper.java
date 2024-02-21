package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserAlbum;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/15 14:17
 */
public interface SysUserAlbumMapper {
    int insert(SysUserAlbum sysUserAlbum);

    List<SysUserAlbum> list(Map<String, Object> search);

}

package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLabel;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 10:20
 */
public interface SysSpotLabelMapper {

    int insertSelective(SysSpotLabel sysSpotLabel);

    int updateSelective(SysSpotLabel sysSpotLabel);

    int deleteByPrimaryKey(Long id);

    List<SysSpotLabel> list(String content);

    List<SysSpotLabel> sysSpotLabelDrop();


}

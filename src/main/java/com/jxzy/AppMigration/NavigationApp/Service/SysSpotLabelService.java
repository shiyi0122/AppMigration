package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLabel;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 10:18
 */
public interface SysSpotLabelService {
    int addSysSpotLabel(SysSpotLabel sysSpotLabel);


    int editSysSpotLabel(SysSpotLabel sysSpotLabel);

    int delSysSpotLabel(Long id);


    PageDataResult getSysSpotLabelListList(Integer pageNum, Integer pageSize, String content);

    List<SysSpotLabel> sysSpotLabelDrop();


}

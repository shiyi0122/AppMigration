package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGame;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 17:18
 */
public interface SysGameService {
    int addSysGame(MultipartFile file, SysGame sysGame);

    int editSysGame(MultipartFile file, SysGame sysGame);

    int delSysGame(Long id);

    PageDataResult getSysGameList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    PageDataResult getSysGameAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int addSysGameN(SysGame sysGame);

    int editSysGameN(SysGame sysGame);

    PageDataResult getAllSysGameShop(Map<String, Object> search);

    List<SysGame> getAllSysGameShopN(Map<String, Object> search);



}

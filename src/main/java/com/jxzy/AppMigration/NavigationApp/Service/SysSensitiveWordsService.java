package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysSensitiveWords;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

/**
 * @Author zhang
 * @Date 2023/1/14 15:56
 */
public interface SysSensitiveWordsService {
    int addSysAboutUs(SysSensitiveWords sysSensitiveWords);

    int editSysSensitiveWords(SysSensitiveWords sysSensitiveWords);

    int delSysSensitiveWords(Long id);

    PageDataResult getSysSensitiveWordsList(Integer pageNum, Integer pageSize, String content);

}

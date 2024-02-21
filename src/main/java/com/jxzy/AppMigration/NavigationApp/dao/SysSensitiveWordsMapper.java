package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysSensitiveWords;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/14 15:57
 */
public interface SysSensitiveWordsMapper {
    int insert(SysSensitiveWords sysSensitiveWords);

    int update(SysSensitiveWords sysSensitiveWords);

    int delete(Long id);

    List<SysSensitiveWords> list(String content);
}

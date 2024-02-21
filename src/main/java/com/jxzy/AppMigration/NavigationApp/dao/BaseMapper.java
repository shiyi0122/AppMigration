package com.jxzy.AppMigration.NavigationApp.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper {
    /**
     *
     * @param idList
     * @param status
     * @param field
     * @param tableName
     */
    void updateState(@Param("list") List<String> idList, @Param("status") String status, @Param("field") String field, @Param("tableName") String tableName);

   }

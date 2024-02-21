package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUs;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 15:07
 */
public interface SysAboutUsMapper {

    int insert(SysAboutUs sysAboutUs);

    int update(SysAboutUs sysAboutUs);

    List<SysAboutUs> list(@Param("type") String type, @Param("subversionId") String subversionId);


    int delete(Long id);

    SysAboutUs getTypeIdByList(String typeId,String appSubversionId);

    int deleteByTypeId(Long id);

    SysAboutUs selectById(String id);

    SysAboutUs selectTypeNameAndSubversionId(String yhxy, String subversionId);

}

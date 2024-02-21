package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.UserRole;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserRoleDTO;
import com.jxzy.AppMigration.NavigationApp.util.Result.IBaseMapper;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper extends IBaseMapper<UserRole> {

    PageInfo<UserRole> listByclassify(PageInfo page, @Param("UserRoleDTO") UserRoleDTO userRoleDTO);
}

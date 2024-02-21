package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.UserRole;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserRoleDTO;
import com.jxzy.AppMigration.NavigationApp.util.Result.IBaseService;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageInfo;

public interface UserRoleService extends IBaseService<UserRole> {

    PageInfo<UserRole> listByclassify(PageInfo<UserRole> pageInfo, UserRoleDTO userRoleDTO);


}

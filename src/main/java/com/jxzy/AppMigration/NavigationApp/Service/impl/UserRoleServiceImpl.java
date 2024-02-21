package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.UserRoleService;
import com.jxzy.AppMigration.NavigationApp.dao.UserRoleMapper;
import com.jxzy.AppMigration.NavigationApp.entity.UserRole;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserRoleDTO;
import com.jxzy.AppMigration.NavigationApp.util.Result.BaseServiceImpl;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public PageInfo<UserRole> listByclassify(PageInfo<UserRole> pageInfo, UserRoleDTO userRoleDTO) {
        return userRoleMapper.listByclassify(pageInfo,userRoleDTO);
    }
}

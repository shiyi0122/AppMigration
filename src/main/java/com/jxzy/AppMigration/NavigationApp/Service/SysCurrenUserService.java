package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.SysCurrenUser;

/**
* @ClassName: SysCurrenUserService  
* @Description: 前台用户业务处理层
* @author Mars
* @date 2018年4月3日  
*
 */
public interface SysCurrenUserService {


	SysCurrenUser selectPhoneByUser(String phone);

}
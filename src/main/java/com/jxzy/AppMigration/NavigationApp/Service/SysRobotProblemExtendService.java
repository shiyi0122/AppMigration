package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobotProblemExtend;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

/**
 * @Author zhang
 * @Date 2022/11/3 9:05
 */
public interface SysRobotProblemExtendService {

    int addSysRobotProblemExtend(SysRobotProblemExtend sysRobotProblemExtend);


    int editSysRobotProblemExtend(SysRobotProblemExtend sysRobotProblemExtend);


    int delSysRobotProblemExtend(SysRobotProblemExtend sysRobotProblemExtend);


    PageDataResult getSysRobotProblemExtendList(PageDTO pageDTO);

}

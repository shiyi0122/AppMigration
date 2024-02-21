package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotShopsExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotCorpus;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/7 19:18
 */
public interface SysRobotCorpusService {


    List<SysRobotCorpus> getSysRobotCorpus(String name);


    /**
     * 后台管理--查询语音语义
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSysRobotCorpusList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 后台管理--添加语义语义
     * @param sysRobotCorpus
     * @return
     */
    int addSysRobotCorpus(SysRobotCorpus sysRobotCorpus);

    /**
     * 后台管理--修改语音语义
     * @param sysRobotCorpus
     * @return
     */
    int editSysRobotCorpus(SysRobotCorpus sysRobotCorpus);

    /**
     * 删除语音语义
     * @param id
     * @return
     */
    int delSysRobotCorpus(Long id);

    /**
     * 语音语义导出
     * @param search
     * @return
     */
    List<SysRobotCorpus> uploadExcelRobotCorpus(Map<String, Object> search);

}

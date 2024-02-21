package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobotCorpus;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/7 19:19
 */

public interface SysRobotCorpusMapper {

    List<SysRobotCorpus> getSysRobotCorpus(String name);

    /**
     * 后台管理——语义语义列表查询
     * @param search
     * @return
     */
    List<SysRobotCorpus> getSysRobotCorpusList(Map<String, Object> search);

    /**
     * 后台管理--语音语义添加
     * @param sysRobotCorpus
     * @return
     */
    int insertSelective(SysRobotCorpus sysRobotCorpus);

    /**
     * 后台管理--语音语义编辑
     * @param sysRobotCorpus
     * @return
     */
    int updateByPrimaryKeySelective(SysRobotCorpus sysRobotCorpus);

    /**
     * 后台管理--语义语音删除
     * @param id
     * @return
     */
    int delSysRobotCorpus(Long id);

    SysRobotCorpus selectByPrimaryKey(Long corpusId);

}

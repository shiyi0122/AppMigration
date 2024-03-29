package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNews;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysGuideAppNewsService {
    /**
     * 查询消息列表
     * @param:
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    List<SysGuideAppNews> queryGuideAppNewsListsLimit(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 根据ID删除消息
     * @param: guideId
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    int deleteGuideAppNews(String guideId, String type);

    /**
     * 读取消息
     * @param userId
     * @param guideId
     * @return
     */
    SysGuideAppNews getSysGuideAppNewsRead(String userId, Long guideId);

    /**
     * 读取已读
     * @param userId
     * @return
     */
    int allRead(String userId);


    /**
     * 后台管理—— 查询消息列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSysGuideAppNewsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 添加消息
     * @param sysGuideAppNews
     * @return
     */
    int addSysGuideAppNews(SysGuideAppNews sysGuideAppNews);

    /**
     * 修改消息
     * @param sysGuideAppNews
     * @return
     */
    int editSysGuideAppNews(SysGuideAppNews sysGuideAppNews);

    /**
     * 删除消息
     * @param guideId
     * @return
     */
    int delSysGuideAppNews(Long guideId);

}

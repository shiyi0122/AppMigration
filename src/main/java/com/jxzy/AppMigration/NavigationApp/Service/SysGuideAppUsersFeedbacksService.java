package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysGuideAppUsersFeedbacksService {

    /**
     * 用户意见反馈
     * @param: user
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    int insetUserFeedback(SysGuideAppUsersFeedbacks user);

    /**
     * 后台管理——用户意见反馈列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSysGuideAppNewsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 后台管理——用户意见状态修改
     * @param id
     * @param states
     * @return
     */
    int exitSysGuideAppUsersFeedbacksStates(Long id, String states);


    /**
     * 后台删除用户反馈
     * @param id
     * @return
     */
    int delSysGuideAppUsersFeedbacks(Long id);

    /**
     * 后台回复修改
     * @param sysGuideAppUsersFeedbacks
     * @return
     */
    int exitSysGuideAppUsersFeedbacksReply(SysGuideAppUsersFeedbacks sysGuideAppUsersFeedbacks);


    /**
     * 根据反馈id，获取反馈图片列表
     * @param id
     * @return
     */
    List<SysGuideAppUsersFeedbacks> getUsersFeedbacksPicturl(Long id);

}

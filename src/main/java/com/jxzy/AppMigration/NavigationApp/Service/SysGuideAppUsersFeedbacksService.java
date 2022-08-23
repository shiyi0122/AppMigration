package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;

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
}

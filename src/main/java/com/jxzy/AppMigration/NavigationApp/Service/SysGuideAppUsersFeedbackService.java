package com.jxzy.AppMigration.NavigationApp.Service;

public interface SysGuideAppUsersFeedbackService {
    /**
     * 用户反馈
     * @param: userId 用户ID
     * @param: feedbackContent 反馈内容
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    int insetUsersFeedback(Long userId, String feedbackContent);
}

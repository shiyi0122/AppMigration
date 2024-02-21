package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/12 18:07
 * 用户关注表
 */
@Data
public class SysUserFans {

    private Long id;

    private Long fansUserId;

    private Long coverFansUserId;

    private String createTime;

    private String updateTime;

}

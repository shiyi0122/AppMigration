package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/8 10:03
 */
@Data
public class SysScenicSpotAdmissionFeeNotice {

    private Long id;

    private Long spotId;

    private Long noticeLevelId;

    private String noticeType;

    private String noticeLevel;

    private String noticeName;

    private String noticeContent;

    private String createTime;

    private String updateTime;
}

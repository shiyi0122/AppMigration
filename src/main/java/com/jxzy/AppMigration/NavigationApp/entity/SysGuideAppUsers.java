package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysGuideAppUsers {
    private Long userId;

    private String userPhone;
    //登录令牌
    private String longinTokenId;
    //推送ID
    private String userClientGtId;
    //手机唯一标识
    private String phoneSign;
    //创建时间
    private String createDate;
    //更新时间
    private String updateDate;


    //新添加字段

    private String userName;
    //qq登录唯一标识
    private String qqId;
    //微信登录唯一标识
    private String weChatId;
    //苹果登录唯一标识
    private String appleId;
    //用户头像
    private String portraitPic;
    //性别
    private String userGender;
    //账号状态0正常1封号
    private String userState;
    //ip归属地
    private String ipAscription;
    //介绍
    private String introduce;

    private String fristSpotId;

    private String fristSpotName;

    private String spotId;

    private String spotName;

    private String userNowIp;

    //数据表里没有

    private Integer likeNumber;

    private Integer fansNumber;

    private Integer collectionNumber;

    private Integer userVisitNumber;

    private String accessToken;
    private String openId;
}
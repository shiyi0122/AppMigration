package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDtoConvert;
import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/11 18:32
 * 攻略，游记，广场 实体类
 */
@Data
public class SysStrategy extends BaseDTO {

    private Long id;

    private String   title;

    private Long userId;

    private Long strategySpotId;

    private String placeOfOwnership;

    private String strategyIntroduction;

    private String content;

    private String coverPic;

    private String createTime;

    private String updateTime;

    private Long strategyNumber;

    private String toExamineResult;

    private String type;

    private String strategyState;

    private String province;

    private String city;

    private String area;

    private String takePhotos;

    private String reviewComments;

    private String temporaryHeadUrl;

    private String temporaryUserName;

    private String toExamineTime;

    private String publishingType;

    private String publishCoordinates;

    private String mapScreenshotUrl;

    //以下字段数据表里没有
    private String  scenicSpotName;
    //作者名称
    private String userName;
    //头像地址
    private String portraitPic ;
    //作者发布文章数量
    private String userStrategyNumber;

    //喜欢数量
    private String likeNumber;
    //评论数量
    private String commentNumber;
    //收藏数量
    private String collectionNumber;
    //是否关注作者
    private String isFans;
    //是否收藏
    private String isCollection;
    //是否喜欢
    private String isLike;

    //攻略类型，1，景区，2城市
    private String cityType;


    //图片数量
    private Integer pictureNumber;

    //省名称
    private String provinceName;
    //市名称
    private String cityName;
    //区名称
    private String areaName;

    private List<SysStrategyContent> contentlist;



}

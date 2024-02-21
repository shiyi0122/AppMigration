package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/6/17 16:00
 */
@Data
public class SysMuseumCollection {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId("id")
    private Long id;

    @ApiModelProperty("藏品id(马老师用的藏品id)")
    @TableField("collection_id")
    private String collectionId;

    @ApiModelProperty("博物馆id")
    @TableField("museum_id")
    private Long museumId;

    @ApiModelProperty("藏品名称")
    @TableField("museum_collection_name")
    private String museumCollectionName;

    @ApiModelProperty("藏品拼音")
    @TableField("museum_collection_pinyin")
    private String museumCollectionPinyin;

    @ApiModelProperty("藏品楼层")
    @TableField("museum_collection_floor")
    private String museumCollectionFloor;

    @ApiModelProperty("藏品介绍")
    @TableField("museum_collection_introduce")
    private String museumCollectionIntroduce;

    @ApiModelProperty("藏品图片")
    @TableField("museum_collection_pictures")
    private String museumCollectionPictures;

    @ApiModelProperty("封面图片")
    @TableField("museum_collection_cover_pic")
    private String museumCollectionCoverPic;

    @ApiModelProperty("长(CM)")
    @TableField("museum_collection_long")
    private String museumCollectionLong;

    @ApiModelProperty("宽(CM)")
    @TableField("museum_collection_wide")
    private String museumCollectionWide;

    @ApiModelProperty("高(CM)")
    @TableField("museum_collection_high")
    private String museumCollectionHigh;

    @ApiModelProperty("外形特点")
    @TableField("museum_collection_feature")
    private String museumCollectionFeature;

    @ApiModelProperty("藏品故事")
    @TableField("museum_collection_story")
    private String museumCollectionStory;

    @ApiModelProperty("藏品音频链接")
    @TableField("museum_collection_video")
    private String museumCollectionVideo;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("修改时间")
    @TableField("update_time")
    private String updateTime;

    @ApiModelProperty("藏品所在行坐标")
    @TableField("line_x")
    private String lineX;

    @ApiModelProperty("藏品所在列坐标")
    @TableField("line_y")
    private String lineY;

    @ApiModelProperty("博物馆名称")
    @TableField(exist = false)
    private String museumName;
}
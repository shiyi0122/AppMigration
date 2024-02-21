package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/6/15 10:32
 */
@Data
public class SysMuseum {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId("id")
    private Long id;

    @ApiModelProperty("归属景区id")
    @TableField("spot_id")
    private Long spotId;

    @ApiModelProperty("博物馆名称")
    @TableField("museum_name")
    private String museumName;

    @ApiModelProperty("博物馆拼音")
    @TableField("museum_name_pinying")
    private String museumNamePinying;

    @ApiModelProperty("百度坐标")
    @TableField("museum_gps_bai_du")
    private String museumGpsBaiDu;

    @ApiModelProperty("高德坐标")
    @TableField("museum_gps_gao_de")
    private String museumGpsGaoDe;

    @ApiModelProperty("84坐标")
    @TableField("museum_gps")
    private String museumGps;

    @ApiModelProperty("坐标半径")
    @TableField("coordinate_radius")
    private String coordinateRadius;

    @ApiModelProperty("博物馆介绍")
    @TableField("museum_introduce")
    private String museumIntroduce;

    @ApiModelProperty("博物馆介绍拼音")
    @TableField("museum_introduce_pinying")
    private String museumIntroducePingying;

    @ApiModelProperty("地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("营业时间")
    @TableField("business_hours")
    private String businessHours;

    @ApiModelProperty("推荐人数")
    @TableField("recommend_number")
    private String recommendNumber;

    @ApiModelProperty("封面图片")
    @TableField("cover_pic")
    private String coverPic;

    @ApiModelProperty("详情页图片")
    @TableField("details_pic")
    private String detailsPic;

    /*@ApiModelProperty("博物馆缩略图(暂未使用)")
    @TableField("museum_thumbnail_pic")
    private String museumThumbnailPic;*/

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("修改时间")
    @TableField("update_time")
    private String updateTime;

    @ApiModelProperty("省")
    @TableField("province")
    private Long province;

    @ApiModelProperty("市")
    @TableField("city")
    private Long city;

    @ApiModelProperty("区")
    @TableField("area")
    private Long area;

    @ApiModelProperty("播报内容")
    @TableField("broadcast_content")
    private String broadcastContent;

    @ApiModelProperty("介绍")
    @TableField("introduce")
    private String introduce;

    @ApiModelProperty("门票")
    @TableField("admission_fee")
    private String admissionFee;

    @ApiModelProperty("识别次数(多少次算识别成功)")
    @TableField("identify_number")
    private String identifyNumber;

    @ApiModelProperty("博物馆楼层")
    @TableField("museum_floor")
    private String museumFloor;

    @ApiModelProperty("博物馆在线状态 1：在线 0：下线")
    @TableField(value = "museum_online",fill = FieldFill.INSERT)
    private String museumOnline;

    @ApiModelProperty(value = "博物馆藏品")
    @TableField(exist = false)
    private List<SysMuseumCollection> museumCollectionList;

    @ApiModelProperty("博物馆地图")
    @TableField(exist = false)
    private List<SysMuseumMapRes> museumMapResList;

    @ApiModelProperty("博物馆轮播图")
    @TableField(exist = false)
    private List<SysMuseumBanner> museumBannerList;

    @ApiModelProperty("博物馆语音讲解点")
    @TableField(exist = false)
    private List<SysMuseumCollectionBroadcast> museumCollectionBroadcastList;

    @ApiModelProperty("博物馆标签")
    @TableField(exist = false)
    private List<SysMuseumTag> museumTagList;
    @TableField(exist = false)
    private String lineX;
    @TableField(exist = false)
    private String lineY;

    @TableField(exist = false)
    private Double distance;
    @TableField(exist = false)
    private Integer sysMuseumCollectionNumber;
    @TableField(exist = false)
    private Integer sysMuseumCollectionBroadcastNumber;
    @TableField(exist = false)
    @ApiModelProperty("博物馆平面图")
    private List<SysMuseumPlanView> museumPlanViewList;

    @ApiModelProperty("博物馆的轮播图数量")
    @TableField(exist = false)
    private Long museumBannerCount;

    @ApiModelProperty("博物馆的地图数量")
    @TableField(exist = false)
    private Long museumMapResCount;

    @ApiModelProperty("博物馆的藏品数量")
    @TableField(exist = false)
    private Long museumCollectionCount;

    @ApiModelProperty("博物馆的语音讲解点数量")
    @TableField(exist = false)
    private Long museumCollectionBroadcastCount;

}

package com.jxzy.AppMigration.NavigationApp.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@Data
public class SysScenicSpot {

    @ApiModelProperty("景区id")
    private Long scenicSpotId;
    @ApiModelProperty("景区名称")
    private String scenicSpotName;
    @ApiModelProperty("景区联系人")
    private String scenicSpotContact;
    @ApiModelProperty("景区联系人手机号")
    private String scenicSpotPhone;
    @ApiModelProperty("邮箱")
    private String scenicSpotEmail;
    @ApiModelProperty("景区地址")
    private String scenicSpotAddres;
    @ApiModelProperty("邮编")
    private String scenicSpotPostalCode;
    @ApiModelProperty("景区机器人总数量")
    private String scenicSpotRobotTotal;
    @ApiModelProperty("景区状态：10 正常 50 禁用 90 删除")
    private String scenicSpotStatus;
    @ApiModelProperty("景区机器人开机语言")
    private String scenicSpotOpenword;
    @ApiModelProperty("景区机器人关机语言")
    private String scenicSpotCloseword;
    @ApiModelProperty("景区机器人调度费")
    private String scenicSpotBeyondPrice;
    @ApiModelProperty("周六日单价")
    private String scenicSpotWeekendPrice;
    @ApiModelProperty("工作日单价")
    private String scenicSpotNormalPrice;
    @ApiModelProperty("周六日起租价格")
    private String scenicSpotWeekendRentPrice;
    @ApiModelProperty("工作日起租价格")
    private String scenicSpotNormalRentPrice;
    @ApiModelProperty("周末计费时间设置（默认值1分钟）--")
    private String scenicSpotWeekendTime;
    @ApiModelProperty("周末封顶价格（起租价格不能超出封顶价格）")
    private String scenicSpotNormalTime;
    @ApiModelProperty("剩余时间--")
    private String scenicSpotRemainingTime;
    @ApiModelProperty("起租时间设置 单位分钟")
    private String scenicSpotRentTime;
    @ApiModelProperty("景区坐标")
    private String coordinateRange;
    @ApiModelProperty("景区押金金额")
    private String scenicSpotDeposit;
    @ApiModelProperty("工作日封顶价格（起租价格不能超出封顶价格）")
    private String normalCappedPrice;
    @ApiModelProperty("周末封顶价格（起租价格不能超出封顶价格）")
    private String weekendCappedPrice;
    @ApiModelProperty("随机播报时间设置默认值1分钟")
    private String randomBroadcastTime;
    @ApiModelProperty("1：已运营景区 2：测试3、预运营景区")
    private String robotWakeupWords;
    @ApiModelProperty("服务器 1 腾讯云 2 阿里云")
    private String scenicSpotTheServer;
    @ApiModelProperty("景区创建时间")
    private String createDate;
    @ApiModelProperty("景区修改时间")
    private String updateDate;
    @ApiModelProperty("景区父ID")
    private Long scenicSpotFid;
    @ApiModelProperty("景区市ID")
    private Long scenicSpotSid;
    @ApiModelProperty("景区区ID")
    private Long scenicSpotQid;
    @ApiModelProperty("超出禁区或者电子围栏 锁定次数限制")
    private String scenicSpotFrequency;
    @ApiModelProperty("电子围栏锁定时间")
    private String scenicSpotFenceTime;
    @ApiModelProperty("禁区锁定时间")
    private String scenicSpotForbiddenZoneTime;
    @ApiModelProperty("测试开始时间")
    private String testStartTime;
    @ApiModelProperty("试运营开始时间")
    private String trialOperationsTime;
    @ApiModelProperty("正式运营时间")
    private String formalOperationTime;
    @ApiModelProperty("停止运营时间")
    private String stopOperationTime;
    @ApiModelProperty("景区归属公司ID")
    private Long companyId;
    @ApiModelProperty("景区暂停运营状态 1是正常运营（默认值）0是暂停运营")
    private String pauseState;
    @ApiModelProperty("上班时间")
    private String workTime;
    @ApiModelProperty("下班时间")
    private String closingTime;
    @ApiModelProperty("景区停运播报内容")
    private String pauseBroadcast;
    @ApiModelProperty("上班播报内容")
    private String workBroadcast;
    @ApiModelProperty("下班播报内容")
    private String closingBroadcast;
    @ApiModelProperty("机器人照明开启时间")
    private String lampOpeningTime;
    @ApiModelProperty("机器人照明关闭时间")
    private String lampClosingTime;
    @ApiModelProperty("照明时间")
    private String lampLightingTime;
    @ApiModelProperty("起租免单时间设置")
    private String freeTimeSetting;
    @ApiModelProperty("赠送时常设置")
    private String giftTimeSetting;
    @ApiModelProperty("赠送使用时常设置")
    private String giftUsageSetting;
//    @ApiModelProperty("景区图片")
//    private String scenicSpotImgUtl;
    @ApiModelProperty("景区图片")
    private String scenicSpotImgUrl;
    @ApiModelProperty("拼音名称")
    private String pinYingName;

    @ApiModelProperty("景区归属公司")
    private String  scenicSpotCompany;

    private String total;
    @ApiModelProperty("景点坐标")
    private String broadcastGps;
    @ApiModelProperty("热度")
    private Long heat;
    @ApiModelProperty("景区宣传语")
    private String  scenicSpotSlogan;
    @ApiModelProperty("")
    private List<SysScenicSpotHeat> heatList;

    @ApiModelProperty("景区星级")
    private Long startLevel;
    @ApiModelProperty("景区介绍")
    private String  introduction;
    @ApiModelProperty("百度坐标")
    private String  coordinateRangeBaiDu;
    @ApiModelProperty("高德坐标")
    private String  coordinateRangeGaoDe;
    @ApiModelProperty("坐标半径")
    private String  coordinateRangeAdius;
    @ApiModelProperty("景区标签")
    private String  spotLabel;
    @ApiModelProperty("游览时长")
    private String  tourDuration;

    @ApiModelProperty("景区播报")
    private String broadcastContent;
    @ApiModelProperty("门票")
    private String admissionTicket;
    @ApiModelProperty("是否需要门票")
    private String ifNeedAdmissionTicket;
    @ApiModelProperty("")
    private List<SysScenicSpotAdmissionFee> admissionFeeList;

    @ApiModelProperty("营业状态")
    private String businessStatus;


    @ApiModelProperty("距离")
    private Double distance ;
    @ApiModelProperty("景点数量")
    private Integer scenicSpotBroadcastCount;
    @ApiModelProperty("路径")
    private String url;

    @ApiModelProperty("景区音频路径")
    private String  scneicSpotAudioUrl;

    @ApiModelProperty("点赞次数")
    private Integer fabulousCount;
    @ApiModelProperty("收藏次数")
    private Integer collectionCount;

    @ApiModelProperty("平均驻足时长")
    private Double avgTime;

    @ApiModelProperty("景区归属地名称")
    private String scenicSpotFname;

    @ApiModelProperty("游客数量")
    private Integer peopleCount;

    @ApiModelProperty("轮播图数量")
    private Integer bannerCount;

    @ApiModelProperty("榜单类型")
    private Integer listType;

    @ApiModelProperty("动态标题")
    private String dynamicTitle;

    @ApiModelProperty("归属市区")
    private String cityName;


}
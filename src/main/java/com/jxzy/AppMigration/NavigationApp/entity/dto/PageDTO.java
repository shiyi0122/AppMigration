package com.jxzy.AppMigration.NavigationApp.entity.dto;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 获取分页数据DTO
 *
 * @author
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PageDTO extends BaseDTO {

    @Max(Long.MAX_VALUE)
    @Min(1)
    private Long id;

    @Max(Integer.MAX_VALUE)
    @Min(1)
    @NotNull
    private Integer pageNum;

    @Max(Integer.MAX_VALUE)
    @Min(1)
    @NotNull
    private Integer pageSize;

    //景区id
    @ApiModelProperty(value = "景区id")
    private Long spotId;

    //经度
    @ApiModelProperty(value = "经度")
    private String lng;
    //纬度
    @ApiModelProperty(value = "纬度")
    private String lat;

    //排序
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "景区名称")
    private String spotName;
    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "归属地id")
    private Long spotBindingId;
    @ApiModelProperty(value = "状态")
    private String type;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "景点id")
    private String broadcastId;
    @ApiModelProperty(value = "景点名称")
    private String  broadcastName;
    @ApiModelProperty(value = "店铺id")
    private String shopsId;
    @ApiModelProperty(value = "店铺商品类型名称")
    private String shopsTypeName;
    @ApiModelProperty(value = "类型")
    private String state;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "消息标题")
    private String guideTitle;
    @ApiModelProperty(value = "出入口名称")
    private String parkingName;
    @ApiModelProperty(value = "归属地名称")
    private String spotBindingName;

    @ApiModelProperty(value = "攻略类型1景区，2城市")
    private String cityType;

    private String spotType;


    private String province;

    private String city;

    private String area;

    private String isPeriphery;


    private String scenicSpotFid;

    private String scenicSpotSid;

    private String  scenicSpotQid;

    //博物馆id
    private String museumId;

    private String museumName;




}

package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/22 16:27
 * 景点饱和相关接口
 */
@Data
public class SysScenicSpotBroadcastSaturation extends BaseDTO {

    private Long id;

    @ApiModelProperty(value = "景区id")
    private Long spotId;

    @ApiModelProperty(value = "景点id")
    private Long broadcastId;
    @ApiModelProperty(value = "人数")
    private Long peopleCount;

    private String createTime;

    private String updateTime;
    @ApiModelProperty(value = "类型，0景点，1停靠点，2出口，3卫生间")
    private String type;
    @ApiModelProperty(value = "景点承受人数")
    private Long bearPeople;
    @ApiModelProperty(value = "饱和度")
    private String saturation;

}

package com.jxzy.AppMigration.NavigationApp.entity.dto;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 搜索数据DTO
 *
 * @author
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchDTO extends BaseDTO {

    @ApiModelProperty(value = "id")
    private String  id;

    @ApiModelProperty(value = "景区id")
    private String  spotId;
    @ApiModelProperty(value = "景区name")
    private String  spotName;

    @ApiModelProperty(value = "景点id")
    private String broadcastId;

    @ApiModelProperty(value = "商铺id")
    private String  shopsId;

    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @ApiModelProperty(value = "手机号")
    private String phone;


    private String title;

    private String name;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


    //经度
    @ApiModelProperty(value = "经度")
    private String lng;
    //纬度
    @ApiModelProperty(value = "纬度")
    private String lat;
    //排序
    @ApiModelProperty(value = "排序")
    private Integer sort;


}
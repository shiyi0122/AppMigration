package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/30 11:16
 */
@Data
public class SysMuseumGpsCoordinate {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("坐标主键ID")
    @TableId(value = "coordinate_id", type = IdType.AUTO)
    private Long coordinateId;

    @ApiModelProperty("景区主键ID")
    @TableField("coordinate_scenic_spot_id")
    private Long coordinateScenicSpotId;

    @ApiModelProperty("WGS84坐标系 电子围栏")
    @TableField("coordinate_outerring")
    private String coordinateOuterring;

    @ApiModelProperty("百度坐标系 电子围栏")
    @TableField("coordinate_outerring_bai_du")
    private String coordinateOuterringBaiDu;

    @ApiModelProperty("外侧警告缓冲范围")
    @TableField("warning_loop_coordinate_group")
    private String warningLoopCoordinateGroup;

    @ApiModelProperty("内侧警告缓冲范围")
    @TableField("inside_warning")
    private String insideWarning;

    @ApiModelProperty("是否启用")
    @TableField("coordinate_parking_type")
    private String coordinateParkingType;

    @ApiModelProperty("创建时间")
    @TableField("create_date")
    private String createDate;

    @ApiModelProperty("更新时间")
    @TableField("update_date")
    private String updateDate;


}

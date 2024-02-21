package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/10 19:33
 */
@Data
public class SysUserMapSignRemarks extends BaseDTO {
    @ApiModelProperty(value = "id")
    private Long id ;

    @ApiModelProperty(value = "景区id")
    private Long spotId;
    @ApiModelProperty(value = "用户id")
    private Long appUsersId;
    @ApiModelProperty(value = "备注内容")
    private String remarksContent;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "修改时间")
    private String updateTime;
    @ApiModelProperty(value = "坐标")
    private String  coordinate;
    @ApiModelProperty(value = "景区名称")
    private String  scenicSpotName;

}

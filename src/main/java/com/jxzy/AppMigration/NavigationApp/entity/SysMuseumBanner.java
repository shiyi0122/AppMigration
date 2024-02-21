package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/8/8 10:17
 */
@Data
public class SysMuseumBanner {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId("id")
    private Long id;

    @ApiModelProperty("博物馆id")
    @TableField("museum_id")
    private Long museumId;

    @ApiModelProperty("轮播图")
    @TableField("museum_banner_url")
    private String museumBannerUrl;

    @ApiModelProperty("序号(按序号顺序播放)")
    @TableField("sort")
    private String sort;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("修改时间")
    @TableField("update_time")
    private String updateTime;

    @ApiModelProperty("是否启用，1启用2未启用")
    @TableField("state")
    private String state;

    @ApiModelProperty("图片类型")
    @TableField("museum_banner_type")
    private String museumBannerType;

    @ApiModelProperty("图片大小")
    @TableField("museum_banner_size")
    private Long museumBannerSize;

}

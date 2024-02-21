package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysMuseumMapRes对象", description = "地图资源维护")
public class SysMuseumMapRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId("res_id")
    private Long resId;

    @ApiModelProperty("博物馆id")
    @TableField("res_museum_id")
    private Long resMuseumId;

    @ApiModelProperty("资源下载地址")
    @TableField("res_url")
    private String resUrl;

    @ApiModelProperty("资源是否启用")
    @TableField(value = "res_type",fill = FieldFill.INSERT_UPDATE )
    private String resType;

    @ApiModelProperty("资源大小")
    @TableField("res_size")
    private String resSize;

    @ApiModelProperty("地图资源版本自增形式")
    @TableField(value = "res_version",fill = FieldFill.INSERT_UPDATE)
    private String resVersion;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @ApiModelProperty("资源楼层")
    @TableField("res_floor")
    private String resFloor;

    @ApiModelProperty("地图图片(png格式，大小不超过100MB)")
    @TableField("res_pic")
    private String resPic;

    @ApiModelProperty("标注地图(png格式，大小不超过100MB)")
    @TableField("res_mark_pic")
    private String resMarkPic;

    @ApiModelProperty("标注地图文件(dat格式，大小不超过100MB)")
    @TableField("res_mark_pic_file")
    private String resMarkPicFile;

    @ApiModelProperty("博物馆名称")
    @TableField(exist = false)
    private String museumName;
}

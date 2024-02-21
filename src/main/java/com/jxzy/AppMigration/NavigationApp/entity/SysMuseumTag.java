package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author libofan
 * @since 2023-10-30
 */
@Getter
@Setter
@TableName("sys_museum_tag")
@ApiModel(value = "SysMuseumTag对象", description = "")
public class SysMuseumTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签id")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("标签内容")
    @TableField("content")
    private String content;


}

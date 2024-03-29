package com.jxzy.AppMigration.NavigationApp.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 自定义return
 * 曲绍备
 */
@Data
@ApiModel(description = "自定义return")
@NoArgsConstructor
@AllArgsConstructor
public class ReturnModel implements Serializable {

    @ApiModelProperty(value = "数据主体" , required = true)
    private Object data;

    @ApiModelProperty(value = "成功失败信息")
    private String msg;

    @ApiModelProperty(value = "请求返回状态")
    private String state;

    @ApiModelProperty(value = "未知")
    private String type;

    @ApiModelProperty(value = "数据总数")
    private Integer total;


    public ReturnModel(Object data , String msg , String state , String type){
        this.data = data;
        this.msg = msg;
        this.state = state;
        this.type = type;
    }
}

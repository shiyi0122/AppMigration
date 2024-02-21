package com.jxzy.AppMigration.NavigationApp.util.Result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(
        value = "PageRequest",
        description = "分页请求参数"
)
public class PageRequest {
    @ApiModelProperty(
            value = "页大小",
            example = "10"
    )
    private long size = 10L;
    @ApiModelProperty(
            value = "当前页数",
            example = "1"
    )
    private long current = 1L;
    @ApiModelProperty("排序字段，DESC")
    private List<String> descOrders;
    @ApiModelProperty("排序字段，ASC")
    private List<String> ascOrders;

    public PageRequest() {
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return this.current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public List<String> getDescOrders() {
        return this.descOrders;
    }

    public void setDescOrders(List<String> descOrders) {
        this.descOrders = descOrders;
    }

    public List<String> getAscOrders() {
        return this.ascOrders;
    }

    public void setAscOrders(List<String> ascOrders) {
        this.ascOrders = ascOrders;
    }
}

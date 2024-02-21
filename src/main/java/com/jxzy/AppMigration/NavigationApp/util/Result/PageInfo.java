package com.jxzy.AppMigration.NavigationApp.util.Result;




import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApiModel(
        value = "PageInfo",
        description = "分页数据对象"
)
@JsonIgnoreProperties({"orders", "hitCount", "searchCount"})
@JsonNaming(PagePropertyNamingStrategy.class)
public class PageInfo<T> implements IPage<T> {
    private static final long serialVersionUID = -8551077538866242945L;
    @ApiModelProperty(
            hidden = true
    )
    private List<OrderItem> orders;
    @ApiModelProperty("当前页数据")
    private List<T> records;
    @JsonSerialize(
            using = PageInfoSerializer.class
    )
    @ApiModelProperty("数据总条数")
    private long total;
    @JsonSerialize(
            using = PageInfoSerializer.class
    )
    @ApiModelProperty("页大小")
    private long size;
    @JsonSerialize(
            using = PageInfoSerializer.class
    )
    @ApiModelProperty("当前页数")
    private long current;
    @JsonSerialize(
            using = PageInfoSerializer.class
    )
    @ApiModelProperty("总页数")
    private long pages;
    @ApiModelProperty(
            hidden = true
    )
    private boolean optimizeCountSql;
    @ApiModelProperty(
            hidden = true
    )
    private boolean searchCount;
    @ApiModelProperty(
            hidden = true
    )
    private boolean hitCount;

    public PageInfo() {
        this.orders = new ArrayList();
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.pages = 0L;
        this.optimizeCountSql = true;
        this.searchCount = true;
    }

    public PageInfo(long current, long size) {
        this(current, size, 0L);
    }

    public PageInfo(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PageInfo(long current, long size, boolean isSearchCount) {
        this(current, size, 0L, isSearchCount);
    }

    public PageInfo(long current, long size, long total, boolean isSearchCount) {
        this.orders = new ArrayList();
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.pages = 0L;
        this.optimizeCountSql = true;
        this.searchCount = true;
        if (current > 1L) {
            this.current = current;
        }

        this.size = size;
        this.total = total;
        this.searchCount = isSearchCount;
    }

    public List<OrderItem> orders() {
        return this.getOrders();
    }

    public List<T> getRecords() {
        return this.records;
    }

    public PageInfo<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public PageInfo<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getSize() {
        return this.size;
    }

    public PageInfo<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

    public PageInfo<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    public boolean isSearchCount() {
        return this.total < 0L ? false : this.searchCount;
    }

    public List<OrderItem> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public PageInfo<T> setSearchCount(boolean isSearchCount) {
        this.searchCount = isSearchCount;
        return this;
    }

    public PageInfo<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    public void hitCount(boolean hit) {
        this.hitCount = hit;
    }

    public boolean isHitCount() {
        return this.hitCount;
    }

    public long getPages() {
        if (this.getSize() == 0L) {
            return 0L;
        } else {
            long pages = this.getTotal() / this.getSize();
            if (this.getTotal() % this.getSize() != 0L) {
                ++pages;
            }

            return pages;
        }
    }

    public static <T> PageInfo<T> empty() {
        return new PageInfo(0L, 0L, 0L);
    }
}

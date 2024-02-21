package com.jxzy.AppMigration.NavigationApp.util.Result.mvc.action;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageInfo;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageRequest;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageUtil;

/**
 * <p>
 *
 * </p>
 *
 * @author Han
 * @since 2022/3/23
 */
public class BaseAction {
    public BaseAction() {
    }

    public <P> PageInfo<P> getPageInfo(PageRequest pageRequest) {
        return PageUtil.getPageInfo(pageRequest);
    }

    public <P> PageInfo<P> getPageInfo(long current, long size) {
        return PageUtil.getPageInfo(current, size);
    }

    public <P> Page<P> getPage(PageRequest pageRequest) { return new Page<P>(pageRequest.getCurrent(), pageRequest.getSize()); }

    public <P> Page<P> getPage(long current, long size) { return new Page<P>(current, size); }
}

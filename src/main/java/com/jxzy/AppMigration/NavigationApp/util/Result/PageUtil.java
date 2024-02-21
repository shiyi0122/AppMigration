package com.jxzy.AppMigration.NavigationApp.util.Result;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description: 分页工具类<br>
 * Create Date: 2022年3月3日<br>
 * Modified By：<br>
 * Modified Date：<br>
 * Why & What is modified：<br>
 * @author Han
 * @version 1.0
 */
public class PageUtil {

    /**
     * Description: 构造分页参数<br>
     * Created date: 2022年3月3日
     * @param pageRequest 分页请求
     * @return 分页参数对象
     * @author Xu
     */
    public static <P> PageInfo<P> getPageInfo(PageRequest pageRequest) {
        if (pageRequest == null) {
            return null;
        }
        PageInfo<P> page = new PageInfo<>(pageRequest.getCurrent(), pageRequest.getSize());
        List<OrderItem> orders = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pageRequest.getAscOrders())) {
            pageRequest.getAscOrders().forEach(order -> orders.add(OrderItem.asc(order)));
        }
        if (!CollectionUtils.isEmpty(pageRequest.getDescOrders())) {
            pageRequest.getDescOrders().forEach(order -> orders.add(OrderItem.desc(order)));
        }
        page.setOrders(orders);
        return page;
    }

    /**
     * Description: 构造分页参数<br>
     * Created date: 2022年3月3日
     * @param current 当前页数
     * @param size 页大小
     * @return 分页参数对象
     * @author Han
     */
    public static <P> PageInfo<P> getPageInfo(long current, long size) {
        return new PageInfo<>(current, size);
    }

    /**
     * Description: 逻辑分页<br>
     * Created date: 2022年3月3日
     * @param pageInfo 分页参数对象
     * @param list 参与分页的数据列表
     * @return 分页参数对象
     * @author Han
     */
    public static <P> PageInfo<P> getLogicalPageInfo(PageInfo<P> pageInfo, List<P> list) {
        //清空分页结果数据
        pageInfo.setRecords(Collections.emptyList());
        //判断参加逻辑分页的数据参数是否合法
        if (list != null && list.size() != 0){
            //总数据条数
            int totalCount = list.size();
            //分页页数
            int pageCount;
            //每页数据条数
            int size = (int) pageInfo.getSize();
            //临时计算页数
            int m = totalCount % size;
            //当前页数
            int currentPage = (int) pageInfo.getCurrent();
            //计算分页页数
            if (m > 0) {
                pageCount = totalCount / size + 1;
            } else {
                pageCount = totalCount / size;
            }
            //分页起始位置
            int fromIndex = 0;
            //需要截取的位置
            int toIndex = 0;
            //如果当前页数小于0则置为1
            currentPage = (currentPage <= 0) ? 1 : currentPage;
            //如果当前页数大于最大页数则置为最大页数
            currentPage = (currentPage > pageCount) ? pageCount : currentPage;
            //计算分页起始位置
            fromIndex = (currentPage - 1) * size;
            if (currentPage == pageCount) {
                toIndex = list.size();
                //计算需要截取的位置
            } else {
                toIndex = currentPage * size;
            }
            pageInfo.setTotal(totalCount);
            //放入分页结果
            pageInfo.setRecords(list.subList(fromIndex, toIndex));
        }
        return pageInfo;
    }

}

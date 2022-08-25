package com.jxzy.AppMigration.NavigationApp.util;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * 封装分页数据（记录数和所有记录）
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: PageDataResult
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/5/12 14:54
 * @Version: 1.0
 */
@Data
public class PageDataResult {

    private Integer code=200;

    //总记录数量
    private Integer totals;

    private List<?> list;

    private Object data;

    private Object dataNew;

    private Integer count;

    private String realIncome;

    private String paymentTotalAccount;

}

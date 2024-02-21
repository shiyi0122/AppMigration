package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.APIReturnResult;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrder;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderY;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/24 17:09
 */
public interface BusinessOrderService {
    int addBusinessOrderY(BusinessOrderY businessOrderY);

    BusinessOrderY getBusinessOrderYId(Map<String, Object> search);

    PageDataResult getBusinessOrderYByUserIdList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney);


    BusinessOrderY getTransactLogsById(String orderNumber);

}

package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.APIReturnResult;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessTransactLogs;

import java.util.Map;

/**
 *  @Description: 交易记录接口实现类
 *  @Date: 2020-01-09 19:25
 *  @version: 1.0
 *  @Copyright: 2019 http://www.fenghuaapp.com/ Inc. All rights reserved.
 *  注意：本内容仅限于风华正茂科技(北京)有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public interface TransactLogsService {
    /***
     * 根据id查询交易记录表
     * @param id
     * @return
     */
    BusinessTransactLogs getTransactLogsById(String id);

    /***
     * 微信或者支付宝的业务处理
     * @param out_trade_no
     * @param objectId
     * @param type
     * @param payMoney
     * @return
     */
    APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney)throws Exception;

    /***
     * 根据手机号查询是否支付过保证金
     * @param phone
     * @return
     */
    Integer getTransactLogsCountByPhone(String phone);

    /***
     * 判断用户是否支付保证金
     * @param search
     * @return
     */
    Integer getTransactLogsCountBySearch(Map<String, String> search);
}

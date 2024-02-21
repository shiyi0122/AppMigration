package com.jxzy.AppMigration.NavigationApp.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import com.jxzy.AppMigration.NavigationApp.Service.BusinessOrderService;
import com.jxzy.AppMigration.NavigationApp.Service.RobotLiveDeviceService;
import com.jxzy.AppMigration.NavigationApp.Service.TransactLogsService;
import com.jxzy.AppMigration.NavigationApp.Service.WechatDepositService;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.exception.CommonException;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.JsonUtils;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.jxzy.AppMigration.NavigationApp.util.XMLUtil;
import com.jxzy.AppMigration.NavigationApp.util.wxpay.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @Description:支付回调
 *  @Date: 2020-01-09 18:16
 *  @version: 1.0
 *  @Copyright: 2019 http://www.fenghuaapp.com/ Inc. All rights reserved.
 *  注意：本内容仅限于风华正茂科技(北京)有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@RestController
@RequestMapping("pay_call")
@Slf4j
@Validated
public class PayCallBackController {

    @Value("${wxpay.apiSecretkey}")
    private String wxApiSecretkey; //微信api秘钥
    @Value("${wxpay.appid}")
    private String wxPcAppid;//pc端的appId
    @Value("${wxpay.mchid}")
    private String wxMchid;// 微信商户号

    //支付宝相关参数
    @Value("${alipay.partner}")
    private String alipayPartner; //合作身份者ID，签约账号
//
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey; //合作伙伴密钥中的RSA密钥，支付宝开发者中心中->PID和公钥管理->合作伙伴密钥，不是开发者公钥，是支付宝公钥
//    @Autowired
//    private AlipayProperties aliPayProperties;

    @Autowired
    private TransactLogsService transactLogsService;
    @Autowired
    private BusinessOrderService businessOrderService;
    @Autowired
    private RobotLiveDeviceService robotLiveDeviceService;
    @Autowired
    private WechatDepositService wechatDepositService;

    //用于储存在队列中的订单,防止重复提交
    Map<String, String> cacheMap = new ConcurrentHashMap<>();


    /***
     * 检查支付是否成功
     * @param orderNumber
     * @return
     */
    @PostMapping("check_order")
    public ReturnModel checkOrderSuccessfulPayment(@NotBlank(message = "订单编号不能为空") String orderNumber)throws Exception {
        ReturnModel returnModel = new ReturnModel();
        BusinessOrderY logs = businessOrderService.getTransactLogsById(orderNumber);
        if(logs==null){ throw new CommonException("订单不存在");
        }
        if("1".equals(logs.getOrderStatus())){//订单已经完成
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("支付完成");

            return returnModel;
        }
        returnModel.setState(Constant.STATE_FAILURE);
        returnModel.setMsg("支付失败");
        return returnModel;
    }

    /***
     * 微信支付回调
     * @param request
     * @param response
     * @return
     * @throws NumberFormatException
     * @throws Exception
     */
    @RequestMapping(value = "wx_pay")
    public ReturnModel weixinPay(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception {
        ReturnModel returnModel = new ReturnModel();
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        String result_code;
        String return_code;
        String out_trade_no;
        String attach;
        map = XMLUtil.doXMLParse(xmlString);
        result_code = map.get("result_code");
        out_trade_no = map.get("out_trade_no");
        return_code = map.get("return_code");
        attach = map.get("attach");
        System.out.println("out_trade_no"+out_trade_no);
        System.out.println("return_code"+return_code);
        System.out.println("attach"+attach);
        String resXml = "";
        if (checkSign(xmlString) && !"".equals(attach) && attach != null) {
            System.out.println("***********验证通过，进入业务处理*********************"+cacheMap.get(out_trade_no));
            synchronized (out_trade_no) {
                if(cacheMap.get(out_trade_no)==null){
                    Map<String, Object> data = JsonUtils.toHashMap(attach);
                    cacheMap.put(out_trade_no, out_trade_no);
                    String objectId = data.get("objectId").toString();
                    String type = data.get("type").toString();
                    String payMoney = data.get("payMoney").toString();
                    System.out.println("objectId"+objectId+"支付用户id:"+out_trade_no);
                    System.out.println("支付类型type:"+type+"payMoney"+payMoney);

                    if(StringUtils.isEmpty(type) || StringUtils.isEmpty(payMoney)){
                        returnModel.setState(Constant.STATE_FAILURE);
                        returnModel.setMsg("微信参数不全");
                        return returnModel;
                    }
                    System.out.println("****支付成功进入执行方法****");
                    APIReturnResult result = verificationAmount(type, payMoney, out_trade_no);
                    System.out.println("处理数据返回值======"+ JSONUtils.valueToString(result));
                    if (result != null){
                        System.out.println("verificationAmount处理数据错误信息======"+result.getMsg());
                        returnModel.setMsg(result.getMsg());
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }

                    //判断是游娱go商品订单还是直播订单，直播订单须切换使用另一个数据库进行处理
                    if ("Y".equals(out_trade_no.substring(0,1))){
                        if ("YJ".equals(out_trade_no.substring(0,2))){//机器人押金订单回调逻辑

                            APIReturnResult x = wechatDepositService.aliPayAndWeiXinOperation(out_trade_no,objectId, type, payMoney);
                            System.out.println("处理成功数据返回值======"+ JSONUtils.valueToString(x));
                            if (x != null){
                                System.out.println("aliPayAndWeiXinOperation失败信息======"+x.getMsg());
                                returnModel.setMsg(x.getMsg());
                                returnModel.setState(Constant.STATE_FAILURE);
                                return returnModel;
                            }
                            cacheMap.remove(out_trade_no);
                            //这里 根据实际业务场景 做相应的操作
                            // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                            out.write(resXml.getBytes());
                            out.flush();
                            out.close();
                            returnModel.setData(result_code);
                            returnModel.setMsg("交易成功");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;

                        }else{//游娱go商品订单回调逻辑

                            APIReturnResult x = businessOrderService.aliPayAndWeiXinOperation(out_trade_no,objectId, type, payMoney);
                            System.out.println("处理成功数据返回值======"+ JSONUtils.valueToString(x));
                            if (x != null){
                                System.out.println("aliPayAndWeiXinOperation失败信息======"+x.getMsg());
                                returnModel.setMsg(x.getMsg());
                                returnModel.setState(Constant.STATE_FAILURE);
                                return returnModel;
                            }
                            cacheMap.remove(out_trade_no);
                            //这里 根据实际业务场景 做相应的操作
                            // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                            out.write(resXml.getBytes());
                            out.flush();
                            out.close();
                            returnModel.setData(result_code);
                            returnModel.setMsg("交易成功");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;

                        }

                    }else{//直播订单回调逻辑

                        APIReturnResult x = robotLiveDeviceService.aliPayAndWeiXinOperation(out_trade_no,objectId, type, payMoney);
                        System.out.println("处理成功数据返回值======"+ JSONUtils.valueToString(x));
                        if (x != null){
                            System.out.println("aliPayAndWeiXinOperation失败信息======"+x.getMsg());
                            returnModel.setMsg(x.getMsg());
                            returnModel.setState(Constant.STATE_FAILURE);
                            return returnModel;
                        }
                        cacheMap.remove(out_trade_no);
                        //这里 根据实际业务场景 做相应的操作
                        // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                        out.write(resXml.getBytes());
                        out.flush();
                        out.close();
                        returnModel.setData(result_code);
                        returnModel.setMsg("交易成功");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }
                }
                System.out.println("***********消息队列存在订单****************"+out_trade_no);
            }
            returnModel.setData(result_code);
            returnModel.setMsg("交易失败");
            returnModel.setState("500");
            return returnModel;
        } else {
            log.info("支付失败,错误信息：{}",result_code);
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
            returnModel.setData(result_code);
            returnModel.setMsg("交易失败");
            returnModel.setState("500");
            return returnModel;
        }
    }
//    /***
//     * 支付宝回调
//     * @param request
//     * @return
//     * @throws AlipayApiException
//     * @throws UnsupportedEncodingException
//     */
//    @RequestMapping("/alipay")
//    public String notify(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
//        // 一定要验签，防止黑客篡改参数
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        StringBuilder notifyBuild = new StringBuilder("/****************************** 进入支付回调方法 ******************************/\n");
//        StringBuilder finalNotifyBuild = notifyBuild;
//        parameterMap.forEach((key, value) -> finalNotifyBuild.append(key + "=" + value[0] + "\n") );
//        log.info(notifyBuild.toString());
//        String message = "";
//        boolean flag = this.aliRsaCheck(request);
//        if (flag) {
//            System.out.println("/****************************** 支付宝延签通过 ******************************/\n");
//            //交易状态
//            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
//            if("WAIT_BUYER_PAY".equals(tradeStatus)){//交易创建，等待买家付款
//                System.out.println("暂时不做处理****");
//                return "failed";
//            }
//            //订单编号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//            //付款金额
//            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
//            // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
//            // TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
//            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
//                System.out.println("/****************************** 支付宝支付完成进入业务处理 ******************************/\n");
//                APIReturnResult result = null;
//                APIReturnResult x = null;
//                try {
//                    result = verificationAmount("", total_amount, out_trade_no);
//                    if(result == null) {//校验金额成功的情况进入业务处理
//                        System.out.println("/****************************** 支付宝支付金额校验成功 ******************************/\n");
//                        x = businessOrderService.aliPayAndWeiXinOperation(out_trade_no, "", "", total_amount);
//                    }else{
//                        System.out.println("/****************************** 支付宝支付金额校验失败： "+result.getMsg()+"*****************************/\n");
//                        return "failed";
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (x != null) {
//                    System.out.println("/****************************** 支付宝业务处理异常： *"+x.getMsg()+"*****************************/\n"+x.getMsg());
//                    message =  "failed";
//                }else {
//                    message = "success";
//                }
//            }else{
//                message =  "failed";
//            }
//            return message;
//        }
//        System.out.println("/****************************** 支付宝验证失败 ******************************/"+flag);
//        return message;
//    }

    /***
     * 校验 金额
     * @param type
     * @param payMoney 支付金额
     * @param tradeNo 订单号
     * @return
     * @throws Exception
     */
    private APIReturnResult verificationAmount(String type, String payMoney, String tradeNo) throws Exception {
        System.out.println("************进入校验金额**************");

        if ("Y".equals(tradeNo.substring(0,1))){
            if ("YJ".equals(tradeNo.substring(0,2))){//机器人押金订单

                WechatDeposit log = wechatDepositService.getTransactLogsById(tradeNo);//查询预支付订单
                if(log == null){
                    return APIReturnResult.error("订单不存在或没有找到,订单号:"+tradeNo);
                }
                if("30".equals(log.getDepositState()) ){
                    return APIReturnResult.error("订单已经支付，请勿重复支付");
                }
//        System.out.println("*******type="+type+"==============tradeNo="+tradeNo+"------log="+log.getObjectId());

                if(new BigDecimal(log.getDepositMoney()).compareTo(new BigDecimal(payMoney)) != 0){
                    return APIReturnResult.error("支付的金额不正确");
                }
                System.out.println("************校验金额成功出来了**************");
                return null;

            }else{//游娱goApp中的订单
                BusinessOrderY log = businessOrderService.getTransactLogsById(tradeNo);//查询预支付订单
                if(log == null){
                    return APIReturnResult.error("订单不存在或没有找到,订单号:"+tradeNo);
                }
                if("1".equals(log.getOrderStatus()) ){
                    return APIReturnResult.error("订单已经支付，请勿重复支付");
                }
//        System.out.println("*******type="+type+"==============tradeNo="+tradeNo+"------log="+log.getObjectId());

                if(new BigDecimal(log.getPaymentAmount()).compareTo(new BigDecimal(payMoney)) != 0){
                    return APIReturnResult.error("支付的金额不正确");
                }
                System.out.println("************校验金额成功出来了**************");
                return null;
            }
        }else{//直播订单

            BusinessTransactLogs log = transactLogsService.getTransactLogsById(tradeNo);//查询预支付订单
            if(log == null){
                return APIReturnResult.error("订单不存在或没有找到,订单号:"+tradeNo);
            }
            if(log.getStatus() == 1){
                return APIReturnResult.error("订单已经支付，请勿重复支付");
            }
            System.out.println("*******type="+type+"==============tradeNo="+tradeNo+"------log="+log.getObjectId());

            if(log.getPrice().compareTo(new BigDecimal(payMoney)) != 0){
                return APIReturnResult.error("支付的金额不正确");
            }
            System.out.println("************校验金额成功出来了**************");
            return null;

        }


    }

    /***
     * 校验签名是否正确
     * @param xmlString
     * @return
     */
    private boolean checkSign(String xmlString)throws Exception {
        System.out.println("=================进入校验签名==================");
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(xmlString);  // 转换成map
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        for (Map.Entry<String, String> stringStringEntry : notifyMap.entrySet()) {
            signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        String sing = WXPayUtil.generateSignature(notifyMap, wxApiSecretkey);
        if(WXPayUtil.isSignatureValid(signParams, wxApiSecretkey)){
            System.out.println("校验签名成功");
            return true;
        }
        System.out.println("服务器回包里面的签名是:" + notifyMap.get("sign").toString()+"===代码生成得签名是："+sing);
        System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
        System.out.println("=================进入校验失败==================");
        return false;
    }

    /***
     * 支付宝验证签名
     * @param request
     * @return
     */
//    public boolean aliRsaCheck(HttpServletRequest request){
//        // https://docs.open.alipay.com/54/106370
//        // 获取支付宝POST过来反馈信息
//        Map<String, String> params = new HashMap<>();
//        Map requestParams = request.getParameterMap();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            params.put(name, valueStr);
//        }
//        System.out.println("---验证签名参数---"+params.toString());
//        try {
//            boolean verifyResult = AlipaySignature.rsaCheckV1(params,
//                    aliPayProperties.getAlipayPublicKey(),
//                    aliPayProperties.getCharset(),
//                    aliPayProperties.getSignType());
//
//            return verifyResult;
//        } catch (AlipayApiException e) {
//            log.debug("verify sigin error, exception is:{}", e);
//            return false;
//        }
//    }
}

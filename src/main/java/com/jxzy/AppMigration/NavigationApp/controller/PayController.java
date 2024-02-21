package com.jxzy.AppMigration.NavigationApp.controller;


import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.exception.CommonException;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.NavigationApp.util.jwt.JwtUtils;
import com.jxzy.AppMigration.NavigationApp.util.wxpay.MyConfig;
import com.jxzy.AppMigration.NavigationApp.util.wxpay.WXPay;
import com.jxzy.AppMigration.NavigationApp.util.wxpay.WXPayConstants;
import com.jxzy.AppMigration.NavigationApp.util.wxpay.WXPayUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableServer.ID_UNIQUENESS_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.*;

/**
 *  @Description: 支付生成预支付订单记录
 *  @Date: 2020-01-10 9:45
 *  @version: 1.0
 *  @Copyright: 2019 http://www.fenghuaapp.com/ Inc. All rights reserved.
 *  注意：本内容仅限于风华正茂科技(北京)有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@Api(tags = "支付相关")
@RestController
@RequestMapping("pay")
@Slf4j
@Validated
public class PayController {

    @Value("${wxpay.apiSecretkey}")
    private String apiSecretkey; //微信api秘钥
//    @Value("${custom.appid}")
//    private String wxAppid;//小程序得appid
//    @Value("${custom.secret}")
//    private String wxSecret;//小程序的密钥
    @Value("${wxpay.appid}")
    private String wxPcAppid;//pc端的appId
    @Value("${wxpay.mchid}")
    private String wxMchid;// 微信商户号

    @Value("${wxpay.deposit_weChat_mch_id}")
    private String depositWeChatMchId;// 押金支付微信商户号
    @Value("${wxpay.notify_url}")
    private String wx_notify_url;//微信回调地址
    @Value("${alipay.partner}")
    private String alipayPartner;//支付宝
    @Value("${custom.business-name}")
    private String businessName;//订单平台名称


//    @Autowired
//    private AlipayProperties aliPayProperties;

    @Autowired
    private BusinessOrderService businessOrderService;

    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;
    @Autowired
    private BusinessTransactLogsService businessTransactLogsService;

    @Autowired
    private SysCurrenUserService sysCurrenUserService;

    @Autowired
    private WechatDepositService wechatDepositService;

    @Autowired
    private SysScenicSpotService sysScenicSpotService;

//    @Autowired
//    private AlipayTradeService alipayTradeService;

    @Autowired
    private BaseService baseService;

    @RequestMapping("test")
    public APIReturnResult Test(String unionId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> payMap = new HashMap<>();
        payMap.put("orderTitle","注册保证金");
        payMap.put("orderNumber", IDBuilder.getOrderNumber("13722320853"));
        payMap.put("type","0");
        payMap.put("payType","1");
        payMap.put("payMoney","0.01");
        payMap.put("objectId","");
        payMap.put("unionId",unionId);
       return null;
//       return w(payMap);
    }

    /***
     * 支付生成预支付订单
     * @param token 登录标识 0的时候没有
     * @param params 加密的字符串
     * @param agentType 1代表小程序2app
     * @return
     * @throws Exception
     */
    @ApiOperation("支付生成预支付订单")
    @PostMapping("generate_order_live_broadcast")
    public APIReturnResult generate_order_live_broadcast(String token, String agentType,String orderNumber,String liveId ,@NotNull(message = "参数不能为空") String params, HttpServletRequest request, HttpServletResponse response)throws Exception {
        System.out.println(params);
        Map<String, String> search = new HashMap<>();
        params = AES.decode(params);
        System.out.println(params);
        Map<String, Object> paramMap= JsonUtils.parseJSON2Map(params);
        Long autionId = paramMap.get("autionId")==null?null: Long.valueOf(paramMap.get("autionId").toString());
        String phone = paramMap.get("phone")==null?"":paramMap.get("phone").toString();
        String scenicSpotId = paramMap.get("scenicSpotId")==null?"":paramMap.get("scenicSpotId").toString();

        search.put("phone",phone);
//        BusinessUsers user = businessUsersService.getBusinessUserBySearch(search);
        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersService.selectPhoneByUser(phone);
        String unionId = sysGuideAppUsers.getWeChatId();//小程序支付需要
        BigDecimal payMoney = paramMap.get("payMoney")==null?null:new BigDecimal(paramMap.get("payMoney").toString());//支付金额
        String type = paramMap.get("type")==null?null:paramMap.get("type").toString();//  1门票，2商品,4直播
        Integer payType = paramMap.get("payType")==null?null: Integer.valueOf(paramMap.get("payType").toString());// 0 微信 1支付宝
        if(payMoney == null){throw new CommonException("支付金额不能为空");}
        if(StringUtils.isEmpty(type)){throw new CommonException("订单类型不能为空");}
        if(payType == null){throw new CommonException("支付类型不能为空");}
        if(payType !=0 && payType !=1 ){throw new CommonException("支付类型不正确");}
        Map<String, Object> payMap = new HashMap<>();
        Long userId = null;
        if(StringUtils.isNotEmpty(token)){
            userId = JwtUtils.getUserIdByToken(token);//获取用户信息
        }
        String orderTitle = "";//订单标题
//        String orderNumber = "";//订单编号
        if("4".equals(type)){//直播时长购买支付
            if(StringUtils.isEmpty(phone)){throw new CommonException("手机号不能为空");}
            orderTitle = "购买观看直播时长";
            orderNumber = saveTransactLogs(type,payType,payMoney,null,phone,null,liveId);
            if("1".equals(agentType) && StringUtils.isEmpty(unionId)) {//小程序 支付
                throw new CommonException("微信unionId不能为空");
            }
        }else if ("5".equals(type)){//机器人支付押金

           SysCurrenUser sysCurrenUser = sysCurrenUserService.selectPhoneByUser(phone);
            if (org.springframework.util.StringUtils.isEmpty(sysCurrenUser)){
                //小程序没有注册用户，游娱go无法支付押金，支付后有问题，后续完善逻辑后在开发
                new APIReturnResult("400","失败","未找到小程序用户,无法提交!",null);
            }else{
                SysScenicSpot sysScenicSpot =  sysScenicSpotService.selectSpotIdByDetail(scenicSpotId);
                if (org.springframework.util.StringUtils.isEmpty(sysScenicSpot)){
                    new APIReturnResult("400","失败","未查询到景区押金,无法提交!",null);
                }else{
                    String scenicSpotDeposit = sysScenicSpot.getScenicSpotDeposit();
                    payMoney = new BigDecimal(scenicSpotDeposit);
                    orderNumber = saveWechatDeposit(payMoney,sysCurrenUser.getCurrentUserId(),phone,scenicSpotId);
                }
            }
        }
        payMap.put("orderTitle",orderTitle);
        payMap.put("orderNumber",orderNumber);
        payMap.put("type",type);
        payMap.put("payType",payType);
        payMap.put("payMoney",payMoney);
        payMap.put("objectId",autionId);
        payMap.put("unionId",unionId);
        if(payType == 0){//调起微信支付
            return wechatPayment(payMap,request);//pc端微信支付
        }
        return wechatPayment( payMap, request);//pc端微信支付
//        return  aliPayment(payMap);//支付宝支付
    }

    /***
     * 小程序微信支付预下单
     * @param payMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
//    public APIReturnResult wxProceduresPay(Map<String, Object> payMap, HttpServletRequest request, HttpServletResponse response)throws Exception {
//        BigDecimal realPrice = new BigDecimal(payMap.get("payMoney").toString());
//        realPrice = realPrice.multiply(new BigDecimal("100"));//微信金额要乘100
//        MyConfig config = new MyConfig();
//        config.setAppid(wxAppid);
////        config.setAppid(wxAppidNew);
//        config.setApiSecretkey(apiSecretkey);
//        config.setMchid(wxMchid);
//        WXPay wxpay = new WXPay(config);
//        SortedMap<String, String> data = new TreeMap<String, String>();
//        data.put("body", businessName+"-"+payMap.get("orderTitle").toString());
//        data.put("openid", payMap.get("unionId").toString());
//        data.put("out_trade_no", payMap.get("orderNumber").toString());
//        data.put("fee_type", "CNY");
//        data.put("total_fee", String.valueOf(realPrice.intValue()));
//        String spbill_create_ip = IpgetUtil.getIp(request);
//        data.put("spbill_create_ip", spbill_create_ip);
//        data.put("notify_url", wx_notify_url);
//        data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
//        Map<String, String> attachMap = new HashMap<>();
//        attachMap.put("objectId",payMap.get("objectId")==null?"":payMap.get("objectId").toString());
//        attachMap.put("payMoney",payMap.get("payMoney").toString());
//        attachMap.put("type",payMap.get("type").toString());
//        String attach = JsonUtils.toJSONString(attachMap);
//        data.put("attach", attach);//附加数据
//        data.put("sign_type","HMAC-SHA256");
//        String sign = WXPayUtil.generateSignature(data, apiSecretkey);
//        System.out.println("生成签名成功==="+sign);
//        data.put("sign",sign);
//        Map<String, String> resp = wxpay.unifiedOrder(data);
//        System.out.println(resp);
//        if("SUCCESS".equals(resp.get("return_code"))){//获取成功 url返回前端
//            Map<String, String> dataMap = new HashMap<>();
//            dataMap.put("nonceStr",resp.get("nonce_str"));
//            dataMap.put("appId",wxAppid);
////            dataMap.put("appId",wxAppidNew);
//            dataMap.put("signType","HMAC-SHA256");
//            dataMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
//            dataMap.put("package","prepay_id="+resp.get("prepay_id"));
//            System.out.println("----------小程序数据加密-----------"+dataMap.toString());
//            String paySign = WXPayUtil.generateSignature(dataMap, apiSecretkey);
//            System.out.println("----------小程序数据paySign-----------"+paySign);
//            dataMap.put("paySign",paySign);
//            return APIReturnResult.ok("获取成功",dataMap);
//        }
//        return APIReturnResult.error(resp.get("return_msg"),resp);
//    }

//    /***
//     * 支付宝预支付订单
//     * @param payMap
//     * @return
//     */
//    public APIReturnResult aliPayment(Map<String, Object> payMap){
//        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
//        String outTradeNo = payMap.get("orderNumber").toString();
//        // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
//        String subject = businessName+"-"+payMap.get("orderTitle").toString();
//        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
//        String body = subject;
//
//        // (必填) 订单总金额，单位为元，不能超过1亿元
//        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
//        String totalAmount = payMap.get("payMoney").toString();
//
//        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
//        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
//        String sellerId = "";
//
//        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
//        String storeId = alipayPartner;
//        // 支付超时，线下扫码交易定义为5分钟
//        String timeoutExpress = "5m";
//        AlipayTradePrecreateRequestBuilder builder =new AlipayTradePrecreateRequestBuilder()
//                .setSubject(subject)
//                .setTotalAmount(totalAmount)
//                .setOutTradeNo(outTradeNo)
//                .setSellerId(sellerId)
//                .setBody(body)
//                .setStoreId(storeId)
//                .setTimeoutExpress(timeoutExpress)
//                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
//                .setNotifyUrl(aliPayProperties.getNotifyUrl());
//        AlipayF2FPrecreateResult result = alipayTradeService.tradePrecreate(builder);
//        String message = "";
//        switch (result.getTradeStatus()) {
//            case SUCCESS:
//                log.info("支付宝预下单成功: )");
//                AlipayTradePrecreateResponse res = result.getResponse();
//                Map<String, String> dataMap = new HashMap<>();
//                dataMap.put("code_url",res.getQrCode());
//                dataMap.put("orderNumber",payMap.get("orderNumber").toString());
//                return APIReturnResult.ok("获取成功",dataMap);
//            case FAILED:
//                message = "支付宝预下单失败!!!";
//                log.error(message);
//                break;
//
//            case UNKNOWN:
//                message = "系统异常，预下单状态未知!!!";
//                log.error(message);
//                break;
//
//            default:
//                message = "不支持的交易状态，交易返回异常!!!";
//                log.error(message);
//                break;
//        }
//        return APIReturnResult.error(message);
//    }

    /***
     * 调起微信支付
     * @param payMap
     * @param request
     * @return
     * @throws Exception
     */
    public APIReturnResult wechatPayment(Map<String, Object> payMap, HttpServletRequest request)throws Exception {
//        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(apiSecretkey.getBytes("utf-8")));

        BigDecimal realPrice = new BigDecimal(payMap.get("payMoney").toString());
        realPrice = realPrice.multiply(new BigDecimal("100"));//微信金额要乘100
        MyConfig config = new MyConfig();
        config.setAppid(wxPcAppid);
        config.setApiSecretkey(apiSecretkey);
        if ("5".equals(payMap.get("type").toString())){
            config.setMchid(depositWeChatMchId);
        }else{
            config.setMchid(wxMchid);
        }
        WXPay wxpay = new WXPay(config);
        SortedMap<String, String> data = new TreeMap<String, String>();
        data.put("body", businessName+"-"+payMap.get("orderTitle").toString());
        data.put("out_trade_no", payMap.get("orderNumber").toString());
        data.put("fee_type", "CNY");
        data.put("total_fee", String.valueOf(realPrice.intValue()));
        String spbill_create_ip = IpgetUtil.getIp(request);
        data.put("spbill_create_ip", spbill_create_ip);
        data.put("notify_url", wx_notify_url);//微信支付回调
        data.put("trade_type", "APP");  // 此处指定为APP支付
        Map<String, String> attachMap = new HashMap<>();
        attachMap.put("objectId",payMap.get("objectId")==null?"":payMap.get("objectId").toString());
        attachMap.put("payMoney",payMap.get("payMoney").toString());
        attachMap.put("type",payMap.get("type").toString());
        String attach = JsonUtils.toJSONString(attachMap);
        data.put("attach", attach);//附加数据
        data.put("sign_type", "HMACSHA256");
//        String sign = WXPayUtil.generateSignatureMd5(data, apiSecretkey);
        String sign = WXPayUtil.generateSignature(data,apiSecretkey, WXPayConstants.SignType.HMACSHA256);
        System.out.println("生成签名成功==="+sign);
        data.put("sign",sign);
        Map<String, String> resp = wxpay.unifiedOrder(data);//统一下单
        System.out.println("生成微信支付二维码成功==="+resp);
        if("SUCCESS".equals(resp.get("return_code"))){//获取成功 url返回前端
            String type = payMap.get("type").toString();
            if ("5".equals(type)){//如果是押金支付，需要修改下押金订单，补充下押金订单数据
                WechatDeposit wechatDeposit = wechatDepositService.getTransactLogsById(payMap.get("orderNumber").toString());
                wechatDeposit.setRequestNonceStr(DateUtil.DateTimeKey());
                wechatDeposit.setReturnNonceStr(DateUtil.DateTimeKey());
                wechatDeposit.setSpbillCreateIp(spbill_create_ip);
//                wechatDeposit.setRequestSign(resp.get("sign"));
//                wechatDeposit.setReturnSign(resp.get("sign"));
                wechatDeposit.setReturnPrepayId(resp.get("prepay_id"));
                wechatDeposit.setUpdateDate(DateUtil.currentDateTime());
                wechatDepositService.updateWechatDeposit(wechatDeposit);
            }
            SortedMap<String, String> dataMap = new TreeMap<String, String>();
            //随机字符串
            String nonce =  UUID.randomUUID().toString().replaceAll("-", "");
            //随机时间戳
            String timestamp = String.valueOf(System.currentTimeMillis()/1000);
            dataMap.put("appid",wxPcAppid);
            dataMap.put("noncestr",nonce);
            dataMap.put("prepayid",resp.get("prepay_id"));
            dataMap.put("partnerid",resp.get("mch_id"));
            dataMap.put("package","Sign=WXPay");
            dataMap.put("timestamp",timestamp);
            sign = new String();
            sign = WXPayUtil.generateSignature(dataMap, apiSecretkey);
            System.out.println(sign);
            dataMap.put("sign",sign);
//            boolean signatureValid = WXPayUtil.isSignatureValid(dataMap, apiSecretkey, WXPayConstants.SignType.MD5);
//            dataMap.put("code_url",resp.get("code_url"));
//            dataMap.put("orderNumber",payMap.get("orderNumber").toString());
            return APIReturnResult.ok("获取成功",dataMap);
        }
        return APIReturnResult.error(resp.get("return_msg"),resp);
    }


    /***
     * 保存消费记录表
     * @param type 0注册保证金 1竞拍保证金 2限时购保证金,4直播观看时长
     * @param payType 0 微信 1支付宝
     * @param userId 1竞拍保证金 2限时购保证金 记录用户id
     * @param phone 注册保证金 时候用手机号标识
     * @param auctionId 1竞拍保证金 2限时购保证金 的商品id
     * @return
     */
    private String saveTransactLogs(String type, Integer payType, BigDecimal payMoney,Long userId, String phone, Long auctionId,String liveId) throws Exception{
//        String logId = IDBuilder.getOrderNumber(phone);
        String logId  =  IdUtils.getOrderNumber(phone);
        BusinessTransactLogs log = new BusinessTransactLogs();
        log.setId(logId);
        log.setPayType(payType);
        log.setType(Integer.parseInt(type));
        log.setPhone(phone);
        log.setUserId(userId);
        log.setObjectId(auctionId);
        log.setPrice(payMoney);
        log.setLiveId(liveId);
        String remarks = "";

        remarks = "购买直播观看时长";

        log.setRemarks(remarks);
        log.setCreateTime(DateUtil.date2String(new Date()));
        log.setUpdateTime(DateUtil.date2String(new Date()));
//        baseService.insertObj(log);
        businessTransactLogsService.insertSelective(log);
        return logId;
    }


    /**
     * 生成押金订单数据
     * @param
     * @param payMoney
     * @param userId
     * @param phone
     * @return
     * @throws Exception
     */
    private String saveWechatDeposit(BigDecimal payMoney,Long userId, String phone,String scenicSpotId) throws Exception{
        String outTradeNo = IdUtils.getRobotDepositOrderNumber(phone);
        WechatDeposit wechatDeposit = new WechatDeposit();
        wechatDeposit.setDepositId(IdUtils.getSeqId());
        wechatDeposit.setDepositUserId(userId);
        wechatDeposit.setDepositMoney(payMoney.toString());
        wechatDeposit.setDepositScenicSpotId(Long.parseLong(scenicSpotId));
        wechatDeposit.setDepositState("20");
        wechatDeposit.setOutTradeNo(outTradeNo);
        wechatDeposit.setRequestNonceStr(DateUtil.DateTimeKey());
        wechatDeposit.setReturnNonceStr(DateUtil.DateTimeKey());
        wechatDeposit.setCreateDate(DateUtil.currentDateTime());
        wechatDeposit.setUpdateDate(DateUtil.currentDateTime());

//        baseService.insertObj(log);
        wechatDepositService.insertSelective(wechatDeposit);
        return outTradeNo;
    }



}

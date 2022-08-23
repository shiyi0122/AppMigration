package com.jxzy.AppMigration.NavigationApp.controller;


import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotShopsService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "游小伴商品相关")
@RestController
@CrossOrigin
@RequestMapping("commodity")
public class commodityController extends PublicUtil {

    @Autowired
    private SysScenicSpotShopsService sysScenicSpotShopsService;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址

    /**
     * 查询景区商铺信息
     * @param: shopsId
     * @param: pageNum
     * @param: pageSize
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    @ApiOperation("查询景区商铺信息")
    @GetMapping("/queryScenicShopsList")
    public ReturnModel queryScenicShopsList(@ApiParam(name="shopsId",value="商铺ID",required=true)String shopsId,
                                            @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                            @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
                search.put("shopsId",shopsId);
                List<SysScenicSpotShops> shops = sysScenicSpotShopsService.queryScenicShopsList(pageNum,pageSize,search);
                for (int i = 0; i < shops.size(); i++) {
                    String shopsPic = shops.get(i).getShopsPic();//商铺图片
                    if (shopsPic != null && !"".equals(shopsPic)) {
                        shops.get(i).setShopsPic(DOMAIN_NAME+shopsPic);
                    }
                    for (int j = 0; j < shops.get(i).getSysScenicSpotShopsType().size(); j++) {
                        String productPicUrl = shops.get(i).getSysScenicSpotShopsType().get(i).getSysScenicSpotShopsDetails().get(i).getProductPicUrl();//商品详情图片
                        for (int k = 0; k < shops.get(i).getSysScenicSpotShopsType().get(i).getSysScenicSpotShopsDetails().size(); k++) {
                            if (productPicUrl != null && !"".equals(productPicUrl)) {
                                shops.get(i).getSysScenicSpotShopsType().get(j).getSysScenicSpotShopsDetails().get(k).setProductPicUrl(DOMAIN_NAME+productPicUrl);
                            }
                        }
                    }
                }
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(shops);
                returnModel.setData(pageInfo);
                returnModel.setMsg("成功获取商品列表！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
        }catch (Exception e){
            logger.info("queryScenicShopsList",e);
            returnModel.setData("");
            returnModel.setMsg("获取商品列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}
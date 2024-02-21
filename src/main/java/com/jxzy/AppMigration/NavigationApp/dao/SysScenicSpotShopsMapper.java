package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotShopsExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotShopsMapper {
    int deleteByPrimaryKey(Long shopsId);

    int insert(SysScenicSpotShops record);

    int insertSelective(SysScenicSpotShops record);

    SysScenicSpotShops selectByPrimaryKey(Long shopsId);

    int updateByPrimaryKeySelective(SysScenicSpotShops record);

    int updateByPrimaryKey(SysScenicSpotShops record);

    /**
     * 查询商品店铺详情
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    List<SysScenicSpotShops> queryScenicShopsList(Map<String, Object> search);

    /**
     * 查询景区中的商店
     * 张
     * @param search
     * @return
     */
    List<SysScenicSpotShops> searchScenicShopsList(Map<String, Object> search);

    /**
     * 后台管理——店铺列表
     * @param search
     * @return
     */
    List<SysScenicSpotShops> getSotShopsList(Map<String, Object> search);

    /**
     * 获取景区中全部的店铺信息
     * @param spotId
     * @return
     */
    List<SysScenicSpotShops> getScenicShopsList(String spotId);

    /**
     * 后台管理——店铺导出
     * @param search
     * @return
     */
    List<SysScenicSpotShopsExcel> uploadExcelShop(Map<String, Object> search);

    /**
     * 导入店铺添加
     * @param sysScenicSpotServiceResExcel
     * @return
     */
    int addImportShops(SysScenicSpotShopsExcel sysScenicSpotServiceResExcel);


}
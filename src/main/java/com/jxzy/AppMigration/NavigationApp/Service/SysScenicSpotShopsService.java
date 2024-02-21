package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotShopsExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsDetails;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotShopsService {
    /**
     * 查询商品店铺详情
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    List<SysScenicSpotShops> queryScenicShopsList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询最近的店铺信息
     * @param spotId
     * @param lng
     * @param lat
     * @return
     */
    SysScenicSpotShops getLatelyScewnicShops(String spotId, String lng, String lat);

    /**
     * 后台管理——店铺列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSpotShopsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 后台管理——添加店铺
     * @param sysScenicSpotShops
     * @return
     */
    int addSpotShops(SysScenicSpotShops sysScenicSpotShops);


    /**
     * 后台管理——修改店铺
     * @param sysScenicSpotShops
     * @return
     */

    int exitSpotShops(SysScenicSpotShops sysScenicSpotShops);

    /**
     * 后台管理——根据id删除店铺
     * @param shopsId
     * @return
     */
    int delSpotShops(Long shopsId);

    /**
     * 后台店铺管理——店铺上传图片
     * @param file
     * @param shopsId
     * @return
     */
    int addSpotShopsPicture(MultipartFile file, Long shopsId);


    /**
     * 后台店铺管理——店铺添加店铺详情
     * @param sysScenicSpotShopsDetails
     * @return
     */
    int addSpotShopsDetails(SysScenicSpotShopsDetails sysScenicSpotShopsDetails);


    /**
     * 后台管理——添加商品店铺详情
     * @param file
     * @param detailsId
     * @return
     */
    int addSpotShopsDetailsPicture(MultipartFile file, Long detailsId);

    /**
     * 后台管理——查询店铺详情
     * @param
     * @return
     */
    PageDataResult getSpotShopsDetails(Integer pageNum,Integer pageSize, Map<String, Object> search);

    /**
     * 后台管理——景区中全部店铺详情
     * @param spotId
     * @return
     */
    List<SysScenicSpotShops> getScenicShopsList(String spotId);

    /**
     * 商品管理——编辑商品详情
     * @param sysScenicSpotShopsDetails
     * @return
     */
    int editSpotShopsDetails(SysScenicSpotShopsDetails sysScenicSpotShopsDetails);

    /**
     * 后台管理——店铺的启用禁用
     * @param
     * @return
     */
    int editSpotShopsState(Long shopsId, String state);

    /**
     * 后台管理——店铺详情删除
     * @param detailsId
     * @return
     */
    int delSpotShopsDetails(Long detailsId);


    /**
     * 后台管理——店铺商品上下架
     * @param detailsId
     * @param state
     * @return
     */
    int editSpotShopsDetailsState(Long detailsId, String state);

    /**
     * 后台管理--店铺导出
     * @param search
     * @return
     */
    List<SysScenicSpotShopsExcel> uploadExcelShop(Map<String, Object> search);

    /**
     * 导入添加店铺
     * @param sysScenicSpotServiceResExcel
     * @return
     */
    int  addImportShops(SysScenicSpotShopsExcel sysScenicSpotServiceResExcel);



}

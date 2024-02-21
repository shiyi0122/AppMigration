package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotAdmissionFeeNoticeService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFeeNotice;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhang
 * @Date 2023/5/9 15:58
 */
@Api(tags = "门票须知/退还须知相关接口")
@RestController
@RequestMapping("scenicSpotAdmissionFeeNotice")
@CrossOrigin
public class SysScenicSpotAdmissionFeeNoticeController {

    @Autowired
    SysScenicSpotAdmissionFeeNoticeService sysScenicSpotAdmissionFeeNoticeService;







}

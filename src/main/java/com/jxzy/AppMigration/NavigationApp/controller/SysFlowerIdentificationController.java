package com.jxzy.AppMigration.NavigationApp.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jxzy.AppMigration.NavigationApp.Service.SysFlowerIdentificationService;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/19 16:39
 */

@Api(tags = "游娱go花草识别")
@CrossOrigin
@RestController
@RequestMapping("sysFlowerIdentification")
public class SysFlowerIdentificationController {

    @Autowired
    SysFlowerIdentificationService sysFlowerIdentificationService;

    @Autowired
    AdvancedGeneral advancedGeneral;

    @Autowired
    AuthService authService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    PicUtils picUtils;

    @ApiOperation("查询游娱go花草识别")
    @GetMapping("getSysFlowerIdentificationList")
    @ResponseBody
    public PageDataResult getSysFlowerIdentificationList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
        }

        pageDataResult = sysFlowerIdentificationService.getSysFlowerIdentificationList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }


    @ApiOperation("查询游娱go花草识别New")
    @PostMapping(value = "getSysFlowerIdentificationN")
    @ResponseBody
    public ReturnModel getSysFlowerIdentificationN(@RequestPart("file")MultipartFile file) {
        int j = 0;
        ReturnModel returnModel = new ReturnModel();
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        try{
            byte[] bytes = picUtils.compressPicForScale(file.getBytes(), 1024);
//            String accessToken= null;
//            boolean authserviceBAIDU = redisUtil.exists("authServiceBAIDU");
//            if (authserviceBAIDU){
//                accessToken = (String)redisUtil.get("authServiceBAIDU");
//            }else{
//                accessToken = AuthService.getAuth();
//                redisUtil.set("authServiceBAIDU",accessToken);
//            }
            String s = advancedGeneral.botanYadvancedGeneral(bytes);
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONArray result = jsonObject.getJSONArray("result");
            String js=JSONObject.toJSONString(result, SerializerFeature.WriteClassName);
            List<JSONObject> list = JSONObject.parseArray(js,JSONObject.class);
            for (JSONObject object : list) {
                map = new HashMap<>();
                String name =(String) object.get("name");
                if ("非植物".equals(name)){
                    j = 1;
                    continue;
                }
//                String root =(String) object.get("root");
//                if (!StringUtils.isEmpty(root)){
//                    Object score = object.get("score");
//                    String keyword =(String) object.get("keyword");
//                    Map baike_info =(Map) object.get("baike_info");
//                    String image_url = (String)baike_info.get("image_url");
//                    String description = (String)baike_info.get("description");
//                    map.put("score",score);
//                    map.put("imageUrl",image_url);
//                    map.put("description",description);
//                    map.put("root",root);
//                    map.put("keyword",keyword);
                Object score = object.get("score");
//                String keyword =(String) object.get("keyword");
//                String name =(String) object.get("name");
                Map baike_info =(Map) object.get("baike_info");
                String baikeUrl =  (String) baike_info.get("baike_url");
                String image_url = (String)baike_info.get("image_url");
                String description = (String)baike_info.get("description");
                map.put("score",score);
                map.put("imageUrl",image_url);
                map.put("description",description);
                map.put("baikeUrl",baikeUrl);
//                map.put("root",root);
                map.put("keyword",name);
                listMap.add(map);

            }


            if (j == 1) {
                byte[] bytesN = picUtils.compressPicForScale(file.getBytes(), 1024);
                String sN = advancedGeneral.advancedGeneral(bytesN);
                JSONObject jsonObjectN = JSONObject.parseObject(sN);
                JSONArray resultN = jsonObjectN.getJSONArray("result");
                String jsN = JSONObject.toJSONString(resultN, SerializerFeature.WriteClassName);
                List<JSONObject> listN = JSONObject.parseArray(jsN, JSONObject.class);
                for (JSONObject object : listN) {
                    map = new HashMap<>();
                    String name = (String) object.get("name");
                    String root = (String) object.get("root");
                    if (!StringUtils.isEmpty(root)) {
                        Object score = object.get("score");
                        String keyword = (String) object.get("keyword");
                        Map baike_info = (Map) object.get("baike_info");
                        String image_url = (String) baike_info.get("image_url");
                        String description = (String) baike_info.get("description");
                        String baikeUrl =  (String) baike_info.get("baike_url");
                        map.put("score", score);
                        map.put("imageUrl", image_url);
                        map.put("description", description);
                        map.put("root", root);
                        map.put("keyword", keyword);
                        map.put("baikeUrl",baikeUrl);
                        listMap.add(map);
                    }

                }
            }


        }catch (Exception e){
             e.printStackTrace();
        }
        returnModel.setData(listMap);
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }



}

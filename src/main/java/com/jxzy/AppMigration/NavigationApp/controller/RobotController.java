package com.jxzy.AppMigration.NavigationApp.controller;


import com.alibaba.fastjson.JSON;

import com.jxzy.AppMigration.NavigationApp.Service.SysRobotService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotWifiDataService;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotWifiData;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Api(tags = "机器人端接口")
@RestController//使用此注解无法返回jsp或者html，只用使用@Controller才能正常返回映射页面
@RequestMapping("robot")
public class RobotController extends PublicUtil {
    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysScenicSpotWifiDataService sysScenicSpotWifiDataService;


    @ApiOperation("查询机器人列表")
    @GetMapping("/queryRobotList")
    public ReturnModel queryCityAndScenicSpotLists(@ApiParam(name="robotCode",value="机器人编号",required=false)String robotCode,
                                                   @ApiParam(name="baseDTO",value="登录令牌",required=false)BaseDTO baseDTO){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            search.put("robotCode",robotCode);
           List<SysRobot> robot =  sysRobotService.queryRobotList(search);
            returnModel.setData(robot);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotLists",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("查询机器人列表")
    @PostMapping("/wifiData")
    public ReturnModel wifiData(HttpServletRequest request, HttpServletResponse response){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            int length = request.getContentLength();
            ServletInputStream iii = request.getInputStream();
            byte[] buffer = new byte[length];
            iii.read(buffer, 0, length);
            //String data = IOUtils.toString(buffer,"UTF-8");
            String json = IOUtils.toString(decompress(buffer),"UTF-8");
            json=json.replace("data=","");
            System.out.println(json);
            JSONObject jsonObject = JSONObject.fromObject(json);
            JSONArray features = jsonObject.getJSONArray("data");
            System.out.println("Data数据"+features);
            for (int i = 0; i < features.size(); i++) {
                JSONObject info = features.getJSONObject(i);// 获取features数组的第i个json对象
                System.out.println("JSON数据"+info);
                SysScenicSpotWifiData data = new SysScenicSpotWifiData();
                boolean mac1 = info.containsKey("mac");
                if (mac1 == true) {
                    String mac = info.getString("mac");
                    data.setMac(mac);
                    System.out.println("mac:"+mac);
                }else {
                    data.setMac("");
                }
                boolean one = info.containsKey("rssi");
                if (one == true) {
                    String rssi = info.getString("rssi");
                    data.setRssi(rssi);
                    System.out.println("rssi:"+rssi);
                }else {
                    data.setRssi("");
                }
                boolean one1 = info.containsKey("rssi1");
                if (one1 == true) {
                    String rssi1 = info.getString("rssi1");
                    data.setRssiOne(rssi1);
                    System.out.println("rssi1:"+rssi1);
                }else {
                    data.setRssiOne("");
                }
                boolean one2 = info.containsKey("rssi2");
                if (one2 == true) {
                    String rssi2 = info.getString("rssi2");
                    data.setRssiTwo(rssi2);
                    System.out.println("rssi2:"+rssi2);
                }else {
                    data.setRssiTwo("");
                }
                boolean one3 = info.containsKey("rssi3");
                if (one3 == true) {
                    String rssi3 = info.getString("rssi3");
                    data.setRssiThree(rssi3);
                    System.out.println("rssi3:"+rssi3);
                }else {
                    data.setRssiThree("");
                }
                boolean tmc1 = info.containsKey("tmc");
                if (tmc1 == true) {
                    String tmc = info.getString("tmc");
                    data.setTmc(tmc);
                    System.out.println("tmc:"+tmc);
                }else {
                    data.setTmc("");
                }
                boolean router1 = info.containsKey("router");
                if (router1 == true) {
                    String router = info.getString("router");
                    data.setRouter(router);
                    System.out.println("router:"+router);
                }else {
                    data.setRouter("");
                }
                boolean range1 = info.containsKey("range");
                if (range1 == true) {
                    String range = info.getString("range");
                    data.setRanges(range);
                    System.out.println("range:"+range);
                }else {
                    data.setRanges("");
                }
                int a = sysScenicSpotWifiDataService.wifiData(data);
                String jsonData = JSON.toJSONString(data);
                System.out.println("(KEY:)"+data.getMac()+"(JSON:)"+jsonData);
                //redisTemplate.opsForValue().append(data.getMac(),jsonData);
            }
        }catch (Exception e){
            logger.info("queryScenicSpotLists",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return null;
    }

    public byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    public static void main(String[] args) {
        String json ="data={\"id\":\"00f41465\",\"data\":[{\"mac\":\"80:ea:07:96:8f:22\",\"rssi\":\"-44\",\"rssi1\":\"-45\",\"rssi2\":\"-38\",\"rssi3\":\"-44\",\"tmc\":\"5a:72:c3:51:a2:70\",\"router\":\"topsroboteer2.4\",\"range\":\"1.6\"},{\"mac\":\"80:ea:07:a8:58:eb\",\"rssi\":\"-53\",\"rssi1\":\"-53\",\"rssi2\":\"-54\",\"rssi3\":\"-54\",\"tmc\":\"5a:72:c3:51:a2:70\",\"router\":\"topsroboteer2.4\",\"range\":\"3.5\"},{\"mac\":\"40:b8:9a:09:50:e1\",\"rssi\":\"-45\",\"rssi1\":\"-58\",\"rssi2\":\"-54\",\"rssi3\":\"-58\",\"ts\":\"topsroboteer2.4\",\"tmc\":\"80:ea:07:96:8f:22\",\"tc\":\"Y\",\"range\":\"1.8\"},{\"mac\":\"80:e4:55:83:15:f6\",\"rssi\":\"-60\",\"range\":\"6.5\"},{\"mac\":\"86:e4:55:83:15:f6\",\"rssi\":\"-58\",\"router\":\"517\",\"range\":\"5.5\"},{\"mac\":\"a0:57:e3:cd:3a:69\",\"rssi\":\"-91\",\"range\":\"91.8\"},{\"mac\":\"32:a7:76:1d:31:5c\",\"rssi\":\"-76\",\"rssi1\":\"-75\",\"ts\":\"517\",\"tmc\":\"86:e4:55:83:15:f6\",\"tc\":\"Y\",\"range\":\"25.5\"},{\"mac\":\"48:7d:2e:94:b0:61\",\"rssi\":\"-86\",\"tmc\":\"70:47:e9:d7:0a:23\",\"router\":\"jwld\",\"range\":\"59.9\"},{\"mac\":\"92:e4:55:83:15:f6\",\"rssi\":\"-61\",\"router\":\"H3C_GUEST\",\"range\":\"7.1\"},{\"mac\":\"b8:8e:82:db:e6:e0\",\"rssi\":\"-64\",\"rssi1\":\"-62\",\"router\":\"HUAWEI-10EB43\",\"range\":\"9.1\"},{\"mac\":\"9c:a6:15:e9:a6:18\",\"rssi\":\"-86\",\"router\":\"mumumu\",\"range\":\"59.9\"},{\"mac\":\"64:6e:97:8d:35:ba\",\"rssi\":\"-87\",\"tmc\":\"ee:34:53:85:2c:de\",\"router\":\"XZGYWY\",\"range\":\"65.2\"},{\"mac\":\"68:77:24:38:2a:5e\",\"rssi\":\"-76\",\"router\":\"FYJJ\",\"range\":\"25.5\"},{\"mac\":\"c0:2e:25:82:13:05\",\"rssi\":\"-77\",\"rssi1\":\"-80\",\"ts\":\"HUAWEI-10EB43\",\"tmc\":\"b8:8e:82:db:e6:e0\",\"tc\":\"Y\",\"range\":\"27.8\"},{\"mac\":\"ec:26:ca:9c:ad:68\",\"rssi\":\"-82\",\"router\":\"dghy001\",\"range\":\"42.6\"},{\"mac\":\"14:6b:9c:f4:14:65\",\"rssi\":\"-66\",\"router\":\"DataSky_f41465\",\"range\":\"10.8\"},{\"mac\":\"24:a6:5e:e9:15:40\",\"rssi\":\"-94\",\"range\":\"118.5\"},{\"mac\":\"00:c6:81:9c:46:8f\",\"rssi\":\"-77\",\"router\":\"CAR-DVR-468f\",\"range\":\"27.8\"},{\"mac\":\"88:c3:97:a5:c3:b7\",\"rssi\":\"-87\",\"range\":\"65.2\"},{\"mac\":\"36:78:39:32:dd:98\",\"rssi\":\"-68\",\"range\":\"12.9\"},{\"mac\":\"74:70:fd:67:59:2b\",\"rssi\":\"-54\",\"ts\":\"topsroboteer2.4\",\"tmc\":\"80:ea:07:c9:af:c4\",\"tc\":\"N\",\"range\":\"3.9\"},{\"mac\":\"80:ea:07:c9:af:c4\",\"rssi\":\"-57\",\"tmc\":\"74:70:fd:67:59:2b\",\"router\":\"topsroboteer2.4\",\"range\":\"5.0\"},{\"mac\":\"18:31:bf:a1:64:a0\",\"rssi\":\"-81\",\"router\":\"YuYu_2G\",\"range\":\"39.1\"},{\"mac\":\"d0:d7:83:f8:f4:28\",\"rssi\":\"-80\",\"router\":\"DING\",\"range\":\"35.9\"},{\"mac\":\"14:75:90:6f:fc:28\",\"rssi\":\"-79\",\"tmc\":\"30:b4:9e:42:e9:51\",\"router\":\"525\",\"range\":\"33.0\"},{\"mac\":\"20:dc:e6:ff:a3:9a\",\"rssi\":\"-70\",\"router\":\"LABO\",\"range\":\"15.3\"},{\"mac\":\"1e:bf:c0:f4:2a:f2\",\"rssi\":\"-83\",\"router\":\"DIRECT-54DCP-L2535DW_BR2af2\",\"range\":\"46.4\"},{\"mac\":\"88:25:93:e7:3f:28\",\"rssi\":\"-70\",\"router\":\"sanfang\",\"range\":\"15.3\"},{\"mac\":\"68:d1:ba:67:4c:e8\",\"rssi\":\"-75\",\"router\":\"CMCC-pLJx\",\"range\":\"23.4\"},{\"mac\":\"3c:06:a7:bd:c3:fd\",\"rssi\":\"-76\",\"router\":\"VS-506\",\"range\":\"25.5\"},{\"mac\":\"74:05:a5:96:27:be\",\"rssi\":\"-52\",\"router\":\"yichao888\",\"range\":\"3.3\"},{\"mac\":\"80:ea:07:c9:a7:84\",\"rssi\":\"-71\",\"tmc\":\"d4:ab:cd:7f:a3:71\",\"router\":\"topsroboteer2.4\",\"range\":\"16.6\"},{\"mac\":\"34:96:72:c4:e0:1b\",\"rssi\":\"-69\",\"router\":\"406\",\"range\":\"14.0\"},{\"mac\":\"12:5b:ad:9d:36:e0\",\"rssi\":\"-61\",\"router\":\"DIRECT-e0-HP M130 LaserJet\",\"range\":\"7.1\"},{\"mac\":\"c2:b8:e6:85:bd:60\",\"rssi\":\"-77\",\"router\":\"JXZY813\",\"range\":\"27.8\"},{\"mac\":\"08:40:f3:5f:cd:91\",\"rssi\":\"-79\",\"tmc\":\"ec:89:14:a8:4b:85\",\"router\":\"B526\",\"range\":\"33.0\"},{\"mac\":\"84:3e:92:be:9f:28\",\"rssi\":\"-58\",\"router\":\"CMCC-AVaW\",\"range\":\"5.5\"},{\"mac\":\"ec:6c:b5:92:53:1a\",\"rssi\":\"-89\",\"router\":\"CU_LianTronics-BeiJing\",\"range\":\"77.4\"},{\"mac\":\"d2:98:05:2a:8d:69\",\"rssi\":\"-87\",\"ts\":\"TPGuest_2465\",\"tmc\":\"b6:95:8e:4f:24:65\",\"tc\":\"Y\",\"range\":\"65.2\"},{\"mac\":\"74:1e:93:5b:d5:f1\",\"rssi\":\"-77\",\"router\":\"STB_HLPI\",\"range\":\"27.8\"},{\"mac\":\"16:ba:6f:20:2c:07\",\"rssi\":\"-65\",\"rssi1\":\"-64\",\"rssi2\":\"-66\",\"range\":\"10.0\"},{\"mac\":\"08:71:90:ec:09:a0\",\"rssi\":\"-69\",\"range\":\"14.0\"},{\"mac\":\"4a:5f:99:27:f3:cc\",\"rssi\":\"-77\",\"tmc\":\"08:71:90:ec:09:a0\",\"router\":\"DIRECT-cc-HP M104 LaserJet\",\"range\":\"27.8\"},{\"mac\":\"b6:95:8e:4f:24:65\",\"rssi\":\"-81\",\"tmc\":\"d2:98:05:2a:8d:69\",\"router\":\"TPGuest_2465\",\"range\":\"39.1\"},{\"mac\":\"3c:06:a7:9a:7a:e6\",\"rssi\":\"-74\",\"router\":\"hetao506\",\"range\":\"21.5\"},{\"mac\":\"74:1e:93:5b:d5:f0\",\"rssi\":\"-78\",\"router\":\"CU_HLPI\",\"range\":\"30.3\"},{\"mac\":\"c0:b8:e6:08:38:f0\",\"rssi\":\"-82\",\"router\":\"BJMEDNEW\",\"range\":\"42.6\"},{\"mac\":\"34:71:46:54:af:b4\",\"rssi\":\"-72\",\"tmc\":\"da:a1:19:0a:75:21\",\"router\":\"华为路由器\",\"range\":\"18.1\"},{\"mac\":\"46:06:a7:9a:7a:e6\",\"rssi\":\"-77\",\"range\":\"27.8\"},{\"mac\":\"5a:79:95:e5:dd:b6\",\"rssi\":\"-89\",\"range\":\"77.4\"},{\"mac\":\"8c:8f:8b:d2:69:0e\",\"rssi\":\"-88\",\"tmc\":\"f4:bf:80:f0:e0:bc\",\"router\":\"CMCC-zmzk\",\"range\":\"71.0\"},{\"mac\":\"f4:bf:80:f0:e0:bc\",\"rssi\":\"-87\",\"ts\":\"CMCC-zmzk\",\"tmc\":\"8c:8f:8b:d2:69:0e\",\"tc\":\"N\",\"range\":\"65.2\"},{\"mac\":\"e8:13:6e:b6:2b:84\",\"rssi\":\"-84\",\"router\":\"CU_h9Pu\",\"range\":\"50.5\"},{\"mac\":\"34:75:63:2c:12:86\",\"rssi\":\"-65\",\"rssi1\":\"-64\",\"essid0\":\"DD02\",\"range\":\"10.0\"},{\"mac\":\"a0:57:e3:cd:3a:68\",\"rssi\":\"-88\",\"router\":\"DQ812\",\"range\":\"71.0\"},{\"mac\":\"52:9e:bc:c8:e7:e5\",\"rssi\":\"-55\",\"ts\":\"topsroboteer2.4\",\"tmc\":\"80:ea:07:96:8f:22\",\"tc\":\"N\",\"range\":\"4.2\"},{\"mac\":\"88:c3:97:f1:3c:22\",\"rssi\":\"-87\",\"router\":\"Xiaom203\",\"range\":\"65.2\"},{\"mac\":\"3c:06:a7:87:94:b8\",\"rssi\":\"-79\",\"tmc\":\"33:33:00:00:00:0c\",\"router\":\"VS-506\",\"range\":\"33.0\"},{\"mac\":\"8c:eb:c6:26:18:4f\",\"rssi\":\"-84\",\"tmc\":\"30:b4:9e:50:8a:d3\",\"router\":\"HUAWEI-B315-184F\",\"range\":\"50.5\"},{\"mac\":\"3c:06:a7:87:94:3e\",\"rssi\":\"-75\",\"router\":\"VS-506\",\"range\":\"23.4\"},{\"mac\":\"80:89:17:01:9d:82\",\"rssi\":\"-87\",\"router\":\"hchs412\",\"range\":\"65.2\"},{\"mac\":\"74:05:a5:92:e5:34\",\"rssi\":\"-75\",\"router\":\"TP-LINK_E534anj\",\"range\":\"23.4\"},{\"mac\":\"b0:95:8e:4f:24:65\",\"rssi\":\"-80\",\"router\":\"ZYJL\",\"range\":\"35.9\"}],\"mmac\":\"14:6b:9c:f4:14:65\",\"rate\":\"5\",\"time\":\"Fri Mar 11 16:56:42 2022\",\"lat\":\"\",\"lon\":\"\"} \n";
        json=json.replace("data=","");
        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray features = jsonObject.getJSONArray("data");
        System.out.println("Data数据"+features);
        for (int i = 0; i < features.size(); i++) {
            JSONObject info = features.getJSONObject(i);// 获取features数组的第i个json对象
            String mac = info.getString("mac");
            System.out.println("mac"+mac);
            String rssi = info.getString("rssi");
            System.out.println("rssi"+rssi);
            String rssi1 = info.getString("rssi1");
            System.out.println("rssi1"+rssi1);
            String rssi2 = info.getString("rssi2");
            System.out.println("rssi2"+rssi2);
            String rssi3 = info.getString("rssi3");
            System.out.println("rssi3"+rssi3);
            String tmc = info.getString("tmc");
            System.out.println("tmc"+tmc);
            boolean router1 = info.containsKey("router");
            if (router1 == true) {
                String router = info.getString("router");
                System.out.println("router"+router);
            }
            String range = info.getString("range");
            System.out.println("range"+range);
            System.out.println("JSON数据"+info);
        }
        String aaa = jsonObject.getString("id");
        System.out.println("mac:" + aaa);
    }
}

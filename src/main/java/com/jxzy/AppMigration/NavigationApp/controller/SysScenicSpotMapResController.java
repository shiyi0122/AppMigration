package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotMapResService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotMapRes;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * @Author zhang
 * @Date 2022/8/29 18:34
 */

@Api(tags = "地图下载")
@RestController
@RequestMapping("scenicSpotMapRes")
public class SysScenicSpotMapResController {

    @Autowired
    SysScenicSpotMapResService sysScenicSpotMapResService;

    @Value("${GET_MAP_PAHT}")
    private String GET_MAP_PAHT;
    @Value("${GET_MAP_URL}")
    private String GET_MAP_URL;




//    @ApiOperation("根据景区id获取地图包,无法使用")
//    @GetMapping("/getScenicSpotMapRes")
//    public void getScenicSpotMapRes(Long spotId, HttpServletResponse response) throws IOException{
//
//        SysScenicSpotMapRes sysScenicSpotMapRes = sysScenicSpotMapResService.getScenicSpotMapRes(spotId);
//
//        if (!StringUtils.isEmpty(sysScenicSpotMapRes)){
//            String resUrl = sysScenicSpotMapRes.getResUrl();
//            String substring = resUrl.substring(18);
//            File file = new File(GET_MAP_URL  + substring);
//            if(file.exists()){
//                response.setContentType("application/octet-stream");
//                response.setHeader("content-type", "application/octet-stream");
//                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(substring,"utf8"));
//                byte[] buffer = new byte[1024];
//                //输出流
//                OutputStream os = null;
//                try(FileInputStream fis= new FileInputStream(file);
//                    BufferedInputStream bis = new BufferedInputStream(fis);) {
//                    os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while(i != -1){
//                        os.write(buffer);
//                        i = bis.read(buffer);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else{
//                response.setContentType("text/html; charset=UTF-8"); //转码
//                PrintWriter out = response.getWriter();
//                out.flush();
//                out.println("<script defer='defer' type='text/javascript'>");
//                out.println("alert('文件不存在或已经被删除！');");
//                out.println("window.location.href='/page/system/robotMapRes/html/robotMapResList.html';");
//                out.println("</script>");
//            }
//
//
//        }
//    }

    @ApiOperation("根据景区id获取地图包")
    @GetMapping("/getScenicSpotMapResTwo")
    public ReturnModel getScenicSpotMapResTwo(Long spotId, HttpServletResponse response) throws IOException{

        ReturnModel returnModel = new ReturnModel();

        SysScenicSpotMapRes sysScenicSpotMapRes = sysScenicSpotMapResService.getScenicSpotMapRes(spotId);

        if (!StringUtils.isEmpty(sysScenicSpotMapRes)){
            String resUrl = sysScenicSpotMapRes.getResUrl();
            String s = resUrl.substring(0, 1);
            if ("s".equals(s)){
                resUrl = resUrl.substring(18);
                sysScenicSpotMapRes.setResUrl(resUrl);
                returnModel.setData(sysScenicSpotMapRes);
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                resUrl = resUrl.substring(11);
                sysScenicSpotMapRes.setResUrl(resUrl);

                returnModel.setData(sysScenicSpotMapRes);
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
        }
        returnModel.setData("");
        returnModel.setState(Constant.STATE_FAILURE);

        return returnModel;
    }


}

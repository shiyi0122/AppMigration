package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.entity.vo.FailedResultVO;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.FileUtil;
import com.jxzy.AppMigration.NavigationApp.util.PicUtils;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author zhang
 * @Date 2023/1/14 10:31
 */
@Api(tags = "游娱go用户上传文件接口")
@CrossOrigin
@RestController
@RequestMapping("uploadFile")
public class UploadFileController {

    //攻略图片上传地址
    @Value("${sysStrategyPatheGetPicUrl}")
    private String sysStrategyPatheGetPicUrl;
    @Value("${sysStrategyPatheGetPicPaht}")
    private String sysStrategyPatheGetPicPaht;

    //特色美食，地道好物，酒店民宿，娱乐项目图片上传地址
    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;

    //动态图片上传地址
    @Value("${sysSpotDynamicPatheGetPicPaht}")
    private String sysSpotDynamicPatheGetPicPaht;
    @Value("${sysSpotDynamicPatheGetPicUrl}")
    private String sysSpotDynamicPatheGetPicUrl;

    //导航图片图片上传
    @Value("${navigationPatheGetPicUrl}")
    private String sysNavigationPatheGetPicUrl;
    @Value("${navigationPatheGetPicPaht}")
    private String sysNavigationPatheGetPicPaht;

    //地标图片上传
    @Value("${sysLandmarkPatheGetPicUrl}")
    private String sysLandmarkPatheGetPicUrl;
    @Value("${sysLandmarkPatheGetPicPaht}")
    private String sysLandmarkPatheGetPicPaht;

    //app版本文件上传
    @Value("${sysAppVersionPatheGetPicUrl}")
    private String sysAppVersionPatheGetPicUrl;
    @Value("${sysAppVersionPatheGetPicPaht}")
    private String sysAppVersionPatheGetPicPaht;

    //推荐线路图片封面文件上传
    @Value("${sysRoutePatheGetPicUrl}")
    private String sysRoutePatheGetPicUrl;
    @Value("${sysRoutePatheGetPicPaht}")
    private String sysRoutePatheGetPicPaht;

    @Value("${sysMuseumPatheGetPicUrl}")
    private String sysMuseumPatheGetPicUrl;
    @Value("${sysMuseumPatheGetPicPaht}")
    private String sysMuseumPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;


    @Autowired
    PicUtils picUtils;

    @ApiOperation("攻略图片文件上传接口")
    @PostMapping("/addStrategyFile")
    public String addStrategyFile(@NotNull @RequestPart("file") MultipartFile[] multipartFile, HttpServletRequest request) {

        String filename = null;
        String url = null;
        try {
            if (multipartFile.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile files = multipartFile[i];
                    long size = files.getSize();

//                    byte[] bytes = picUtils.compressPicForScale(files.getBytes(), 100);
//
//                    InputStream inputStream = new ByteArrayInputStream(bytes);
//                    MultipartFile fileResult = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
//                    long size1 = fileResult.getSize();

                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(sysStrategyPatheGetPicPaht + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = sysStrategyPatheGetPicUrl + filename;
                    } else {
                        url = url + "," + sysStrategyPatheGetPicUrl + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地

                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);


                }
                return url;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @ApiOperation("动态图片文件上传接口")
    @PostMapping("/addDynamicFile")
    public String addDynamicFile(@NotNull @RequestPart("file") MultipartFile[] multipartFile, HttpServletRequest request) {

        String filename = null;
        String url = null;
        try {
            if (multipartFile.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile files = multipartFile[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(sysSpotDynamicPatheGetPicPaht + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = sysSpotDynamicPatheGetPicUrl + filename;
                    } else {
                        url = url + "," + sysSpotDynamicPatheGetPicUrl + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                return url;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation("特色美食，地道好物，酒店民宿，娱乐项目图片文件上传接口")
    @PostMapping("/addFeaturedFoodFile")
    public String addFeaturedFoodFile(@NotNull @RequestPart("file") MultipartFile[] multipartFile, HttpServletRequest request) {

        String filename = null;
        String url = null;
        try {
            if (multipartFile.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile files = multipartFile[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(featuredFoodPatheGetPicPaht + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = featuredFoodPatheGetPicUrl + filename;
                    } else {
                        url = url + "," + featuredFoodPatheGetPicUrl + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                return url;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation("导航图片文件上传接口")
    @PostMapping("/addNavigationFile")
    public String addNavigationFile(@NotNull @RequestPart("file") MultipartFile[] multipartFile, HttpServletRequest request) {

        String filename = null;
        String url = null;
        try {
            if (multipartFile.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile files = multipartFile[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(sysNavigationPatheGetPicPaht + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = sysNavigationPatheGetPicUrl + filename;
                    } else {
                        url = url + "," + sysNavigationPatheGetPicUrl + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                return url;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @ApiOperation("地标文件上传接口")
    @PostMapping("/addLandmarkFile")
    public String addLandmarkFile(@NotNull @RequestPart("file") MultipartFile[] multipartFile, HttpServletRequest request) {

        String filename = null;
        String url = null;
        try {
            if (multipartFile.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile files = multipartFile[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(sysLandmarkPatheGetPicPaht + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = sysLandmarkPatheGetPicUrl + filename;
                    } else {
                        url = url + "," + sysLandmarkPatheGetPicUrl + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                return url;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @ApiOperation("线路封面上传接口")
    @PostMapping("/addRouteFile")
    public String addRouteFile(@NotNull @RequestPart("file") MultipartFile[] multipartFile, HttpServletRequest request) {

        String filename = null;
        String url = null;
        try {
            if (multipartFile.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile files = multipartFile[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(sysRoutePatheGetPicPaht + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = sysRoutePatheGetPicUrl + filename;
                    } else {
                        url = url + "," + sysRoutePatheGetPicUrl + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                return url;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation("博物馆相关上传图片接口")
    @PostMapping("/addSysMuseumFile")
    public String addSysMuseumFile(@NotNull @RequestPart("file") MultipartFile[] multipartFile, HttpServletRequest request) {

        String filename = null;
        String url = null;
        try {
            if (multipartFile.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile files = multipartFile[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(sysMuseumPatheGetPicPaht + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = sysMuseumPatheGetPicUrl + filename;
                    } else {
                        url = url + "," + sysMuseumPatheGetPicUrl + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                return url;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @ApiOperation("app版本文件上传接口")
    @PostMapping("/addAppVersionFile")
    public String addAppVersionFile(@NotNull @RequestPart("file") MultipartFile file, HttpServletRequest request) {

        String filename = null;
        String url = null;
        Map<String, Object> map = new HashMap<>();
        try {
            long size = file.getSize();
            String OriginalFilename = file.getOriginalFilename();
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            //重新随机生成名字
            filename = UUID.randomUUID().toString() + suffixName;//重命名
            File localFile = new File(sysAppVersionPatheGetPicPaht + filename);
            if (StringUtils.isEmpty(url)) {
                url = sysAppVersionPatheGetPicPaht + filename;
            } else {
                url = url + "," + sysAppVersionPatheGetPicPaht + filename;
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
            String upload = fileUploadUtil.upload(file, sysAppVersionPatheGetPicPaht.substring(1) + filename);
            System.out.println("upload = " + upload);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

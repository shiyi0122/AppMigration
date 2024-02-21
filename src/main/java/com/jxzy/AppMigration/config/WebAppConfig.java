package com.jxzy.AppMigration.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author zhang
 * @Date 2022/8/22 14:16
 * 本地启动时，和前端联调访问图片地址使用，打包上传服务器时需注释调 ！！！！！！！！
 * ！！！！！！！！！！！！！！！！！！！！！！！！！！
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

//    @Value("${cbs.imagesPath}")
//    private String mImagesPath;
//    //本地访问图片方法
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if(mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")){
//            String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
//            if(imagesPath.indexOf(".jar")>0){
//                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
//            }else if(imagesPath.indexOf("classes")>0){
//                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
//            }
//            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/static/";
//            mImagesPath = imagesPath;
//        }
//        LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath="+mImagesPath);
//        registry.addResourceHandler("/**").addResourceLocations(mImagesPath);
//        super.addResourceHandlers(registry);
//    }
}

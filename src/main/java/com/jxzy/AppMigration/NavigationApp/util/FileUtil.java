package com.jxzy.AppMigration.NavigationApp.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: FileUtil
 * @Author: 郭凯
 * @Description: Excel导出工具类
 * @Date: 2020/9/14 11:38
 * @Version: 1.0
 */
public class FileUtil {

    public static final Logger logger = LoggerFactory.getLogger(PublicUtil.class);
    
    /** 允许导出的最大条数 */
    //private static final Integer EXPORT_EXCEL_MAX_NUM = 10000;

    /**
     * 获取导出的 Workbook对象
     *
     * @param title     大标题
     * @param sheetName 页签名
     * @param object    导出实体
     * @param list      数据集合
     * @return Workbook
     */
    public static Workbook getWorkbook(String title, String sheetName, Class<?> object, List<?> list) {
        //判断导出数据是否为空
        if (list == null) {
            list = new ArrayList<>();
        }
        //判断导出数据数量是否超过限定值
		/*
		 * if (list.size() > EXPORT_EXCEL_MAX_NUM) { title = "导出数据行数超过:" +
		 * EXPORT_EXCEL_MAX_NUM + "条,无法导出、请添加导出条件!"; list = new ArrayList<>(); }
		 */
        //获取导出参数
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        //设置导出样式
        exportParams.setStyle(ExcelStyleUtil.class);
        //设置行高
        exportParams.setHeight((short) 6);
        //输出Workbook流
        return ExcelExportUtil.exportExcel(exportParams, object, list);
    }

    /**
     * 获取导出的 Workbook对象
     *
     * @param path 模板路径
     * @param map  导出内容map
     * @return Workbook
     */
    public static Workbook getWorkbook(String path, Map<String, Object> map) {
        //获取导出模板
        TemplateExportParams params = new TemplateExportParams(path);
        //设置导出样式
        params.setStyle(ExcelStyleUtil.class);
        //输出Workbook流
        return ExcelExportUtil.exportExcel(params, map);
    }

    /**
     * 获取导出的 Workbook对象
     *
     * @param title     大标题
     * @param sheetName 页签名
     * @param object    导出实体
     * @param server      数据集合
     * @return Workbook
     */
    public static Workbook getWorkbooks(String title, String sheetName, Class<?> object, IExcelExportServer server, Map<String, Object> params) {
        //判断导出数据数量是否超过限定值
        /*
         * if (list.size() > EXPORT_EXCEL_MAX_NUM) { title = "导出数据行数超过:" +
         * EXPORT_EXCEL_MAX_NUM + "条,无法导出、请添加导出条件!"; list = new ArrayList<>(); }
         */
        //获取导出参数
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        //设置导出样式
        exportParams.setStyle(ExcelStyleUtil.class);
        //设置行高
        exportParams.setHeight((short) 6);
        //输出Workbook流
        return ExcelExportUtil.exportBigExcel(exportParams, object,server,params);
    }

    /**
     * 导出Excel
     *
     * @param workbook workbook流
     * @param fileName 文件名
     * @param response 响应
     */
    public static void exportExcel(Workbook workbook, String fileName, HttpServletResponse response) {
        //输出文件
        try (OutputStream out = response.getOutputStream()) {
            //获取文件名并转码
            String name = URLEncoder.encode(fileName, "UTF-8");
            //编码
            response.setCharacterEncoding("UTF-8");
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xlsx");
            //输出表格
            workbook.write(out);
        } catch (IOException e) {
        	logger.error("文件导出异常,详情如下:", e);
        } finally {
            try {
                if (workbook != null) {
                    //关闭输出流
                    workbook.close();
                }
            } catch (IOException e) {
            	logger.error("文件导出异常,详情如下:", e);
            }
        }
    }

}

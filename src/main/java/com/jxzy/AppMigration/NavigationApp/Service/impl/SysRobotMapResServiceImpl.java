package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysRobotMapResService;
import com.jxzy.AppMigration.NavigationApp.dao.SysRobotMapResMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRobotMapResServiceImpl implements SysRobotMapResService {
    @Autowired
    private SysRobotMapResMapper sysRobotMapResMapper;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址

    @Value("${GET_MAP_PAHT}")
    private String GET_MAP_PAHT;

    @Value("${GET_MAP_URL}")
    private String GET_MAP_URL;

    @Autowired
    FileUploadUtil fileUploadUtil;


    /**
     * 查询地图资源
     *
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    public List<SysRobotMapRes> queryMapRes(Map<String, Object> search) {
        List<SysRobotMapRes> res = sysRobotMapResMapper.queryMapRes(search);

        for (int i = 0; i < res.size(); i++) {
            Long resScenicSpotId = res.get(i).getResScenicSpotId();
            if ("21011241290975".equals(resScenicSpotId.toString())) {
                res.get(i).setResUrl(DOMAIN_NAME + "static/upload_map/1635405342994--.zip");
            } else {
                res.get(i).setResUrl(DOMAIN_NAME + res.get(i).getResUrl());
            }
        }
        return res;
    }

    /**
     * 后台管理--地图列表查询
     *
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSysRobotMapResList(Map<String, Object> search, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotMapRes> list = sysRobotMapResMapper.getSysRobotMapResList(search);

        for (SysRobotMapRes sysRobotMapRes : list) {
            String resUrl = sysRobotMapRes.getResUrl();
            String s = resUrl.substring(0, 1);
            if ("s".equals(s)) {
                resUrl = resUrl.substring(18);
                sysRobotMapRes.setResUrl(resUrl);
            } else {
                resUrl = resUrl.substring(11);
                sysRobotMapRes.setResUrl(resUrl);
            }
        }

        if (list.size() > 0) {
            PageInfo<SysRobotMapRes> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理--修改地图启用禁用
     *
     * @param id
     * @param state
     * @return
     */
    @Override
    public int editSysRobotMapResState(Long id, String state) {

        SysRobotMapRes sysRobotMapRes = sysRobotMapResMapper.selectByPrimaryKey(id);
        sysRobotMapRes.setResType(state);
        sysRobotMapRes.setUpdateDate(DateUtil.currentDateTime());
        int i = sysRobotMapResMapper.updateByPrimaryKeySelective(sysRobotMapRes);
        return i;
    }

    /**
     * 后台管理--删除地图对应资源
     *
     * @param id
     * @param
     * @return
     */
    @Override
    public int delSysRobotMapRes(Long id) {

        int i = sysRobotMapResMapper.deleteByPrimaryKey(id);
        return i;
    }

    /**
     * 后台管理--添加地图资源
     *
     * @param file
     * @param sysRobotMapRes
     * @return
     */
    @Override
    public int addRobotMapRes(MultipartFile file, SysRobotMapRes sysRobotMapRes) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".zip")) {
            String filename = "";
            String filenameMysql = "";
            if (sysRobotMapRes.getResScenicSpotId() == 21011241290975l || sysRobotMapRes.getResScenicSpotId() == 110902424077155l) {

                long l = System.currentTimeMillis();
                //文件实际存放的名称
                filename = l + "--" + type;// 取当前时间戳作为文件名
                //文件在数据库中保存的名称
                filenameMysql = l + type;// 取当前时间戳作为文件名

            } else {
                long l = System.currentTimeMillis();

                filename = l + type;// 取当前时间戳作为文件名

                filenameMysql = l + type;// 取当前时间戳作为文件名

            }

            String path = GET_MAP_PAHT + filename;// 存放位置

            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                //阿里文件存储
                String upload = null;
                upload = fileUploadUtil.upload(file, GET_MAP_PAHT.substring(1) + filename);

                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysRobotMapRes.setResId(IdUtils.getSeqId());
            sysRobotMapRes.setResUrl(GET_MAP_URL + filenameMysql);
            String size = Exam_getSize.getSize(file);
            sysRobotMapRes.setResSize(size);
            sysRobotMapRes.setCreateDate(DateUtil.currentDateTime());
            sysRobotMapRes.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotMapResMapper.insertSelective(sysRobotMapRes);
        } else {
            return 2;
        }


    }
}

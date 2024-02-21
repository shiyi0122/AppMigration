package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysAboutUsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysAboutUsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUs;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserFans;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 15:05
 */
@Service
public class SysAboutUsServiceImpl implements SysAboutUsService {

    @Autowired
    SysAboutUsMapper sysAboutUsMapper;

    @Override
    public int addSysAboutUs(SysAboutUs sysAboutUs) {

        SysAboutUs sysAboutUsN = sysAboutUsMapper.getTypeIdByList(sysAboutUs.getTypeId(),sysAboutUs.getAppSubversionId().toString());

        if (StringUtils.isEmpty(sysAboutUsN)){
            sysAboutUs.setId(IdUtils.getSeqId());
            sysAboutUs.setCreateTime(DateUtil.currentDateTime());
            sysAboutUs.setUpdateTime(DateUtil.currentDateTime());
            int i = sysAboutUsMapper.insert(sysAboutUs);
            return i;
        }else{
            sysAboutUsN.setContent(sysAboutUs.getContent());
            sysAboutUsN.setTitle(sysAboutUs.getTitle());
            sysAboutUsN.setTypeId(sysAboutUs.getTypeId());
            sysAboutUsN.setUpdateTime(DateUtil.currentDateTime());
            int i = sysAboutUsMapper.update(sysAboutUsN);
            return i;
        }
    }

    @Override
    public int editSysAboutUs(SysAboutUs sysAboutUs) {

        sysAboutUs.setUpdateTime(DateUtil.currentDateTime());
        int i = sysAboutUsMapper.update(sysAboutUs);

        return i;
    }

    @Override
    public PageDataResult getSysAboutUsList(String type,String subversionId) {

        PageDataResult pageDataResult = new PageDataResult();

        List<SysAboutUs> list = sysAboutUsMapper.list(type,subversionId);
        if (list.size()>0){
            PageInfo<SysAboutUs> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }


    @Override
    public int delSysAboutUs(Long id) {

        int i = sysAboutUsMapper.delete(id);
        return i;

    }

    /**
     * app获取协议
     * @param type
     * @return
     */
    @Override
    public SysAboutUs getSysUserFans(String type) {
        SysAboutUs sysAboutUs = new SysAboutUs();
        if ("1".equals(type)){//用户协议
            sysAboutUs =  sysAboutUsMapper.selectById("11810242415014");

        }else if ("2".equals(type)){//隐私政策
             sysAboutUs =  sysAboutUsMapper.selectById("11710033706938");

        }else if ("3".equals(type)){//游娱go介绍
             sysAboutUs = sysAboutUsMapper.selectById("11906090813281");

        }else if ("4".equals(type)){//公司介绍
            sysAboutUs = sysAboutUsMapper.selectById("11906111943479");

        }else if("5".equals(type)){//什么是伴游机器人
            sysAboutUs = sysAboutUsMapper.selectById("22110152839416");

            String content = sysAboutUs.getContent();

        }

        return sysAboutUs;

    }

    /**
     * 新获取协议接口游娱go
     * @param type
     * @param subversionId
     * @return
     */
    @Override
    public SysAboutUs getSysUserFansNew(String type, String subversionId) {

        SysAboutUs sysAboutUs = new SysAboutUs();
        if ("1".equals(type)){//用户协议

            sysAboutUs = sysAboutUsMapper.selectTypeNameAndSubversionId("用户协议",subversionId);
//            sysAboutUs =  sysAboutUsMapper.selectById("11810242415014");

        }else if ("2".equals(type)){//隐私政策
            sysAboutUs = sysAboutUsMapper.selectTypeNameAndSubversionId("隐私政策",subversionId);
//            sysAboutUs =  sysAboutUsMapper.selectById("11710033706938");

        }else if ("3".equals(type)){//游娱go介绍
            sysAboutUs = sysAboutUsMapper.selectTypeNameAndSubversionId("游娱go介绍",subversionId);

//            sysAboutUs = sysAboutUsMapper.selectById("11906090813281");

        }else if ("4".equals(type)){//公司介绍
            sysAboutUs = sysAboutUsMapper.selectTypeNameAndSubversionId("公司简介",subversionId);
//            sysAboutUs = sysAboutUsMapper.selectById("11906111943479");

        }else if("5".equals(type)){//什么是伴游机器人
            sysAboutUs = sysAboutUsMapper.selectById("22110152839416");
            String content = sysAboutUs.getContent();

        }
        return sysAboutUs;

    }
}

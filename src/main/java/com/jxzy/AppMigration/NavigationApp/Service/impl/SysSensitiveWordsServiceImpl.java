package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysSensitiveWordsService;
import com.jxzy.AppMigration.NavigationApp.entity.SysSensitiveWords;
import com.jxzy.AppMigration.NavigationApp.dao.SysSensitiveWordsMapper;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.poi.ss.formula.ptg.AreaI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/14 15:56
 */
@Service
public class SysSensitiveWordsServiceImpl implements SysSensitiveWordsService {

    @Autowired
    SysSensitiveWordsMapper sysSensitiveWordsMapper;

    /**
     * 添加敏感词
     * @param sysSensitiveWords
     * @return
     */
    @Override
    public int addSysAboutUs(SysSensitiveWords sysSensitiveWords) {

        sysSensitiveWords.setId(IdUtils.getSeqId());
        sysSensitiveWords.setCreateTime(DateUtil.currentDateTime());
        sysSensitiveWords.setUpdateTime(DateUtil.currentDateTime());
        int i = sysSensitiveWordsMapper.insert(sysSensitiveWords);
        return i;
    }

    /**
     * 修改敏感词
     * @param sysSensitiveWords
     * @return
     */
    @Override
    public int editSysSensitiveWords(SysSensitiveWords sysSensitiveWords) {

        sysSensitiveWords.setUpdateTime(DateUtil.currentDateTime());
        int i = sysSensitiveWordsMapper.update(sysSensitiveWords);
        return i;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int delSysSensitiveWords(Long id) {


       int i = sysSensitiveWordsMapper.delete(id);
       return i;
    }

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @param content
     * @return
     */
    @Override
    public PageDataResult getSysSensitiveWordsList(Integer pageNum, Integer pageSize, String content) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysSensitiveWords> list = sysSensitiveWordsMapper.list(content);
        if (list.size()>0){
            PageInfo<SysSensitiveWords> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }
}

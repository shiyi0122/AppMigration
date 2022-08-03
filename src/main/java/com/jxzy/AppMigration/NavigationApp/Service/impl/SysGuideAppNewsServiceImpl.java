package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.io.unit.DataUnit;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppNewsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppNewsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppNewsReadMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNews;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNewsRead;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysGuideAppNewsServiceImpl implements SysGuideAppNewsService {
    @Autowired
    private SysGuideAppNewsMapper sysGuideAppNewsMapper;
    @Autowired
    private SysGuideAppNewsReadMapper sysGuideAppNewsReadMapper;
    
    /**
     * 查询消息列表
     * @param: 
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    public List<SysGuideAppNews> queryGuideAppNewsListsLimit(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysGuideAppNewsMapper.queryGuideAppNewsListsLimit(search);
    }

    /**
     * 根据ID删除消息
     * @param: guideId
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    public int deleteGuideAppNews(String guideId,String type) {
        SysGuideAppNews news = new SysGuideAppNews();
        if (type.equals("0")) {//批量清除未读消息
            news.setGuideState("1");
            return sysGuideAppNewsMapper.updateByBatchSelective(news);
        }else {
            news.setGuideId(Long.parseLong(guideId));
            news.setGuideState(type);
            return sysGuideAppNewsMapper.updateByPrimaryKeySelective(news);
        }
    }

    /**
     * 读取消息
     * @param userId
     * @param guideId
     * @return
     */
    @Override
    public SysGuideAppNews getSysGuideAppNewsRead(String userId, Long guideId) {

        SysGuideAppNews sysGuideAppNews = sysGuideAppNewsMapper.selectByPrimaryKey(guideId);

        SysGuideAppNewsRead sysGuideAppNewsRead = new SysGuideAppNewsRead();

        sysGuideAppNewsRead.setId(IdUtils.getSeqId());
        sysGuideAppNewsRead.setGuideUserId(Long.parseLong(userId));
        sysGuideAppNewsRead.setGuideId(guideId);
        sysGuideAppNewsRead.setCreateTime(DateUtil.currentDateTime());

          int i = sysGuideAppNewsReadMapper.insertSelective(sysGuideAppNewsRead);


          return sysGuideAppNews;





    }

    /**
     * 全部已读
     * @param userId
     * @return
     */
    @Override
    public int allRead(String userId) {

        HashMap<String, Object> search = new HashMap<>();

        search.put("userId",userId);

        List<String> guideAppNewsIdList =  sysGuideAppNewsMapper.selectIdBySearch(search);

        List<String> guideAppNewsReadIdList = sysGuideAppNewsReadMapper.selectIdBySearch(search);
        guideAppNewsIdList.removeAll(guideAppNewsReadIdList);
        int ins = 0;
        if (guideAppNewsIdList.size()>0){
            SysGuideAppNewsRead sysGuideAppNewsRead = new SysGuideAppNewsRead();
            for (String s : guideAppNewsIdList) {
                sysGuideAppNewsRead = new SysGuideAppNewsRead();
                sysGuideAppNewsRead.setId(IdUtils.getSeqId());
                sysGuideAppNewsRead.setGuideId(Long.parseLong(s));
                sysGuideAppNewsRead.setGuideUserId(Long.parseLong(userId));
                sysGuideAppNewsRead.setCreateTime(DateUtil.currentDateTime());
                ins = sysGuideAppNewsReadMapper.insertSelective(sysGuideAppNewsRead);
            }

        }else{
            return ins;
        }
        return ins;
    }
}

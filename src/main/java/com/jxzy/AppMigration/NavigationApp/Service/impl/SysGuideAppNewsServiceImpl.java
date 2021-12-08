package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppNewsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppNewsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysGuideAppNewsServiceImpl implements SysGuideAppNewsService {
    @Autowired
    private SysGuideAppNewsMapper sysGuideAppNewsMapper;
    
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
}

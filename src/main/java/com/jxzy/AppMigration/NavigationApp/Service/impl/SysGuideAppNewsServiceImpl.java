package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppNewsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppNewsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppNewsReadMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNews;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNewsRead;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.*;

@Service
@Transactional
public class SysGuideAppNewsServiceImpl implements SysGuideAppNewsService {
    @Autowired
    private SysGuideAppNewsMapper sysGuideAppNewsMapper;
    @Autowired
    private SysGuideAppNewsReadMapper sysGuideAppNewsReadMapper;
    @Autowired
    private SysGuideAppUsersMapper sysGuideAppUsersMapper;
    
    /**
     * 查询消息列表
     * @param: 
     * @description: TODO
     * @author: qushaobei---zhang
     * @date: 2021/11/2 0002
     */
    public List<SysGuideAppNews> queryGuideAppNewsListsLimit(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysGuideAppNews> guideAppNews = sysGuideAppNewsMapper.queryGuideAppNewsListsLimit(search);
        List<SysGuideAppNews> guideAppNewsPart =sysGuideAppNewsMapper.queryGuideAppNewsPart(search);
        for (SysGuideAppNews sysGuideAppNews : guideAppNewsPart) {
            guideAppNews.add(sysGuideAppNews);
        }
        return guideAppNews;
    }

    /**
     * 根据ID删除消息
     *
     * @param: guideId
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    public int deleteGuideAppNews(String guideId, String type) {
        SysGuideAppNews news = new SysGuideAppNews();
        if (type.equals("0")) {//批量清除未读消息
            news.setGuideState("1");
            return sysGuideAppNewsMapper.updateByBatchSelective(news);
        } else {
            news.setGuideId(Long.parseLong(guideId));
            news.setGuideState(type);
            return sysGuideAppNewsMapper.updateByPrimaryKeySelective(news);
        }
    }

    /**
     * 读取消息
     *
     * @param userId
     * @param guideId
     * @return
     * @author zhang
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
     *
     * @param userId
     * @return
     * @author zhang
     */
    @Override
    public int allRead(String userId) {

        HashMap<String, Object> search = new HashMap<>();
        search.put("userId", userId);
        List<String> guideAppNewsIdList = sysGuideAppNewsMapper.selectIdBySearch(search);
        List<String> guideAppNewsReadIdList = sysGuideAppNewsReadMapper.selectIdBySearch(search);
        guideAppNewsIdList.removeAll(guideAppNewsReadIdList);
        int ins = 0;
        if (guideAppNewsIdList.size() > 0) {
            SysGuideAppNewsRead sysGuideAppNewsRead = new SysGuideAppNewsRead();
            for (String s : guideAppNewsIdList) {
                sysGuideAppNewsRead = new SysGuideAppNewsRead();
                sysGuideAppNewsRead.setId(IdUtils.getSeqId());
                sysGuideAppNewsRead.setGuideId(Long.parseLong(s));
                sysGuideAppNewsRead.setGuideUserId(Long.parseLong(userId));
                sysGuideAppNewsRead.setCreateTime(DateUtil.currentDateTime());
                ins = sysGuideAppNewsReadMapper.insertSelective(sysGuideAppNewsRead);
            }
        } else {
            return 2;
        }
        return ins;
    }

    /**
     * 后台管理——查询消息列表
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGuideAppNewsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysGuideAppNews> guideAppNews = sysGuideAppNewsMapper.selectBySearch(search);
        if (guideAppNews.size() > 0) {
            PageInfo<SysGuideAppNews> pageInfo = new PageInfo<>(guideAppNews);
            pageDataResult.setData(guideAppNews);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理-添加消息
     *
     * @param sysGuideAppNews
     * @return
     */
    @Override
    public int addSysGuideAppNews(SysGuideAppNews sysGuideAppNews) {

        if ("1".equals(sysGuideAppNews.getMessageType())) {
            List<SysGuideAppUsers> userList = sysGuideAppUsersMapper.selectAllUserId();
            String userIds = "";
            for (SysGuideAppUsers sysGuideAppUsers : userList) {
                if (StringUtils.isEmpty(userIds)) {
                    userIds = sysGuideAppUsers.getUserId().toString();
                } else {
                    userIds = userIds + "," + sysGuideAppUsers.getUserId();
                }
            }
            sysGuideAppNews.setGuideUserId(userIds);

            //定时添加通知消息
            if (!StringUtils.isEmpty(sysGuideAppNews.getPlannedTime())) {
                sysGuideAppNews.setGuideUserId(sysGuideAppNews.getGuideUserId());
                sysGuideAppNews.setGuideId(IdUtils.getSeqId());
                sysGuideAppNews.setCreateDate(DateUtil.currentDateTime());
                sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
                sysGuideAppNews.setSendStatus("2");
                int i = sysGuideAppNewsMapper.insertSelective(sysGuideAppNews);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        sysGuideAppNews.setSendStatus("1");
                        sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
                        int i = sysGuideAppNewsMapper.updateByPrimaryKeySelective(sysGuideAppNews);
                    }
                };
                String plannedTime = sysGuideAppNews.getPlannedTime();
                Date date = null;
                try {
                    date = DateUtil.StringToDateTime(plannedTime);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                timer.schedule(timerTask, date);
                return 1;
            } else {

//                sysGuideAppNews.setGuideUserId(userIds);
                sysGuideAppNews.setGuideId(IdUtils.getSeqId());
                sysGuideAppNews.setCreateDate(DateUtil.currentDateTime());
                sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
                int i = sysGuideAppNewsMapper.insertSelective(sysGuideAppNews);
                return i;
            }
        } else {

            String[] userList = sysGuideAppNews.getUserList();
            String users = "";
            for (String s : userList) {
                if (StringUtils.isEmpty(users)) {
                    users = s;
                } else {
                    users = users + "," + s;
                }
            }
            sysGuideAppNews.setGuideUserId(users);
            if (!StringUtils.isEmpty(sysGuideAppNews.getPlannedTime())) {
                sysGuideAppNews.setGuideUserId(sysGuideAppNews.getGuideUserId());
                sysGuideAppNews.setGuideId(IdUtils.getSeqId());
                sysGuideAppNews.setSendStatus("2");
                sysGuideAppNews.setCreateDate(DateUtil.currentDateTime());
                sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
                int i = sysGuideAppNewsMapper.insertSelective(sysGuideAppNews);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {

                        sysGuideAppNews.setSendStatus("1");
                        sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
                        int i = sysGuideAppNewsMapper.updateByPrimaryKeySelective(sysGuideAppNews);
                    }
                };
                String plannedTime = sysGuideAppNews.getPlannedTime();
                Date date = null;
                try {
                    date = DateUtil.StringToDateTime(plannedTime);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                timer.schedule(timerTask, date);
                return 1;
            } else {
                sysGuideAppNews.setGuideId(IdUtils.getSeqId());
                sysGuideAppNews.setCreateDate(DateUtil.currentDateTime());
                sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
                int i = sysGuideAppNewsMapper.insertSelective(sysGuideAppNews);
                return i;
            }

        }
    }

    /**
     * 修改消息
     *
     * @param sysGuideAppNews
     * @return
     */
    @Override
    public int editSysGuideAppNews(SysGuideAppNews sysGuideAppNews) {


        sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
        int i = sysGuideAppNewsMapper.updateByPrimaryKeySelective(sysGuideAppNews);
        return i;
    }

    /**
     * 删除消息
     *
     * @param guideId
     * @return
     */
    @Override
    public int delSysGuideAppNews(Long guideId) {


        int i = sysGuideAppNewsMapper.deleteByPrimaryKey(guideId);

        return i;
    }


}

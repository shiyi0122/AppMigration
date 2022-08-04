package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicRankingListService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicRankingListMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicRankingList;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/3 18:35
 */
@Service
public class SysScenicRankingListServiceImpl implements SysScenicRankingListService {

    @Autowired
    SysScenicRankingListMapper sysScenicRankingListMapper;



    @Override
    public List<SysScenicRankingList> getRankingList(SearchDTO searchDTO) {

        Map<String, Object> search = new HashMap<>();

        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            search.put("spotId",searchDTO.getSpotId());
        }

        List<SysScenicRankingList> list =  sysScenicRankingListMapper.searchRankingList(search);

        return list;

    }

    /**
     * 添加榜单标签
     * @param sysScenicRankingList
     * @return
     */
    @Override
    public int addRankingList(SysScenicRankingList sysScenicRankingList) {

        sysScenicRankingList.setId(IdUtils.getSeqId());

        sysScenicRankingList.setCreateTime(DateUtil.currentDateTime());

        sysScenicRankingList.setUpdateTime(DateUtil.currentDateTime());

        int i = sysScenicRankingListMapper.insertSelective(sysScenicRankingList);

        return i;
    }
}

package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicDistrictRankingService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicDistrictRankingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicDistrictRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class SysScenicDistrictRankingServiceImpl implements SysScenicDistrictRankingService {
    @Autowired
    private SysScenicDistrictRankingMapper sysScenicDistrictRankingMapper;

    /**
     * 查询景点排行
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicDistrictRanking
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public SysScenicDistrictRanking bestRanking(Map<String, Object> search) {
        return sysScenicDistrictRankingMapper.bestRanking(search);
    }

    /**
     * 插入排行数据
     * @param: ranking
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public int insetbestRanking(SysScenicDistrictRanking ranking) {
        return sysScenicDistrictRankingMapper.insertSelective(ranking);
    }

    /**
     * 更新排行数据
     * @param: rankings
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public int updatebestRanking(SysScenicDistrictRanking rankings) {
        return sysScenicDistrictRankingMapper.updateByPrimaryKeySelective(rankings);
    }

    /**
     * 清空排行数据
     * @return
     */
    @Override
    public int updateRanking() {
       int i = sysScenicDistrictRankingMapper.updateRanking();
       return i;
    }
}

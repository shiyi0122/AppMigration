package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicRankingList;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/8/3 18:34
 */

public interface SysScenicRankingListService {

    List<SysScenicRankingList> getRankingList(SearchDTO searchDTO);


    int addRankingList(SysScenicRankingList sysScenicRankingList);

}

package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicRankingListService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicRankingListMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicRankingList;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Value("${spotHotListPatheGetPicUrl}")
    String SPOT_HOT_LIST_PATHE_GET_PIC_URL;

    @Value("${spotHotListPatheGetPicPaht}")
    String SPOT_HOT_LIST_PATHE_GET_PIC_PAHT;

    /**
     * 榜单列表查询
     * 张
     * @param searchDTO
     * @return
     */

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
    public int addRankingList(MultipartFile file, SysScenicRankingList sysScenicRankingList) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".png") || type.equals(".jpg")){
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = SPOT_HOT_LIST_PATHE_GET_PIC_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            }catch (Exception e){
                e.printStackTrace();
            }
            sysScenicRankingList.setPictureUrl(SPOT_HOT_LIST_PATHE_GET_PIC_URL+ filename);
        }

        sysScenicRankingList.setId(IdUtils.getSeqId());

        sysScenicRankingList.setCreateTime(DateUtil.currentDateTime());

        sysScenicRankingList.setUpdateTime(DateUtil.currentDateTime());

        int i = sysScenicRankingListMapper.insertSelective(sysScenicRankingList);

        return i;
    }
}

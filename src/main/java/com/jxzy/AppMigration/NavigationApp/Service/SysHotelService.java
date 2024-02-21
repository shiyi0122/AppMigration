package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.SysHotel;
import com.jxzy.AppMigration.NavigationApp.entity.SysHotelDetails;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysHotelService {


    int addSysHotel(MultipartFile file, SysHotel sysHotel);

    int editSysHotel(MultipartFile file, SysHotel sysHotel);

    int delSysHotel(Long id);

    PageDataResult getSysHotelList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    PageDataResult getSysHotelAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int addSysHotelN(SysHotel sysHotel);

    int editSysHotelN(SysHotel sysHotel);

    PageDataResult getAllSysHotel(Map<String, Object> search);

    List<SysHotel> getAllSysHotelN(Map<String, Object> search);

    List<SysHotel> uploadExcelHotel(Map<String, Object> search);

    SysHotel getSysHotelIdDetails(Map<String, Object> search);

}
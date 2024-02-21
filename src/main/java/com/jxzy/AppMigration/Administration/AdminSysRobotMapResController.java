package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysRobotMapResService;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/27 10:31
 */
@Api(tags = "后台管理--游小伴地图管理")
@RestController
@RequestMapping("adminSysRobotMapRes")
@CrossOrigin
public class AdminSysRobotMapResController extends PublicUtil {

    @Autowired
    SysRobotMapResService sysRobotMapResService;

    @ApiOperation("后台管理——地图列表查询")
    @GetMapping("/getSysRobotMapResList")
    @ResponseBody
    public PageDataResult getSysRobotMapResList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("spotName",pageDTO.getSpotName());
        pageDataResult = sysRobotMapResService.getSysRobotMapResList(search,pageDTO.getPageNum(),pageDTO.getPageSize());
        return pageDataResult;
    }

    @ApiOperation("后台管理——修改地图启用状态")
    @GetMapping("/editSysRobotMapResState")
    @ResponseBody
    public ReturnModel editSysRobotMapResState(Long id, String state) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysRobotMapResService.editSysRobotMapResState(id,state);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("获取成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("获取失败！");
        }
        return returnModel;
    }


    @ApiOperation("后台管理——删除地图对应资源")
    @GetMapping("/delSysRobotMapRes")
    @ResponseBody
    public ReturnModel delSysRobotMapRes(Long id) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysRobotMapResService.delSysRobotMapRes(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }

    /**
     * 后台管理--添加地图资源
     * @param file
     * @param sysRobotMapRes
     * @return
     */
    @ApiOperation("后台管理--添加地图资源")
    @PostMapping("/addRobotMapRes")
    @ResponseBody
    public ReturnModel addRobotMapRes(@RequestPart("file") MultipartFile file , SysRobotMapRes sysRobotMapRes) {

        ReturnModel dataModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                int i = sysRobotMapResService.addRobotMapRes(file,sysRobotMapRes);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("地图资源新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if(i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("文件上传格式不正确！(支持:zip格式)");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("地图资源新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请上传文件！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("addRobotMapRes",e);
            dataModel.setData("");
            dataModel.setMsg("地图资源新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }





    }

package com.jxzy.AppMigration.Administration;




import com.jxzy.AppMigration.NavigationApp.Service.SysGameService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGame;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
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
 * @Date 2023/1/11 17:19
 * 娱乐设施
 */

@Api(tags = "后台管理-娱乐设施")
@RestController
@RequestMapping("adminSysGame")
@CrossOrigin
public class AdminSysGameController {


    @Autowired
    SysGameService sysGameService;

    /**
     * 添加娱乐设施
     * @param file
     * @param
     * @return
     */

    @ApiOperation("添加娱乐设施")
    @PostMapping("addSysGame")
    @ResponseBody
    public ReturnModel addSysGame(@RequestPart("file") MultipartFile file, SysGame sysGame) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGameService.addSysGame(file, sysGame);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 添加娱乐设施（无文件）
     * @param
     * @param
     * @return
     */

    @ApiOperation("添加娱乐设施(无文件)")
    @PostMapping("addSysGameN")
    @ResponseBody
    public ReturnModel addSysGameN(@RequestBody SysGame sysGame) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGameService.addSysGameN(sysGame);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }

    /**
     * 修改娱乐设施
     * @param file
     * @param
     * @return
     */
    @ApiOperation("修改娱乐设施")
    @PostMapping("editSysGame")
    @ResponseBody
    public ReturnModel editSysGame(@RequestPart("file") MultipartFile file, SysGame sysGame) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGameService.editSysGame(file, sysGame);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }

    /**
     * 修改娱乐设施（无文件）
     * @param
     * @param
     * @return
     */
    @ApiOperation("修改娱乐设施(无文件)")
    @PostMapping("editSysGameN")
    @ResponseBody
    public ReturnModel editSysGameN(@RequestBody SysGame sysGame) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGameService.editSysGameN( sysGame);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 后台管理——删除娱乐设施
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除娱乐设施")
    @GetMapping("delSysGame")
    @ResponseBody
    public ReturnModel delSysGame(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGameService.delSysGame(id);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }

    /**
     * 查询娱乐设施列表
     * @param pageDTO
     * @return
     */
    @ApiOperation("查询娱乐设施列表")
    @GetMapping("getSysGameList")
    @ResponseBody
    public PageDataResult getSysGameList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (pageDTO.getSpotId() != null) {
            search.put("spotId", pageDTO.getSpotId());
        }
        if (pageDTO.getContent() != null) {
            search.put("content", pageDTO.getContent());
        }
        if (pageDTO.getProvince() != null){
            search.put("province",pageDTO.getProvince());
        }
        if (pageDTO.getCity() != null){
            search.put("city",pageDTO.getCity());
        }
        if (pageDTO.getArea() != null){
            search.put("area",pageDTO.getArea());
        }
        if (pageDTO.getIsPeriphery() != null){
            search.put("isPeriphery",pageDTO.getIsPeriphery());
        }
        pageDataResult = sysGameService.getSysGameList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }




}

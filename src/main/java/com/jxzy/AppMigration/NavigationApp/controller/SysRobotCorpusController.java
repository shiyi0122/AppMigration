package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysRobotCorpusService;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotCorpus;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/9/7 19:17
 */
@Api(tags = "游小伴语音交互相关接口")
@CrossOrigin
@RestController
@RequestMapping("sysRobotCorpus")
public class SysRobotCorpusController {

    @Autowired
    SysRobotCorpusService sysRobotCorpusService;


    @ApiOperation("语音交互查询")
    @GetMapping("getSysRobotCorpus")
    @ResponseBody
    public ReturnModel getSysRobotCorpus(SearchDTO searchDTO) {

        ReturnModel returnModel = new ReturnModel();

        List<SysRobotCorpus> sysRobotCorpus = sysRobotCorpusService.getSysRobotCorpus(searchDTO.getName());
        if ( sysRobotCorpus.size()==0 ){
            returnModel.setData("抱歉，您的问题我回答不了，换个问题问吧！");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("没有对应回复语句");
            return returnModel;
        }else{
            returnModel.setData(sysRobotCorpus.get(0).getCorpusAnswer());
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("查询成功！");
            return returnModel;
        }

    }
}

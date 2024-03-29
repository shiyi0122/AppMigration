package com.jxzy.AppMigration.NavigationApp.controller;

import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Api(tags = "导览APP消息、反馈、关于相关")
@RestController
@RequestMapping("text")
public class TextRelatedController extends PublicUtil {
    @Autowired
    private SysGuideAppNewsService sysGuideAppNewsService;
    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;
    @Autowired
    private SysGuideAppUsersFeedbackService sysGuideAppUsersFeedbackService;
    @Autowired
    private SysGuideAppUsersHelpService sysGuideAppUsersHelpService;
    @Autowired
    private SysGuideAppAgreementService sysGuideAppAgreementService;
    @Autowired
    private SysAboutUsService sysAboutUsService;

    /**
     * 查询消息列表
     *
     * @param:
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    @ApiOperation("查询消息列表")
    @GetMapping("/queryGuideAppNewsLists")
    public ReturnModel queryGuideAppNewsLists(@ApiParam(name = "longinTokenId", value = "登录令牌,状态码202为登录失效", required = true) String longinTokenId,
                                              @ApiParam(name = "guideTitle", value = "传参按条件查询，不传参则查询全部", required = false) String guideTitle,
                                              @ApiParam(name = "pageNum", value = "当前页,输入0不分页", required = true) int pageNum,
                                              @ApiParam(name = "pageSize", value = "总条数,输入0不分页", required = true) int pageSize,
                                              @ApiParam(name="baseDTO",value="登录令牌",required=true) BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
            search.put("guideTitle", guideTitle);
            List<SysGuideAppNews> news = sysGuideAppNewsService.queryGuideAppNewsListsLimit(pageNum, pageSize, search);
            //PageInfo就是一个分页Bean
            PageInfo pageInfo = new PageInfo(news);
            returnModel.setData(pageInfo);
            returnModel.setMsg("成功获取消息列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("queryGuideAppNewsLists", e);
            returnModel.setData("");
            returnModel.setMsg("获取消息列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 根据ID删除消息、批量清除未读、修改已读
     *
     * @param: guideId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    @ApiOperation("根据ID删除消息(假删除)或更新已读状态、批量清除未读")
    @PostMapping("/deleteGuideAppNews")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longinTokenId", value = "登录令牌,状态码202为登录失效", dataType = "string", required = true),
            @ApiImplicitParam(name = "guideId", value = "消息主键ID,不传为清除未读", dataType = "string", required = false),
            @ApiImplicitParam(name = "type", value = "type=2代表删除,type=1代表已读,type=0清除未读", dataType = "string", required = true),
            @ApiImplicitParam(name="baseDTO",value="登录令牌",required=true)})
    public ReturnModel deleteGuideAppNews(String longinTokenId, String guideId, String type, BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
            int update = sysGuideAppNewsService.deleteGuideAppNews(guideId, type);
            if (update > 0 && type.equals("2")) {
                returnModel.setData("");
                returnModel.setMsg("成功删除消息！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (update > 0 && type.equals("1")) {
                returnModel.setData("");
                returnModel.setMsg("已读消息！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (update > 0 && type.equals("0")) {
                returnModel.setData("");
                returnModel.setMsg("已清除未读！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("操作失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("deleteGuideAppNews", e);
            returnModel.setData("");
            returnModel.setMsg("删除消息失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 用户反馈
     *
     * @param: longinTokenId
     * @param: feedbackContent
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    @ApiOperation("用户反馈")
    @PostMapping("/insetUsersFeedback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longinTokenId", value = "登录令牌,状态码202为登录失效", dataType = "string", required = true),
            @ApiImplicitParam(name = "feedbackContent", value = "反馈内容", dataType = "string", required = true),
            @ApiImplicitParam(name="baseDTO",value="登录令牌",required=true)})
    public ReturnModel insetUsersFeedback(String longinTokenId, String feedbackContent,BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
            int inset = sysGuideAppUsersFeedbackService.insetUsersFeedback(user.getUserId(), feedbackContent);
            if (inset > 0) {
                returnModel.setData("");
                returnModel.setMsg("反馈成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("反馈失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("insetUsersFeedback", e);
            returnModel.setData("");
            returnModel.setMsg("反馈失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 使用帮助
     * 张
     * @param longinTokenId
     * @return
     */
    @CrossOrigin
    @ApiOperation("使用帮助（暂不使用）")
    @GetMapping("/queryUserHelpList")
    public ReturnModel queryUserHelpList(@ApiParam(name = "longinTokenId", value = "登录令牌,状态码202为登录失效", required = true)
                                                 String longinTokenId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
            List<SysGuideAppUsersHelp> help = sysGuideAppUsersHelpService.queryUserHelpList();
            returnModel.setData(help);
            returnModel.setMsg("成功获取使用帮助列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("queryUserHelpList", e);
            returnModel.setData("");
            returnModel.setMsg("获取失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @CrossOrigin
    @ApiOperation("使用协议")
    @GetMapping("/getSysGuideAppAgreement")
    public ReturnModel getSysGuideAppAgreement(String type) {

        ReturnModel returnModel = new ReturnModel();
        SysAboutUs sysAboutUs = sysAboutUsService.getSysUserFans(type);

        returnModel.setData(sysAboutUs);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;

    }


    @CrossOrigin
    @ApiOperation("使用协议(新)")
    @GetMapping("/getSysGuideAppAgreementTwo")
    public ReturnModel getSysGuideAppAgreementTwo(String type,String subversionId) {

        ReturnModel returnModel = new ReturnModel();

        SysAboutUs sysAboutUs = sysAboutUsService.getSysUserFansNew(type,subversionId);

        returnModel.setData(sysAboutUs);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;

    }


//    @CrossOrigin
//    @ApiOperation("使用协议(奥森)")
//    @GetMapping("/getSysGuideAppAgreementAs")
//    public ReturnModel getSysGuideAppAgreementAs(String type,String subversionId) {
//
//        ReturnModel returnModel = new ReturnModel();
//
//        SysAboutUs sysAboutUs = sysAboutUsService.getSysUserFansNew(type,subversionId);
//
//        returnModel.setData(sysAboutUs);
//        returnModel.setState(Constant.STATE_SUCCESS);
//        returnModel.setMsg("查询成功！");
//        return returnModel;
//
//    }

    /**
     *
     *  zhang
     * @param
     * @return
     */
    @ApiOperation("查询通知消息")
    @GetMapping("/getSysGuideAppNews")
    @ResponseBody
    public ReturnModel getSysGuideAppNews(PageDTO pageDTO) {
        ReturnModel returnModel = new ReturnModel();
        HashMap<String, Object> search = new HashMap<>();

        search.put("userId", pageDTO.getUid());
        if(!StringUtils.isEmpty(pageDTO.getGuideTitle())){
            search.put("guideTitle",pageDTO.getGuideTitle());
        }
        List<SysGuideAppNews> sysGuideAppNews = sysGuideAppNewsService.queryGuideAppNewsListsLimit(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        returnModel.setData(sysGuideAppNews);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;

    }


    /**
     *
     *  zhang
     * @param
     * @return
     */
    @ApiOperation("读取通知消息")
    @GetMapping("/getSysGuideAppNewsRead")
    @ResponseBody
    public ReturnModel getSysGuideAppNewsRead(BaseDataDTO dataDTO) {

        ReturnModel returnModel = new ReturnModel();
        SysGuideAppNews sysGuideAppNews = sysGuideAppNewsService.getSysGuideAppNewsRead(dataDTO.getUid(),dataDTO.getId());
        returnModel.setData(sysGuideAppNews);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("读取成功！");
        return returnModel;

    }

    /**
     *
     *  zhang
     * @param
     * @return
     */
    @ApiOperation("全部已读")
    @GetMapping("/allRead")
    @ResponseBody
    public ReturnModel allRead(BaseDataDTO dataDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppNewsService.allRead(dataDTO.getUid());

        if (i == 2){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("没有未读消息");
        }else if (i == 1){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setState("全部已读成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setState("全部已读失败！");
        }
        return returnModel;

    }


}
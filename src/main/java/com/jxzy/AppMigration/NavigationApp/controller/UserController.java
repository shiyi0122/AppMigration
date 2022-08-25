package com.jxzy.AppMigration.NavigationApp.controller;


import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.entity.SysConfigs;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Api(tags = "游小伴用户相关")
@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController extends PublicUtil {

    private static final java.util.UUID UUID = null;
    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;
    @Autowired
    private SysGuideAppUsersFeedbacksService sysGuideAppUsersFeedbacksService;
    @Autowired
    private SysGuideAppUsersHelpService sysGuideAppUsersHelpService;
    @Autowired
    private SysConfigsService sysConfigsService;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址
    @Value("${UPLOAD_PIC}")
    private String UPLOAD_PIC;//头像上传地址
    @Value("${FEEDBACK_PIC}")
    private String FEEDBACK_PIC;//用户反馈上传图片地址

    @Value("${userHeadPortraitPatheGetPicUrl}")
    private String USER_HEAD_PORTRAIT_PATHE_GET_PIC_URL;//用户头像上传路径
    @Value("${userHeadPortraitGetPicPaht}")
    private String USER_HEAD_PORTRAIT_GET_PICPAHT;//用户头像上传路径

    @Value("${userFeedbackPatheGetPicUrl}")
    private String USER_FEEDBACK_PATHE_GET_PIC_URL ;//意见反馈上传路径
    @Value("${userFeedbackGetPicPaht}")
    private String USER_FEEDBACK_GET_PIC_PAHT; //意见反馈上传路径


    /**
     * 用户意见反馈
     * @param: file
     * @param: request
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    //@ApiOperation("用户意见反馈")
    @PostMapping(value ="/userFeedbacks")
    public ReturnModel userFeedback(@RequestPart("file") MultipartFile file, HttpServletRequest request){
        ReturnModel returnModel = new ReturnModel();
        if(!file.isEmpty()){
//            String uploadPath = FEEDBACK_PIC;
            String uploadPath = USER_FEEDBACK_GET_PIC_PAHT;
            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            //重新随机生成名字
            String filename = UUID.randomUUID().toString() +suffixName;//重命名
            File localFile = new File(uploadPath+"\\"+filename);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                //这里应该把filename保存到数据库,供前端访问时使用
                SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
                user.setId(IdUtils.getSeqId());
                user.setUserId(Long.parseLong(request.getParameter("userId")));
                user.setContent(request.getParameter("content"));
//                user.setUrlPic("static/uploadPIC/"+filename);
                user.setUrlPic(USER_FEEDBACK_PATHE_GET_PIC_URL+filename);
                user.setCreateTime(DateUtil.currentDateTime());
                user.setUpdateTime(DateUtil.currentDateTime());
                sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
                returnModel.setData("");
                returnModel.setMsg("反馈成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }catch (IOException e){
                logger.info("upload",e);
                returnModel.setData("");
                returnModel.setMsg("上传头像异常");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }else{
            SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
            user.setId(IdUtils.getSeqId());
            user.setUserId(Long.parseLong(request.getParameter("userId")));
            user.setContent(request.getParameter("content"));
            user.setUrlPic("");
            user.setCreateTime(DateUtil.currentDateTime());
            user.setUpdateTime(DateUtil.currentDateTime());
            sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
            returnModel.setData("");
            returnModel.setMsg("反馈成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }
    }
    /**
     * 用户意见反馈
     * @param: file
     * @param: request
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/23 0023
     */
    @ApiOperation("用户意见反馈")
    @PostMapping(value ="/userFeedback")
    public ReturnModel userFeedback(@RequestPart("file") MultipartFile[] file, HttpServletRequest request,BaseDTO baseDTO){
        ReturnModel returnModel = new ReturnModel();
        String filename = null;
        if(file!=null&&file.length>0){
//            String uploadPath = FEEDBACK_PIC;
            String uploadPath = USER_FEEDBACK_GET_PIC_PAHT;
            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            try {
                //循环获取file数组中得文件
                for(int i = 0;i<file.length;i++){
                    MultipartFile files = file[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() +suffixName;//重命名
                    File localFile = new File(uploadPath+"\\"+filename);
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                //这里应该把filename保存到数据库,供前端访问时使用
                SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
                user.setId(IdUtils.getSeqId());
                user.setUserId(Long.parseLong(request.getParameter("userId")));
                user.setContent(request.getParameter("content"));
//                user.setUrlPic("static/uploadPIC/"+filename);
                user.setUrlPic(USER_FEEDBACK_PATHE_GET_PIC_URL+filename);
                user.setCreateTime(DateUtil.currentDateTime());
                user.setUpdateTime(DateUtil.currentDateTime());
                sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
                returnModel.setData("");
                returnModel.setMsg("反馈成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }catch (IOException e){
                logger.info("upload",e);
                returnModel.setData("");
                returnModel.setMsg("上传头像异常");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }else{
            SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
            user.setId(IdUtils.getSeqId());
            user.setUserId(Long.parseLong(request.getParameter("userId")));
            user.setContent(request.getParameter("content"));
            user.setUrlPic("");
            user.setCreateTime(DateUtil.currentDateTime());
            user.setUpdateTime(DateUtil.currentDateTime());
            sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
            returnModel.setData("");
            returnModel.setMsg("反馈成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }
    }

    private boolean saveFile(MultipartFile file, String path) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                File filepath = new File(path);
                if (!filepath.exists())
                    filepath.mkdirs();
                // 文件保存路径
                String savePath = path + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(savePath));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 使用帮助
     * @param: longinTokenId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    @ApiOperation("使用帮助")
    @GetMapping("/queryUserHelpList")
    public ReturnModel queryUserHelpList(@ApiParam(name = "longinTokenId", value = "登录令牌,状态码202为登录失效", required = true)
                                                 String longinTokenId,
                                        @ApiParam(name = "helpId", value = "使用帮助ID", required = false)
                                                String helpId,
                                         @ApiParam(name = "helpTitle", value = "使用帮助搜索", required = false)
                                                     String helpTitle,
                                         @ApiParam(name="baseDTO",value="登录令牌",required=true)BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
//            if (user == null) {
//                returnModel.setData("");
//                returnModel.setMsg("令牌失效，请重新登录！");
//                returnModel.setState(Constant.LOGIN_FAILURE);
//                return returnModel;
//            }
            if (helpTitle != null) {
                List<SysGuideAppUsersHelp> help = sysGuideAppUsersHelpService.queryUserHelpData(helpTitle);
                returnModel.setData(help);
                returnModel.setMsg("成功获取使用帮助数据！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                if (user != null) {
                    List<SysGuideAppUsersHelp> help = sysGuideAppUsersHelpService.queryUserHelpList();
                    returnModel.setData(help);
                    returnModel.setMsg("成功获取使用帮助列表！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else {
                    SysGuideAppUsersHelp help = sysGuideAppUsersHelpService.queryUserHelp(Long.parseLong(helpId));
                    returnModel.setData(help);
                    returnModel.setMsg("成功获取使用帮助列表！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }
            }
        } catch (Exception e) {
            logger.info("queryUserHelpList", e);
            returnModel.setData("");
            returnModel.setMsg("获取失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 关于我们
     * @param: longinTokenId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    @ApiOperation("关于我们")
    @GetMapping("/queryAboutUs")
    public ReturnModel queryAboutUs(@ApiParam(name = "longinTokenId", value = "登录令牌,状态码202为登录失效", required = true)
                                                 String longinTokenId,
                                    @ApiParam(name = "type", value = "1、关于我们，2、用户协议和隐私协议", required = true)
                                            String type,
                                    @ApiParam(name="baseDTO",value="登录令牌",required=true)BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        try {
//            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
//            if (user == null) {
//                returnModel.setData("");
//                returnModel.setMsg("令牌失效，请重新登录！");
//                returnModel.setState(Constant.LOGIN_FAILURE);
//                return returnModel;
//            }
            if ("1".equals(type)) {
                SysConfigs help = sysConfigsService.queryAboutUs(Long.parseLong("91711212312291"));
                returnModel.setData(help);
                returnModel.setMsg("成功获取！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if ("2".equals(type)) {
                SysConfigs help = sysConfigsService.queryAboutUs(Long.parseLong("72703530271251"));
                returnModel.setData(help);
                returnModel.setMsg("成功获取！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
            return returnModel;
        } catch (Exception e) {
            logger.info("queryAboutUs", e);
            returnModel.setData("");
            returnModel.setMsg("获取失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 上传用户头像
     * @param: file
     * @param: userId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    @ApiOperation("上传用户头像")
    @PostMapping(value ="/uploadPic")
    public ReturnModel upload(@RequestPart("file") MultipartFile file, HttpServletRequest request,BaseDTO baseDTO){
        ReturnModel returnModel = new ReturnModel();
        SysGuideAppUsers user = new SysGuideAppUsers();
        if(!file.isEmpty()){
//            String uploadPath = UPLOAD_PIC;
            String uploadPath = USER_HEAD_PORTRAIT_GET_PICPAHT;
            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            //重新随机生成名字
            String filename = UUID.randomUUID().toString() +suffixName;//重命名
            File localFile = new File(uploadPath+"\\"+filename);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
//                file.transferTo(localFile); //把上传的文件保存至本地
                //这里应该把filename保存到数据库,供前端访问时使用
                String userName = request.getParameter("userName");
                String userPhone = request.getParameter("userPhone");
                String userGender = request.getParameter("userGender");
                String userId = request.getParameter("userId");
                if (userId == null) {
                    returnModel.setData("");
                    returnModel.setMsg("参数不能为空！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
                user.setUserId(Long.parseLong(userId));
//                user.setPortraitPic("static/uploadPIC/"+filename);
                user.setPortraitPic(USER_HEAD_PORTRAIT_PATHE_GET_PIC_URL+filename);
                user.setUserPhone(userPhone);
                user.setUserName(userName);
                user.setUserGender(userGender);
                sysGuideAppUsersService.updateAppUsers(user);
                returnModel.setData(user);
                returnModel.setMsg("更新成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }catch (IOException e){
                logger.info("upload",e);
                returnModel.setData("");
                returnModel.setMsg("上传头像异常");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }else{
            String userName = request.getParameter("userName");
            String userPhone = request.getParameter("userPhone");
            String userGender = request.getParameter("userGender");
            String userId = request.getParameter("userId");
            user.setUserId(Long.parseLong(userId));
            user.setUserPhone(userPhone);
            user.setUserName(userName);
            user.setUserGender(userGender);
            sysGuideAppUsersService.updateAppUsers(user);
            returnModel.setData(user);
            returnModel.setMsg("更新成功！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    /**
     * 根据用户id获取用户详细信息
     * @param baseDTO
     * @return
     */
    @ApiOperation("用户个人信息")
    @GetMapping("/userDetails")
    public ReturnModel userDetails(BaseDTO baseDTO){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(baseDTO.getUid())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("用户id为空！");
        }
        SysGuideAppUsers sysGuideAppUsers =  sysGuideAppUsersService.userDetails(baseDTO.getUid());
        returnModel.setData(sysGuideAppUsers);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }






}

package com.jxzy.AppMigration.NavigationApp.controller;


import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserOperationLogDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Api(tags = "游娱go用户相关")
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

    @Autowired
    private SysStrategyService sysStrategyService;
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
    private String USER_FEEDBACK_PATHE_GET_PIC_URL;//意见反馈上传路径
    @Value("${userFeedbackGetPicPaht}")
    private String USER_FEEDBACK_GET_PIC_PAHT; //意见反馈上传路径


    /**
     * 用户意见反馈
     *
     * @param: file
     * @param: request
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    //@ApiOperation("用户意见反馈")
    @PostMapping(value = "/userFeedback")
    public ReturnModel userFeedback(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        ReturnModel returnModel = new ReturnModel();
        if (!file.isEmpty()) {
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
            String filename = UUID.randomUUID().toString() + suffixName;//重命名
            File localFile = new File(uploadPath + "\\" + filename);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                //这里应该把filename保存到数据库,供前端访问时使用
                SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
                user.setId(IdUtils.getSeqId());
                user.setUserId(Long.parseLong(request.getParameter("userId")));
                user.setContent(request.getParameter("content"));
//                user.setUrlPic("static/uploadPIC/"+filename);
                user.setUrlPic(USER_FEEDBACK_PATHE_GET_PIC_URL + filename);
                user.setCreateTime(DateUtil.currentDateTime());
                user.setUpdateTime(DateUtil.currentDateTime());
                sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
                returnModel.setData("");
                returnModel.setMsg("反馈成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } catch (IOException e) {
                logger.info("upload", e);
                returnModel.setData("");
                returnModel.setMsg("上传头像异常");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } else {
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
     *
     * @param: file
     * @param: request
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/23 0023
     */
    @ApiOperation("用户意见反馈(有图片上传)")
    @PostMapping(value = "/userFeedbacks")
    public ReturnModel userFeedback(@RequestPart("file") MultipartFile[] file, HttpServletRequest request, BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        String filename = null;
        String url = null;
        String uid = request.getParameter("uid");
        String content = request.getParameter("content");
        if (file != null && file.length > 0) {
//            String uploadPath = FEEDBACK_PIC;
            String uploadPath = USER_FEEDBACK_PATHE_GET_PIC_URL;

            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            try {
                //循环获取file数组中得文件
                for (int i = 0; i < file.length; i++) {
                    MultipartFile files = file[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() + suffixName;//重命名
                    File localFile = new File(uploadPath + filename);
                    if (StringUtils.isEmpty(url)) {
                        url = uploadPath + filename;
                    } else {
                        url = url + "," + uploadPath + filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                //这里应该把filename保存到数据库,供前端访问时使用
                SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
                user.setId(IdUtils.getSeqId());
                user.setUserId(Long.parseLong(uid));
                user.setContent(content);
//                user.setUrlPic("static/uploadPIC/"+filename);
                user.setUrlPic(url);
                user.setCreateTime(DateUtil.currentDateTime());
                user.setUpdateTime(DateUtil.currentDateTime());
                sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
                returnModel.setData("");
                returnModel.setMsg("反馈成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } catch (IOException e) {
                logger.info("upload", e);
                returnModel.setData("");
                returnModel.setMsg("上传头像异常");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } else {
            SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
            user.setId(IdUtils.getSeqId());
            user.setUserId(Long.parseLong(uid));
            user.setContent(content);
            user.setUrlPic("");
            user.setCreateTime(DateUtil.currentDateTime());
            user.setUpdateTime(DateUtil.currentDateTime());
            sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
            returnModel.setData(user.getId());
            returnModel.setMsg("反馈成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }
    }


    @ApiOperation("用户意见反馈上传(无图片上传)")
    @PostMapping(value = "/userFeedbackPicture")
    public ReturnModel userFeedback(HttpServletRequest request, String content, BaseDTO baseDTO) {

        ReturnModel returnModel = new ReturnModel();
        SysGuideAppUsersFeedbacks user = new SysGuideAppUsersFeedbacks();
        user.setId(IdUtils.getSeqId());
        user.setUserId(Long.parseLong(baseDTO.getUid()));
        user.setContent(content);
        user.setUrlPic("");
        user.setCreateTime(DateUtil.currentDateTime());
        user.setUpdateTime(DateUtil.currentDateTime());
        sysGuideAppUsersFeedbacksService.insetUserFeedback(user);
        returnModel.setData(user.getId());
        returnModel.setMsg("反馈成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;


    }

//    @ApiOperation("用户意见反馈多图片上传")
//    @PostMapping(value ="/springUpload")
//    public String springUpload(HttpServletRequest request) throws IllegalStateException, IOException
//    {
//
//        String uploadPath = USER_FEEDBACK_GET_PIC_PAHT;
//        String filename = null;
//        filename = UUID.randomUUID().toString();//重命名
//        long startTime=System.currentTimeMillis();
//
////将当前上下文初始化给 CommonsMutipartResolver (多部分解析器)
//
//        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
//
//                request.getSession().getServletContext());
//
////检查form中是否有enctype="multipart/form-data"
//
//        if(multipartResolver.isMultipart(request))
//        {
//
////将request变成多部分request
//
//            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
//
////获取multiRequest 中所有的文件名
//
//            Iterator iter=multiRequest.getFileNames();
//
//            while(iter.hasNext())
//            {
////一次遍历所有文件
//                MultipartFile file=multiRequest.getFile(iter.next().toString());
//                if(file!=null)
//                {
//                    String path = uploadPath + filename;
////上传
//                    file.transferTo(new File(path));
//                }
//
//            }
//
//        }
//
//        long endTime=System.currentTimeMillis();
//
//        System.out.println("Spring方法的运行时间："+String.valueOf(endTime-startTime)+"ms");
//
//        return "/success";
//
//    }

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
     *
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
                                         @ApiParam(name = "baseDTO", value = "登录令牌", required = true) BaseDTO baseDTO) {
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
            } else {
                if (user != null) {
                    List<SysGuideAppUsersHelp> help = sysGuideAppUsersHelpService.queryUserHelpList();
                    returnModel.setData(help);
                    returnModel.setMsg("成功获取使用帮助列表！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else {
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
     *
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
                                    @ApiParam(name = "baseDTO", value = "登录令牌", required = true) BaseDTO baseDTO) {
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
            } else if ("2".equals(type)) {
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
     *
     * @param: file
     * @param: userId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    @ApiOperation("上传用户头像")
    @PostMapping(value = "/uploadPic")
    public ReturnModel upload(@RequestPart("file") MultipartFile file, HttpServletRequest request, BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        SysGuideAppUsers user = new SysGuideAppUsers();
        if (!file.isEmpty()) {
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
            String filename = UUID.randomUUID().toString() + suffixName;//重命名
            File localFile = new File(uploadPath + filename);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
//                file.transferTo(localFile); //把上传的文件保存至本地
                //这里应该把filename保存到数据库,供前端访问时使用
                String userName = request.getParameter("userName");
                String userPhone = request.getParameter("userPhone");
                String userGender = request.getParameter("userGender");
                String userId = request.getParameter("userId");
                String introduce = request.getParameter("introduce");
                if (userId == null) {
                    returnModel.setData("");
                    returnModel.setMsg("参数不能为空！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
                user.setUserId(Long.parseLong(userId));
//                user.setPortraitPic("static/uploadPIC/"+filename);
                user.setPortraitPic(USER_HEAD_PORTRAIT_PATHE_GET_PIC_URL + filename);
                user.setUserPhone(userPhone);
                user.setUserName(userName);
                user.setUserGender(userGender);
                user.setIntroduce(introduce);
                sysGuideAppUsersService.updateAppUsers(user);
                returnModel.setData(user);
                returnModel.setMsg("更新成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } catch (IOException e) {
                logger.info("upload", e);
                returnModel.setData("");
                returnModel.setMsg("上传头像异常");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } else {
            String userName = request.getParameter("userName");
            String userPhone = request.getParameter("userPhone");
            String userGender = request.getParameter("userGender");
            String userId = request.getParameter("userId");
            String introduce = request.getParameter("introduce");
            user.setUserId(Long.parseLong(userId));
            user.setUserPhone(userPhone);
            user.setUserName(userName);
            user.setUserGender(userGender);
            user.setIntroduce(introduce);
            sysGuideAppUsersService.updateAppUsers(user);
            returnModel.setData(user);
            returnModel.setMsg("更新成功！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 根据用户id获取用户详细信息
     *
     * @param baseDTO
     * @return
     */
    @ApiOperation("用户个人信息")
    @GetMapping("/userDetails")
    public ReturnModel userDetails(BaseDTO baseDTO) {

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(baseDTO.getUid())) {
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("用户id为空！");
        }
        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersService.userDetails(baseDTO.getUid());
        returnModel.setData(sysGuideAppUsers);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }

    /**
     * 根据用户id获取用户详细信息
     *
     * @param baseDTO
     * @return
     */
    @ApiOperation("用户来访次数相加")
    @GetMapping("/addUserVisit")
    public ReturnModel addUserVisit(BaseDTO baseDTO) {

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(baseDTO.getUid())) {
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("用户id为空！");
        }
        int i = sysGuideAppUsersService.addUserVisit(baseDTO.getUid());
        returnModel.setData(i);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }


    @ApiOperation("我的收藏")
    @GetMapping("/myCollection")
    public PageDataResult myCollection(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysStrategyService.myCollection(pageDTO.getUid(), pageDTO.getType(), pageDTO.getPageNum(), pageDTO.getPageSize());
        return pageDataResult;

    }


    @ApiOperation("我的喜欢")
    @GetMapping("/myLike")
    public PageDataResult myLike(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysStrategyService.myLike(pageDTO.getUid(), pageDTO.getType(), pageDTO.getPageNum(), pageDTO.getPageSize());

        return pageDataResult;
    }


    @ApiOperation("我的攻略,游记,广场,相册")
    @GetMapping("/myIntroduction")
    public PageDataResult myIntroduction(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysStrategyService.myIntroduction(pageDTO.getUid(), pageDTO.getType(), pageDTO.getPageNum(), pageDTO.getPageSize());

        return pageDataResult;
    }


    @ApiOperation("根据用户id,获取个推id")
    @RequestMapping(value = "/getUserIdByUserClientGtId.do", method = RequestMethod.POST)
    @ResponseBody
    public String getUserIdByUserClientGtId(String userId) {
        Map<String, Object> map = new HashMap<>();

        String userClientGtId = sysGuideAppUsersService.getUserIdByUserClientGtId(userId);
        map.put("userClientGtId", userClientGtId);
        map.put("code", "200");
        String s = JsonUtils.toString(map);
//        JSONObject jsonObject = JSON.parseObject(s);
//        Object code = jsonObject.get("code");
//        List<SysScenicSpot> scenicSpotList =(List<SysScenicSpot>) jsonObject.get("scenicSpotList");
//        System.out.println(code);
        return s;
    }

    @ApiOperation("查询用户操作记录")
    @PostMapping("getUserOperation")
    public ReturnModel getUserOperation(@RequestBody UserOperationLogDTO userOperationLogDTO) {
        ReturnModel returnModel = new ReturnModel();
        List<UserOperationLog> userOperation = sysStrategyService.getUserOperation(userOperationLogDTO);
        returnModel.setData(userOperation);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }

    @ApiOperation("统计用户操作记录")
    @PostMapping("getUserOperationAll")
    public ReturnModel getUserOperationAll(@RequestBody UserOperationLogDTO userOperationLogDTO) {
        ReturnModel returnModel = new ReturnModel();
        List<UserOperationLog> userOperation = sysStrategyService.getUserOperationAll(userOperationLogDTO);
        returnModel.setData(userOperation);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }

    @ApiOperation("新增用户操作日志")
    @PostMapping("addUserOperation")
    public ReturnModel addUserOperation(@RequestBody UserOperationLogDTO userOperationLogDTO) {
        ReturnModel returnModel = new ReturnModel();
        if (userOperationLogDTO == null) {
            returnModel.setData("");
            returnModel.setMsg("新增失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        int i = sysStrategyService.addUserOperation(userOperationLogDTO);
        if (i != 0) {
            returnModel.setData("");
            returnModel.setMsg("新增成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("新增失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


}
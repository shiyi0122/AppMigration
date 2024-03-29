package com.jxzy.AppMigration.NavigationApp.util;

/**
 * @ClassName Constant
 * @Description 设置返回状态
 * @Author 曲绍备
 * @Date 2020/4/26 14:58
 * @Version 1.0
 **/
public class Constant {
    //============================================返回状态(状态不可重复)===============================================//

    //成功状态
    public static final String STATE_SUCCESS = "200";

    //失败状态
    public static final String STATE_FAILURE = "400";

    //失败状态
    public static final String STATE_NORMAL = "201";
    //登录失效
    public static final String LOGIN_FAILURE = "202";

    //==================================================个推推送TYPE==================================================//
    //结束服务
    public static final String END_THE_JOURNEY = "503";
    //闲置状态
    public static final String ROBOT_IDLE_STATE = "504";
    //管理员解锁机器人
    public static final String ADMINISTRATOR_UNLOCKING_ROBOT = "505";
    //管理员锁定机器人
    public static final String ADMINISTRATOR_LOCKS_ROBOT = "506";
    //解除锁定
    public static final String ROBOT_UNLOCKING = "507";
    //VIP解锁
    public static final String VIP_UNLOCKING = "508";
    //运营人员维护
    public static final String OPERATOR_MAINTENANCE = "509";

    /**
     * 不支持上传的文件类型
     */
    public static final int UN_SUPPORT_PICTURE_TYPE = -2;
    /**
     * 不支持上传的文件类型
     */
    public static final String UN_SUPPORT_PICTURE_TYPE_STR="不支持的文件类型";
    /**
     *上传的文件内容为空
     */
    public static final int PIC_IS_EMPTY = -3;
    /**
     *上传的文件内容为空
     */
    public static final String PIC_IS_EMPTY_STR = "文件内容为空";

    /**
     * 成功上传到OSS
     */
    public static final int SAVE_TO_OSS_SUCCESSFUL = 1;
    /**
     * 不支持的文件大小
     */
    public static final int UN_SUPPORT_PIC_SIZE = -4;
    /**
     * 不支持的文件大小
     */
    public static final String UN_SUPPORT_PIC_SIZE_STR = "不支持的文件大小";

}

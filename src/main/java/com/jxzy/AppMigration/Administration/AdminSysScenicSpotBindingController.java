package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBindingService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

import java.util.List;

import static com.jxzy.AppMigration.common.utils.DateUtil.logger;

/**
 * @Author zhang
 * @Date 2023/1/13 13:14
 * 归属地
 */

@Api(tags = "后台管理-城市区域")
@RestController
@RequestMapping("adminSysScenicSpotBinding")
@CrossOrigin
public class AdminSysScenicSpotBindingController {

    @Autowired
    SysScenicSpotBindingService sysScenicSpotBindingService;

    /**
     * @Author
     * @Description 新增景区归属地(省)
     * @Date 0:19 2020/5/24
     * @Param [sysScenicSpotBinding]
     * @return
     **/
    @ApiOperation("添加归属地")
    @RequestMapping("/addScenicSpotBinding")
    @ResponseBody
    public ReturnModel addScenicSpotBinding(@RequestBody SysScenicSpotBinding sysScenicSpotBinding){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
//            if (!file.isEmpty()){
                int i = sysScenicSpotBindingService.addScenicSpotBinding(sysScenicSpotBinding);
                if (i == 1){
                    returnModel.setData("");
                    returnModel.setMsg("景区归属地新增成功");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if(i == 0){
                    returnModel.setData("");
                    returnModel.setMsg("景区归属地添加失败或者已有该归属地！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
//                else if (i==3){
//                    returnModel.setData("");
//                    returnModel.setMsg("图片已大于3M,请更换图片");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                }else{
//                    returnModel.setData("");
//                    returnModel.setMsg("图片分辨率不在1120*600,请更换图片");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                }
//            }else{
//                returnModel.setData("");
//                returnModel.setMsg("请选择要上传的图片！");
//                returnModel.setState(Constant.STATE_FAILURE);
//                return returnModel;
//            }

        }catch (Exception e){
            logger.info("addScenicSpotBinding",e);
            returnModel.setData("");
            returnModel.setMsg("景区归属地新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }


    /**
     * @Author
     * @Description 编辑景区归属地
     * @Date 0:37 2020/5/24
     * @Param [sysScenicSpotBinding]
     * @return
     **/
    @ApiOperation("修改归属地")
    @RequestMapping("/editScenicSpotBinding")
    @ResponseBody
    public ReturnModel editScenicSpotBinding( @RequestBody SysScenicSpotBinding sysScenicSpotBinding){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysScenicSpotBindingService.editScenicSpotBinding(sysScenicSpotBinding);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区归属地修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("景区归属地修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
//            else if (i==3){
//                returnModel.setData("");
//                returnModel.setMsg("图片已大于3M,请更换图片");
//                returnModel.setState(Constant.STATE_FAILURE);
//                return returnModel;
//            }else{
//                returnModel.setData("");
//                returnModel.setMsg("图片分辨率不在1120*600,请更换图片");
//                returnModel.setState(Constant.STATE_FAILURE);
//                return returnModel;
//            }
        }catch (Exception e){
            logger.info("editScenicSpotBinding",e);
            returnModel.setData("");
            returnModel.setMsg("景区归属地修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }


    /**
     * @Author
     * @Description 删除景区归属地
     * @Date 0:40 2020/5/24
     * @Param [scenicSpotFid]
     * @return
     **/
    @ApiOperation("删除归属地")
    @RequestMapping("/delScenicSpotBinding")
    @ResponseBody
    public ReturnModel delScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysScenicSpotBindingService.delScenicSpotBinding(sysScenicSpotBinding.getScenicSpotFid());
            if (i == 1){
                returnModel.setData(1);
                returnModel.setMsg("景区归属地删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData(2);
                returnModel.setMsg("该地区下面有关联数据,无法删除");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData(i);
                returnModel.setMsg("删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delScenicSpotBinding",e);
            returnModel.setData("");
            returnModel.setMsg("景区归属地删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
            }
    }

    /**
     * 归属省下拉选
     * @return
     */
    @ApiOperation("归属省下拉选")
    @RequestMapping(value = "/getSpotBindingProvince")
    @ResponseBody
    public ReturnModel getSpotBindingProvince(String pid) {
        ReturnModel returnModel = new ReturnModel();

        try {
            List<SysScenicSpotBinding> list  = sysScenicSpotBindingService.getSpotBindingProvince(pid);
            returnModel.setData(list);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return returnModel;
    }


    /**
     * 归属市下拉选
     * @return
     */
    @ApiOperation("归属市下拉选")
    @RequestMapping(value = "/getSpotBindingCity")
    @ResponseBody
    public ReturnModel getSpotBindingCity(String pid) {
        ReturnModel returnModel = new ReturnModel();

        try {
            List<SysScenicSpotBinding> list  = sysScenicSpotBindingService.getSpotBindingCity(pid);
            returnModel.setData(list);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return returnModel;
    }

    /**
     * 归属区下拉选
     * @return
     */
    @ApiOperation("归属区下拉选")
    @RequestMapping(value = "/getSpotBindingArea")
    @ResponseBody
    public ReturnModel getSpotBindingArea(String pid) {
        ReturnModel returnModel = new ReturnModel();

        try {
            List<SysScenicSpotBinding> list  = sysScenicSpotBindingService.getSpotBindingArea(pid);
            returnModel.setData(list);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return returnModel;
    }


    /**
     * @Author
     * @Description 景区归属地列表查询
     * @Date 0:03 2020/5/24
     * @Param [pageNum, pageSize, sysScenicSpotBinding]
     *
     **/
    @ApiOperation("归属地列表查询")
    @RequestMapping(value = "/getScenicSpotBindingList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getScenicSpotBindingList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, SysScenicSpotBinding sysScenicSpotBinding) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotBindingService.getScenicSpotBindingList(pageNum,pageSize,sysScenicSpotBinding);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return pageDataResult;
    }
    @ApiOperation("修改热门城市开关")
    @RequestMapping("/editScenicSpotHot")
    @ResponseBody
    public ReturnModel editScenicSpotHot(@RequestBody SysScenicSpotBinding sysScenicSpotBinding){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysScenicSpotBindingService.editScenicSpotBinding(sysScenicSpotBinding);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区归属地修改状态成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区归属地删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delScenicSpotBinding",e);
            returnModel.setData("");
            returnModel.setMsg("景区归属地删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 多级联动下拉选
     * @return
     */
    @ApiOperation("多级联动下拉选")
    @RequestMapping(value = "/getMultistageDrop")
    @ResponseBody
    public ReturnModel getMultistageDrop() {

        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotBinding> list =  sysScenicSpotBindingService.getMulitistageDrop();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;

    }


}

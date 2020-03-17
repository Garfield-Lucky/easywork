package com.easy.work.controller;

import com.easy.work.api.service.UserService;
import com.easy.work.common.enums.ResultEnum;
import com.easy.work.common.exception.EasyWorkException;
import com.easy.work.common.util.sftp.SftpClientTemplate;
import com.easy.work.model.User;
import com.easy.work.util.vo.ResultVO;
import com.easy.work.util.vo.ResultVOUtils;
import com.jcraft.jsch.SftpException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@Api(value = "用户信息控制器")
public class UserRestController extends BaseController {

    @Autowired
    private UserService userService;

    @Value("${easywork.name}")
    private String applicationName;

    //@Autowired
    //SftpPoolConfig sftpPoolConfig;

    @Autowired
    SftpClientTemplate sftpClientTemplate;


    @RequestMapping(value = "/hello")
    public String hello(){
        System.out.println("easywork.name:"+applicationName);
        return "hello:" + applicationName;
    }

    /**
     * @Description: 通过id查找用户
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value="根据用户编号获取用户信息", notes="test: 仅1和2有正确返回")
    @ApiImplicitParam(paramType="path", name = "id", value = "用户编号", required = true, dataType = "long")
    public ResultVO findUserById(@PathVariable Long id) {
        log.info("***************************findUserById****************************");
        User user = userService.findById(id);
        return ResultVOUtils.success(user);
    }

    /**
     * @Description: 通过用户名查找用户
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    @RequestMapping(value = "/findUserByName", method = RequestMethod.GET)
    @ApiOperation(value="根据用户名获取用户信息", notes="test: 仅wzw正确返回")
    @ApiImplicitParam(paramType = "query" , name = "userName" , value = "用户名" , required = true,dataType = "String")
    public ResultVO findOneUser(@RequestParam(value = "userName", required = true) String userName) {
        log.info("***************************findOneUser****************************");
        User user = userService.findUserByName(userName);
        return ResultVOUtils.success(user);
    }

    /**
     * @Description: 返回所有用户
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value="获取用户列表", notes="目前一次全部取，不分页")
    public ResultVO findAll() throws FileNotFoundException, SftpException, InterruptedException {
        log.info("***************************findAll****************************");
        List<User> userList = userService.findAll();

        //System.out.println(sftpPoolConfig.toString());
        //File file = new File("D:\\software\\uim卡\\C91_27_9390_1806056001.xml");
        //InputStream is = new FileInputStream(file);
        //SftpClientTemplate sftp = SftpClientTemplate.getInstance();

        //new Thread(()->{
        //    try{
        //        //测试文件上传
        //        sftpClientTemplate.upload("/1001/test2/test7/", "C91_27_9390_1806056001.xml", is);
        //        System.out.println("1:"+sftpClientTemplate);
        //        //sftpClientTemplate.upload2("/1001/test2/test7/", "C91_27_9390_1806056002.xml", is);
        //        //System.out.println("2:"+sftpClientTemplate);
        //        //sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003.xml", is);
        //        //System.out.println("3:"+sftp);
        //
        //    }catch (Exception e){
        //        e.printStackTrace();
        //    }finally{
        //        try {
        //            is.close();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //}
        //).start();
        //
        //new Thread(()->{
        //    try{
        //        //Thread.sleep(20000);
        //        sftpClientTemplate.upload("/1001/test2/test7/", "C91_27_9390_1806056002.xml", is);
        //        System.out.println("2:"+sftpClientTemplate);
        //
        //    }catch (Exception e){
        //        e.printStackTrace();
        //    }
        //}
        //).start();
        //
        //new Thread(()->{
        //    try{
        //
        //        //Thread.sleep(20000);
        //        sftpClientTemplate.upload("/1001/test2/test7/", "C91_27_9390_1806056003.xml", is);
        //        System.out.println("3:"+sftpClientTemplate);
        //    }catch (Exception e){
        //        e.printStackTrace();
        //    }
        //}
        //).start();


        return ResultVOUtils.success(userList);
    }

    /**
     * @Description: 按条件返回用户列表数据
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    @RequestMapping(value="/findUserList", method = RequestMethod.GET)
    @ApiOperation(value="获取用户列表", notes="目前一次全部取，不分页")
    public ResultVO findUserList(@RequestBody Map<String, String> map) {
        log.info("*********findUserList**********************"+map.toString());
        List<User> listUser = userService.list(map);
        return ResultVOUtils.success(listUser);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="添加用户信息", notes="")
    public ResultVO save(@RequestBody User user) {
        log.info("save入参 "+user.toString());
        ResultVO resultVO = new ResultVO();
        try {
            long a= userService.save(user);
            resultVO.setCode(ResultEnum.SUCCESS.getCode());
            resultVO.setMsg("保存成功");
        } catch (Exception e) {
            log.error("添加用户失败" + user.toString(), e);
            resultVO.setCode(1);
            resultVO.setMsg("保存失败");
        }
        return resultVO;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    @ApiOperation(value="修改用户信息", notes="根据用户id修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name = "id", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam( name = "user", value = "用户", required = true, dataType = "User")
    })
    public ResultVO update(@PathVariable Long id,@RequestBody User user) {
        log.info("update入参： "+user.toString());
        ResultVO resultVO = new ResultVO();
        try {
            user.setId(id);
            long a = userService.update(user);
            if(a == 0){
                throw new EasyWorkException(ResultEnum.USER_NOT_EXIST);
            }
            resultVO.setCode(ResultEnum.SUCCESS.getCode());
            resultVO.setMsg("更新成功");
        } catch (EasyWorkException e) {
            resultVO.setCode(e.getCode());
            resultVO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("更新用户失败" + user.toString(), e);
            resultVO.setCode(ResultEnum.ERROR.getCode());
            resultVO.setMsg("更新失败");
        }
        return resultVO;
    }

    /**
     * @Description: 根据用户id删除用户
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    @RequestMapping(value="/{id}" ,method=RequestMethod.DELETE)
    @ApiOperation(value="删除用户信息", notes="")
    @ApiImplicitParam(paramType="path", name = "id", value = "用户ID", required = true, dataType = "long")
    public ResultVO delete(@PathVariable Long id) {
        ResultVO resultVO = new ResultVO();
        try {
            long a =userService.delete(id);
            if(a == 0){
                throw new EasyWorkException(ResultEnum.USER_NOT_EXIST);
            }
            resultVO.setCode(ResultEnum.SUCCESS.getCode());
            resultVO.setMsg("删除成功");
        } catch (EasyWorkException e) {
            resultVO.setCode(e.getCode());
            resultVO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("删除用户失败" + id, e);
            resultVO.setCode(ResultEnum.ERROR.getCode());
            resultVO.setMsg("删除失败");
        }
        return resultVO;
    }

}

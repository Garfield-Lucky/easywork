package com.easy.work.controller;

import com.easy.work.api.service.UserService;
import com.easy.work.common.enums.ResultEnum;
import com.easy.work.common.exception.EasyWorkException;
import com.easy.work.model.User;
import com.easy.work.util.vo.ResultVO;
import com.easy.work.util.vo.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserRestController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * @Description: 通过id查找用户
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultVO findUserById(@PathVariable int id) {
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
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResultVO findAll() {
        log.info("***************************findAll****************************");
        List<User> userList = userService.findAll();
        return ResultVOUtils.success(userList);
    }

    /**
     * @Description: 按条件返回用户列表数据
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    @RequestMapping(value="/findUserList", method = RequestMethod.GET)
    public ResultVO findUserList(@RequestBody Map<String, String> map) {
        log.info("*********findUserList**********************"+map.toString());
        List<User> listUser = userService.list(map);
        return ResultVOUtils.success(listUser);
    }

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public ResultVO save(@RequestBody User user) {
        log.info("save入参 "+user.toString());
        ResultVO resultVO = new ResultVO();
        try {
            int a= userService.save(user);
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
    public ResultVO update(@PathVariable int id,@RequestBody User user) {
        log.info("update入参： "+user.toString());
        ResultVO resultVO = new ResultVO();
        try {
            user.setId(id);
            int a = userService.update(user);
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
    public ResultVO delete(@PathVariable int id) {
        ResultVO resultVO = new ResultVO();
        try {
            int a =userService.delete(id);
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

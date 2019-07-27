package com.easy.work.controller;

import com.easy.work.annotation.UserLoginToken;
import com.easy.work.api.service.UserService;
import com.easy.work.common.enums.ResultEnum;
import com.easy.work.util.jwt.JwtUtil;
import com.easy.work.util.vo.ResultVO;
import com.easy.work.util.vo.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.easy.work.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    //登录验证
    @PostMapping("/login")
    public ResultVO login(@RequestBody User userModel){
        Map<String, Object> result = new HashMap<>();
        User user = userService.findUserByName(userModel.getUserName());
        if(user==null){
            return ResultVOUtils.error(ResultEnum.ERROR.getCode(), "登录失败,用户不存在");
        }else {
            if (!user.getPassword().equals(userModel.getPassword())){
                return ResultVOUtils.error(ResultEnum.ERROR.getCode(), "登录失败,密码错误");
            }else {
                String token = JwtUtil.generateTokenWithClaims(user);
                result.put("token", token);
                result.put("user", user);
                return ResultVOUtils.success(result);
            }
        }
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public ResultVO getMessage(){
        return ResultVOUtils.success("你已通过验证");
    }
}

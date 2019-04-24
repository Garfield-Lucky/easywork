package com.easy.work.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 用户实体类
 *
 * @author Created by wuzhangwei on 2019/1/9
 */

@Data
public class User implements Serializable {


    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */

    private String userName;
    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机
     */
    private String tel;

    /**
     * 微信
     */
    private String wx;


    /**
     * 最后登录时间
     */
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 状态
     */
    private Integer status;

    //加密密码的盐
    private String salt;

    /**
     * 密码盐.  重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
     * @return
     */
    @JsonIgnore
    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }


}
package com.easy.work.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 用户实体类
 *
 * @author Created by wuzhangwei on 2019/1/9
 */

@Data
@ApiModel(value="医生对象模型")
public class User implements Serializable {


    /**
     * 用户id
     */
    @ApiModelProperty(value="id" ,required=true)
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户姓名" ,required=true)
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty(value="用户密码")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value="用户真实姓名")
    private String trueName;

    /**
     * 年龄
     */
    @ApiModelProperty(value="用户年龄")
    private Integer age;

    /**
     * 性别
     */
    @ApiModelProperty(value="用户性别")
    private String sex;

    /**
     * 手机
     */
    @ApiModelProperty(value="用户手机号")
    private String tel;

    /**
     * 微信
     */
    @ApiModelProperty(value="用户微信号")
    private String wx;


    /**
     * 最后登录时间
     */
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="用户最后登录时间")
    private Date lastLoginTime;

    /**
     * 状态
     */
    @ApiModelProperty(value="用户状态")
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
package com.easy.work.api.service;


import com.easy.work.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Description: 用户业务逻辑接口类
 *
 * @author Created by wuzhangwei on 2019/1/9
 */
public interface UserService extends BaseService<User,Long>{


    /**
     * @Description: 根据用户名，查询用户信息
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    User findUserByName(String userName);


    /**
     * @Description: 获取角色
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    Set<String> findRoles(String userName);

    /**
     * @Description: 获取权限
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    Set<String> findPermissions(String userName);

    /**
     * @Description: 查询用户列表
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    List<User> findUserList(Map map);

}

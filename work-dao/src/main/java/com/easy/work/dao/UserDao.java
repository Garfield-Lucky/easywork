package com.easy.work.dao;


import com.easy.work.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Description: T用户 DAO 接口类
 *
 * @param
 * @author Create by wuzhangwei on 2019/1/9
 */
@Repository
public interface UserDao extends BaseDao<User,Long> {

   /**
    * @Description: 根据用户名称，查询用户信息
    *
    * @param userName 用户名
    * @author Create by wuzhangwei on 2019/1/9
    */
    User findByName(@Param("userName") String userName);


    /**
     * @Description: 通过map查询用户
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
         */
     User findUserByMap(Map map);


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

    /**
     * @Description: 获取用户总数
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    Long countByUser();
    /**
     * @Description: 根据用户id删除用户
     *
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    void deleteByPrimaryKey(Integer id);

}

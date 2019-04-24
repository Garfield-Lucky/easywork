package com.easy.work.api.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Created by wuzhangwei on 2019/1/9
 * @Description: 通用逻辑层接口
 */
public interface BaseService<T, ID extends Serializable> {

    /**
     * @Description: 保存入库
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    ID save(T entity) throws Exception;

    /**
     * @Description: 删除
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    ID delete(ID id) throws Exception;

    /**
     * @Description: 修改
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    ID update(T entity) throws Exception;

    /**
     * @Description: 返回所有用户
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    List<T> findAll();

    /**
     * @Description: 根据主键查询实体
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    T findById(ID id);

    /**
     * @Description: 返回用户列表数据
     * @param
     * @author Created by wuzhangwei on 2019/1/9
     */
    List<T> list(Map param);
}

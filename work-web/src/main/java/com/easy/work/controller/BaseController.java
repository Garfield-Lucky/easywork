package com.easy.work.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;


/**
 * @Description: 父类Action,包括一些通用的方法
 * @param
 * @author Created by wuzhangwei on 2019/1/9
 */
@Data
@Controller
public abstract class BaseController {

	protected Integer page;//表第几页
	protected Integer pageSize;//表每页显示几条数据
	protected String sortname;//每排序的字段名
	protected String sortorder;//指排序的方式,如desc,asc



}

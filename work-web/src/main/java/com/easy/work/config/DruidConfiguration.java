package com.easy.work.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: druid监控界面设置
 * 该配置类可以用注解的方式来配置，具体参考DruidStartFilter.java 和 DruidStartViewServlet.java
 * @param
 * @author Created by wuzhangwei on 2018/8/30 23:23
 */
@Configuration
public class DruidConfiguration {

	@Bean
	public ServletRegistrationBean druidStatViewServle() {
		//注册服务
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new StatViewServlet(), "/druid/*");
		// 白名单(为空表示,所有的都可以访问,多个IP的时候用逗号隔开)
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// IP黑名单 (存在共同时，deny优先于allow)
		servletRegistrationBean.addInitParameter("deny", "192.168.1.107");
		// 设置登录的用户名和密码
		servletRegistrationBean.addInitParameter("loginUsername", "wzw");
		servletRegistrationBean.addInitParameter("loginPassword", "123456");
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
				new WebStatFilter());
		// 添加过滤规则
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息
		filterRegistrationBean.addInitParameter("exclusions",
				"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		System.out.println("druid初始化成功!");
		return filterRegistrationBean;

	}
}
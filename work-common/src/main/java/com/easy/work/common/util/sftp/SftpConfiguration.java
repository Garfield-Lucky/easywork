package com.easy.work.common.util.sftp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: sftp配置
 * @param
 * @author Created by wuzhangwei on 2019/12/21
 */
@Configuration
public class SftpConfiguration {

   @Autowired
   SftpPoolConfig sftpPoolConfig;

   @Autowired
   SftpClientFactory sftpClientFactory;

   @Autowired
   SftpAbandonedConfig sftpAbandonedConfig;


    @Bean
   public SftpPool configSftpPool() {
        //新建一个对象池,传入对象工厂和配置
        return new SftpPool(sftpClientFactory, sftpPoolConfig);
   }

    //@Bean("sftpPoolAbandoned")
    //    //public SftpPool configSftpPool2() {
    //    //    //新建一个对废弃对象跟踪的对象池
    //    //    return new SftpPool(sftpClientFactory, sftpPoolConfig, sftpAbandonedConfig);
    //    //}


}
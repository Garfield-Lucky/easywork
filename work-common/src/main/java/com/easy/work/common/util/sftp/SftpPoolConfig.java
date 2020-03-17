package com.easy.work.common.util.sftp;

import com.jcraft.jsch.Session;
import lombok.Data;
import org.apache.commons.pool2.impl.EvictionPolicy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * sftp连接池配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "sftp-pool.pool")
public class SftpPoolConfig extends GenericObjectPoolConfig {

}
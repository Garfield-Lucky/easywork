package com.easy.work.common.util.sftp;

import lombok.Data;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * sftp废弃对象跟踪配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "sftp-pool.abandoned")
public class SftpAbandonedConfig extends AbandonedConfig {

}
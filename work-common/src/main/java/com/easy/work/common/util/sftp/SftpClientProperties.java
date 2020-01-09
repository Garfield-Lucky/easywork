package com.easy.work.common.util.sftp;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sftp-pool")
public class SftpClientProperties {
    /**
     * ftp地址
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 登录用户
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 连接超时时间(秒)
     */
    private Integer connectTimeout;

    /**
     * 传输超时时间(秒)
     */
    private Integer dataTimeout;

    // 私钥
    private String privateKey;


}
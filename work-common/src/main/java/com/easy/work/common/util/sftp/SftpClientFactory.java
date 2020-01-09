package com.easy.work.common.util.sftp;

import com.easy.work.common.enums.ResultEnum;
import com.easy.work.common.exception.EasyWorkException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;


/**
 * SFTPClient工厂类，通过SFTPClient工厂提供SFTPClient实例的创建和销毁
 * @author heaven
 */
@Component
public class SftpClientFactory extends BasePooledObjectFactory<Session> {

    private static Logger log = LoggerFactory.getLogger("SftpClientFactory");

    @Autowired
    private SftpClientProperties sftpConfig;

    /**
     * 创建FtpClient对象
     */
    @Override
    public Session create() {
        try {
            JSch jsch = new JSch();
            if (sftpConfig.getPrivateKey() != null) {
                jsch.addIdentity(sftpConfig.getPrivateKey()); // 设置私钥
            }

            Session session = jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHost(), sftpConfig.getPort());

            if (sftpConfig.getPassword() != null) {
                session.setPassword(sftpConfig.getPassword());
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.setTimeout(sftpConfig.getConnectTimeout()); // 设置超时
            session.connect();

            log.info("login to sftp : success! host:{}, username:{}, port{} ", sftpConfig.getHost(), sftpConfig.getUsername(), sftpConfig.getPort());
            return session;
        }
        catch (JSchException e) {
            log.error("login to sftp : failed! host:{}, username:{}, port{}",  sftpConfig.getHost(), sftpConfig.getUsername(), sftpConfig.getPort());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }
    }

    /**
     * 用PooledObject封装对象放入池中
     */
    @Override
    public PooledObject<Session> wrap(Session session) {
        return new DefaultPooledObject<>(session);
    }

    /**
     * 销毁sftp对象
     */
    @Override
    public void destroyObject(PooledObject<Session> sftpPooled) {

        System.out.println("destroyObject:" + new Date());

        if (sftpPooled == null) {
            return;
        }
        Session session = sftpPooled.getObject();
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }

    }

    /**
     * 功能描述：判断资源对象是否有效，有效返回 true，无效返回 false
     *
     * 什么时候会调用此方法
     * 1：从资源池中获取资源的时候，参数 testOnBorrow 或者 testOnCreate 中有一个 配置 为 true 时，则调用  factory.validateObject() 方法
     * 2：将资源返还给资源池的时候，参数 testOnReturn，配置为 true 时，调用此方法
     * 3：资源回收线程，回收资源的时候，参数 testWhileIdle，配置为 true 时，调用此方法
     */
    @Override
    public boolean validateObject(PooledObject<Session> arg0) {

        try {
            System.out.println("validateObject:" + new Date());

            Session session = arg0.getObject();
            if(session.isConnected()) {
                return super.validateObject(arg0);
            } else {
                System.out.println("no Connected");
                return false;
            }

        } catch(Exception e) {
            log.error("validateObject can't open sftp channel: ", e);
            return false;
        }

    }


}
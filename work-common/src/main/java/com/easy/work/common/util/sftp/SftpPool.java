package com.easy.work.common.util.sftp;

import com.jcraft.jsch.Session;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class SftpPool extends GenericObjectPool<Session> {

    public SftpPool(SftpClientFactory sftpClientFactory) {
        super(sftpClientFactory);
    }

    public SftpPool(SftpClientFactory sftpClientFactory, SftpPoolConfig sftpPoolConfig) {
        super(sftpClientFactory, sftpPoolConfig);
    }

    public SftpPool(SftpClientFactory sftpClientFactory, SftpPoolConfig sftpPoolConfig, AbandonedConfig abandonedConfig) {
        super(sftpClientFactory, sftpPoolConfig, abandonedConfig);
    }
}

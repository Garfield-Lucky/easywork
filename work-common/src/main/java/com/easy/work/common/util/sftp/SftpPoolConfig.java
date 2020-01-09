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
public class SftpPoolConfig extends GenericObjectPoolConfig<Session> {

        //这里要给父类的JmxEnable设置成false，否则启动的时候会报jmx的异常 org.springframework.jmx.export.UnableToRegisterMBeanException: Unable to register MBean
        public SftpPoolConfig() {
                super.setJmxEnabled(false);
        }

        //private int maxTotal; //# 对象最大数量
        //private int maxIdle; //# 最大空闲对象数量
        //private int minIdle; //# 最小空闲对象数量
        //private boolean lifo; //表示使用FIFO获取对象
        //private boolean fairness; //不使用lock的公平锁
        //private long maxWaitMillis; //# 获取一个对象的最大等待时间
        //private long minEvictableIdleTimeMillis; //# 对象最小的空闲时间
        //private long evictorShutdownTimeoutMillis; // # 驱逐线程的超时时间
        //private long softMinEvictableIdleTimeMillis; // # 对象最小的空间时间，保留最小空闲数量
        //private int numTestsPerEvictionRun; //检测空闲对象线程每次检测的空闲对象的数量
        //private boolean testOnCreate; //在创建对象时检测对象是否有效
        //private boolean testOnBorrow; //在从对象池获取对象时是否检测对象有效
        //private boolean testOnReturn; //在向对象池中归还对象时是否检测对象有效
        //private boolean testWhileIdle; //在检测空闲对象线程检测到对象不需要移除时，是否检测对象的有效性
        //private long timeBetweenEvictionRunsMillis; //空闲对象检测线程的执行周期
        //private boolean blockWhenExhausted; //当对象池没有空闲对象时，新的获取对象的请求是否阻塞
        //private boolean jmxEnabled; //是否注册JMX
        //private String jmxNamePrefix; //JMX前缀，当jmxEnabled为true时有效
        //private String jmxNameBase; //使用jmxNameBase + jmxNamePrefix + i来生成ObjectName，当jmxEnabled为true时有效


}
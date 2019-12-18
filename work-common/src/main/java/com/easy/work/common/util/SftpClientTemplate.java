package com.easy.work.common.util;

import com.easy.work.common.enums.ResultEnum;
import com.easy.work.common.exception.EasyWorkException;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 实现文件上传下载
 *
 * @author wuzhangwei
 */
@Slf4j
public class SftpClientTemplate {

    private GenericObjectPool<Session> sftpClientPool;

    private int connectTimeout = 120;

    public SftpClientTemplate(SftpClientFactory sftpClientFactory) {
        this.sftpClientPool = new GenericObjectPool<>(sftpClientFactory);
    }

    public SftpClientTemplate(SftpClientFactory sftpClientFactory, GenericObjectPoolConfig poolConfig) {
        this.sftpClientPool = new GenericObjectPool<>(sftpClientFactory, poolConfig);
    }

    private static class InnerHolder {
        static final SftpClientProperties config= new SftpClientProperties();
        static final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();


        static {
            config.setHost("192.168.182.129");
            config.setUsername("wuzhangwei");
            config.setPassword("123456");
            config.setPort(2222);
            config.setConnectTimeout(10000);
            config.setDataTimeout(10000);

            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestOnCreate(true);
            poolConfig.setMaxWaitMillis(30000);
            poolConfig.setMaxTotal(3);
        }

        static final SftpClientFactory factory = new SftpClientFactory(config);

        private static final SftpClientTemplate INSTABCE = new SftpClientTemplate(factory, poolConfig);
    }

    private static class InnerHolder2 {
        static final SftpClientProperties config2= new SftpClientProperties();

        static {
            config2.setHost("192.168.107.134");
            config2.setUsername("wuzhangwei");
            config2.setPassword("123456");
            config2.setPort(2223);
            config2.setConnectTimeout(100);
            config2.setDataTimeout(30000);
        }

        static final SftpClientFactory factory2 = new SftpClientFactory(config2);
        private static final SftpClientTemplate INSTABCE = new SftpClientTemplate(factory2);
    }

    public static SftpClientTemplate getInstance() {
        return SftpClientTemplate.InnerHolder.INSTABCE;
    }
    public static SftpClientTemplate getInstance2() {
        return SftpClientTemplate.InnerHolder2.INSTABCE;
    }


    /**
     * @Description: 上传文件
     * @param savePath
     * @param sftpFileName
     * @param input
     * @throws EasyWorkException
     * @return void
     * @author Created by wuzhangwei on 2019年9月24日
     */
    public void upload(String savePath, String sftpFileName, InputStream input) throws SftpException {

        try {
            //从连接池取连接对象
            final Session session = sftpClientPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(connectTimeout);

                System.out.println("session:" + session);
                if (!checkPathExist(channelSftp, savePath)) {
                    createDir(channelSftp, savePath); //创建目录
                }

                channelSftp.put(input, sftpFileName);  //上传文件

                log.info("upload file to sftp : success! fileName:{} ", sftpFileName);
            }
            catch (Exception e) {
                log.error("upload " + savePath + "/" + sftpFileName + " to sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                if(session != null)
                    // 放回连接池
                    sftpClientPool.returnObject(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }
    }
    //检查是目录是否存在
    private boolean checkPathExist(ChannelSftp sftp, String savePath) {
        try {
            sftp.cd(savePath);
        }
        catch (SftpException e) {
            return false;
        }
        return true;
    }

    //创建目录
    private void createDir(ChannelSftp sftp, String savePath) throws SftpException {
        String[] dirs = savePath.split("/");
        String tempPath = "";
        for (String dir:dirs) {
            if (null == dir || "".equals(dir)) {
                continue;
            }
            tempPath += "/" + dir;
            try {
                sftp.cd(tempPath);
            }
            catch (SftpException ex) {
                sftp.mkdir(tempPath);
                sftp.cd(tempPath);
            }
        }
    }

    /**
     * 下载文件。
     * @param remoteFilePath 下载目录
     * @param remoteFileName 文件名称
     * @param saveFile  保存下载文件的路径
     * @param saveFile 存在本地的路径
     */
    public void download(String remoteFilePath, String remoteFileName, String saveFile) {

        try {
            //从连接池取连接对象
            final Session session = sftpClientPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(connectTimeout);

                channelSftp.cd(remoteFilePath);
                File file = new File(saveFile);
                channelSftp.get(remoteFileName, new FileOutputStream(file));
            }
            catch (Exception e) {
                log.error("download " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpClientPool.returnObject(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }
    }


    /**
     * @Description: 下载xml文件
     * @param remoteFilePath sftp存放路径
     * @param remoteFileName 文件名称
     * @return
     * @return Document
     */
    public Document downloadXmlDocument(String remoteFilePath, String remoteFileName) {

        try {
            //从连接池取连接对象
            final Session session = sftpClientPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(connectTimeout);

                channelSftp.cd(remoteFilePath);
                InputStream is = channelSftp.get(remoteFileName);

                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(is);
                return document;
            }
            catch (Exception e) {
                log.error("downloadXmlDocument " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpClientPool.returnObject(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }

    }

    /**
     * @Description: 下载文件
     * @param remoteFilePath sftp存放路径
     * @param remoteFileName 文件名称
     * @return
     * @return InputStream
     */
    public InputStream downloadFile(String remoteFilePath, String remoteFileName) {

        try {
            //从连接池取连接对象
            final Session session = sftpClientPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(connectTimeout);

                channelSftp.cd(remoteFilePath);
                return channelSftp.get(remoteFileName);
            }
            catch (Exception e) {
                log.error("downloadFile " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpClientPool.returnObject(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }

    }


    /**
     * @Description: 删除文件
     * @param remoteFilePath 要删除文件所在目录
     * @param remoteFileName 要删除的文件名称
     * @return void
     */
    public void delete(String remoteFilePath, String remoteFileName) {
        try {
            //从连接池取连接对象
            final Session session = sftpClientPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(connectTimeout);

                channelSftp.cd(remoteFilePath);
                channelSftp.rm(remoteFileName);
            }
            catch (Exception e) {
                log.error("delete " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpClientPool.returnObject(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }
    }



    /**
     * @Description: 获取对应路径下的文件列表
     * @param filePath
     * @return
     * @return List<String>
     */
    public List<String> getFileList(String filePath) {
        try {
            //从连接池取连接对象
            final Session session = sftpClientPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(connectTimeout);

                @SuppressWarnings("unchecked")
                Vector<ChannelSftp.LsEntry> v = channelSftp.ls(filePath);
                List<String> list = new ArrayList<>();
                for (ChannelSftp.LsEntry obj : v) {
                    String fileName = obj.getFilename();
                    if (".".equals(fileName) || "..".equals(fileName)) {
                        continue;
                    }
                    list.add(fileName);
                }
                return list;
            }
            catch (Exception e) {
                log.error("getFileList " + filePath + " from sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpClientPool.returnObject(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }
    }


    /**
     * @Description: 更改文件路径或文件名称
     * @param remoteFilePath 当前文件存放的路径
     * @param newRemoteFilePath 更改后的文件存放路径
     * @param fileName 更改后的文件名称
     * @throws EasyWorkException
     * @return void
     */
    public void remameFile(String remoteFilePath, String newRemoteFilePath, String fileName) {
        try {
            //从连接池取连接对象
            final Session session = sftpClientPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(connectTimeout);
                if (!checkPathExist(channelSftp, newRemoteFilePath)) {
                    createDir(channelSftp, newRemoteFilePath); //创建目录
                }
                channelSftp.rename(remoteFilePath, newRemoteFilePath + "/" + fileName);
                log.info("remameFile " + remoteFilePath + " to " + newRemoteFilePath + "/" + fileName + " from sftp : success!");
            }
            catch (Exception e) {
                log.error("remameFile " + remoteFilePath + " to " + newRemoteFilePath + " from sftp : failed!" + e.getMessage());
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpClientPool.returnObject(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }
    }



    public static void main(String[] args ) {

        try {

            File file = new File("D:\\software\\uim卡\\C91_27_9390_1806056001.xml");
            InputStream is = new FileInputStream(file);
            SftpClientTemplate sftp = SftpClientTemplate.getInstance();
            //测试文件上传
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056001.xml", is);

            //Thread.sleep(20000);
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056002.xml", is);
            System.out.println(sftp);
            //Thread.sleep(20000);
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056003.xml", is);
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056003.xml", is);
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056003.xml", is);
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056003.xml", is);
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056003.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003880.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003881.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003882.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003883.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003884.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003885.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003886.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003887.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056003888.xml", is);


            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056003.xml", is);
            //sftp2.upload("/1002/test2/test7/", "C91_27_9390_1806056003.xml", is);
            sftp.upload("/1001/test2/test7/", "C91_27_9390_180605600388.xml", is);

            System.out.println(sftp);


        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }


}

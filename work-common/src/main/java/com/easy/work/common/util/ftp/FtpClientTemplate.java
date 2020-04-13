package com.easy.work.common.util.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.*;

/**
 * 实现文件上传下载
 *
 * @author wuzhangwei
 */
@Slf4j
public class FtpClientTemplate {

    private GenericObjectPool<FTPClient> ftpClientPool;

    public FtpClientTemplate(FtpClientFactory ftpClientFactory) {
        this.ftpClientPool = new GenericObjectPool<>(ftpClientFactory);
    }

    /***
     * 上传Ftp文件
     *
     * @param localFile 当地文件
     * @param remotePath 上传服务器路径 - 应该以/结束
     * @return true or false
     */
    public boolean uploadFile(File localFile, String remotePath, String path) {
        FTPClient ftpClient = null;
        BufferedInputStream inStream = null;
        try {
            //从池中获取对象
            ftpClient = ftpClientPool.borrowObject();
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
                return false;
            }
            // 改变工作路径
            ftpClient.changeWorkingDirectory(remotePath);
            inStream = new BufferedInputStream(new FileInputStream(localFile));
            log.info("start upload... {}", localFile.getName());

            final int retryTimes = 3;

            for (int j = 0; j <= retryTimes; j++) {
                boolean success = ftpClient.storeFile(path + localFile.getName() , inStream);
                if (success) {
                    log.info("upload file success! {}", localFile.getName());
                    return true;
                }
                log.warn("upload file failure! try uploading again... {} times", j);
            }

        } catch (FileNotFoundException e) {
            log.error("file not found!{}", localFile);
        } catch (Exception e) {
            log.error("upload file failure!", e);
        } finally {
            IOUtils.closeQuietly(inStream);
            //将对象放回池中
            ftpClientPool.returnObject(ftpClient);
        }
        return false;
    }

    /**
     * 下载文件
     *
     * @param remotePath FTP服务器文件目录
     * @param fileName   需要下载的文件名称
     * @param localPath  下载后的文件路径
     * @return true or false
     */
    public boolean downloadFile(String remotePath, String fileName, String localPath) {
        FTPClient ftpClient = null;
        OutputStream outputStream = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
                return false;
            }

            // 切换FTP目录
            ftpClient.changeWorkingDirectory(remotePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (fileName.equalsIgnoreCase(file.getName())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(localPath).append(File.separator).append(file.getName());
                    File localFile = new File(stringBuilder.toString());
                    outputStream = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), outputStream);
                }
            }
            ftpClient.logout();
            return true;
        } catch (Exception e) {
            log.error("download file failure!", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            ftpClientPool.returnObject(ftpClient);
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param remotePath FTP服务器保存目录
     * @param fileName   要删除的文件名称
     * @return true or false
     */
    public boolean deleteFile(String remotePath, String fileName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
                return false;
            }
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(remotePath);
            int delCode = ftpClient.dele(fileName);
            log.debug("delete file reply code:{}", delCode);
            return true;
        } catch (Exception e) {
            log.error("delete file failure!", e);
        } finally {
            ftpClientPool.returnObject(ftpClient);
        }
        return false;
    }


    public static void main(String[] args ) {

        try {
//            FtpUtil ftp = new FtpUtil("10.45.102.20", "21", "Think", "wzw", "sim");
//            ftp.uploadFile("E:\\download\\C91_27_9390_1806056001.xml", "C91_27_9390_1806056001.xml");
            FtpClientProperties config = new FtpClientProperties();
            config.setHost("10.45.117.78");
            config.setUsername("Think");
            config.setPassword("wzw");
            config.setPort(21);
            config.setConnectTimeout(30);
            config.setDataTimeout(30000);
            config.setEncoding("UTF-8");
            config.setTransferFileType(FTP.BINARY_FILE_TYPE);
            config.setPassiveMode(true);

            FtpClientFactory factory = new FtpClientFactory(config);
            //FtpClientPool ftpClientPool = new FtpClientPool(factory);
            FtpClientTemplate ftp = new FtpClientTemplate(factory);
//            ftp.changeDir("sim");
//            ftp.checkPathExist("sim/qw");
            System.out.println(ftp);
            System.out.println(ftp);
            ftp.uploadFile(new File("E:\\download\\C91_27_9390_1806056001.xml"), "num/qwe","1");
            System.out.println(ftp);

            ftp.uploadFile(new File("E:\\download\\C91_27_9390_1806056001.xml"), "num/qwe","2");
            System.out.println(ftp);

            ftp.uploadFile(new File("E:\\download\\C91_27_9390_1806056001.xml"), "num/qwe","3");

//            ftp.uploadFile("E:\\download\\C91_27_9390_1806056001.xml", "C91_27_9390_180605600111.xml");

            System.out.println(ftp);
            System.out.println(ftp);


        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }


}

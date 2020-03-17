package com.easy.work.common.util.sftp;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
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
@Component
public class SftpClientTemplate {

    @Autowired
    SftpPool sftpPool;

    /**
     * @Description: 上传文件
     * @param savePath
     * @param sftpFileName
     * @param input
     * @throws EasyWorkException
     * @return void
     * @author Created by wuzhangwei on 2019年9月24日
     */
    public void upload(String savePath, String sftpFileName, InputStream input) throws Exception {

            //从连接池取连接对象
             Session session = null;
            try {
                session = sftpPool.borrowObject();
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(3000);

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
                if(session != null) {
                    // 放回连接池
                    sftpPool.returnObject(session);
                } else {
                    System.out.println("session is null");
                }
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
    public void download(String remoteFilePath, String remoteFileName, String saveFile) throws Exception {

            //从连接池取连接对象
            final Session session = sftpPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(3000);

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
                sftpPool.returnObject(session);
            }
    }


    /**
     * @Description: 下载xml文件
     * @param remoteFilePath sftp存放路径
     * @param remoteFileName 文件名称
     * @return
     * @return Document
     */
    public Document downloadXmlDocument(String remoteFilePath, String remoteFileName) throws Exception {

            //从连接池取连接对象
            final Session session = sftpPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(3000);

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
                sftpPool.returnObject(session);
            }

    }

    /**
     * @Description: 下载文件
     * @param remoteFilePath sftp存放路径
     * @param remoteFileName 文件名称
     * @return
     * @return InputStream
     */
    public InputStream downloadFile(String remoteFilePath, String remoteFileName) throws Exception {

            //从连接池取连接对象
            final Session session = sftpPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(3000);

                channelSftp.cd(remoteFilePath);
                return channelSftp.get(remoteFileName);
            }
            catch (Exception e) {
                log.error("downloadFile " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpPool.returnObject(session);
            }

    }


    /**
     * @Description: 删除文件
     * @param remoteFilePath 要删除文件所在目录
     * @param remoteFileName 要删除的文件名称
     * @return void
     */
    public void delete(String remoteFilePath, String remoteFileName) throws Exception {
            //从连接池取连接对象
            final Session session = sftpPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(3000);

                channelSftp.cd(remoteFilePath);
                channelSftp.rm(remoteFileName);
            }
            catch (Exception e) {
                log.error("delete " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
                throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
            }
            finally {
                // 放回连接池
                sftpPool.returnObject(session);
            }
    }



    /**
     * @Description: 获取对应路径下的文件列表
     * @param filePath
     * @return
     * @return List<String>
     */
    public List<String> getFileList(String filePath) throws Exception {
            //从连接池取连接对象
            final Session session = sftpPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(3000);

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
                sftpPool.returnObject(session);
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
    public void remameFile(String remoteFilePath, String newRemoteFilePath, String fileName) throws Exception {
            //从连接池取连接对象
            final Session session = sftpPool.borrowObject();
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect(3000);
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
                sftpPool.returnObject(session);
            }
    }

}

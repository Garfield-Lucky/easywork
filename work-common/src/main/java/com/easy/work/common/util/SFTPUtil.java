package com.easy.work.common.util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;  
import java.util.Vector;

import com.easy.work.common.enums.ResultEnum;
import com.easy.work.common.exception.EasyWorkException;
import org.apache.log4j.spi.ErrorCode;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import com.jcraft.jsch.Channel;  
import com.jcraft.jsch.ChannelSftp;  
import com.jcraft.jsch.JSch;  
import com.jcraft.jsch.JSchException;  
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* 类说明 sftp工具类
*/
public class SFTPUtil {
    private static final Logger logger = LoggerFactory.getLogger(SFTPUtil.class);


    private static final String logModule = "SFTPUtil";
    
    private ChannelSftp sftp;  
        
    private Session session;  
    
    // 登录用户名   
    private String username; 
    
    // 登录密码   
    private String password;  
    
    // 私钥
    private String privateKey;
 
    //服务器地址IP地址
    private String host;  
    
    //端口  
    private int port;  
        
    
    /**  
     * 构造基于密码认证的sftp对象  
     */    
    public SFTPUtil(String username, String password, String host, int port) {  
        this.username = username;  
        this.password = password;  
        this.host = host;  
        this.port = port;  
    } 
    
    /**  
     * 构造基于秘钥认证的sftp对象 
     */  
    public SFTPUtil(String username, String host, int port, String privateKey) {  
        this.username = username;  
        this.host = host;  
        this.port = port;  
        this.privateKey = privateKey;  
    }  
    
    public SFTPUtil() {
        
    }  
    
    
    /** 
     * 连接sftp服务器 
     */  
    public void connectToServer() {  
        try {  
            JSch jsch = new JSch();  
            if (privateKey != null) {  
                jsch.addIdentity(privateKey); // 设置私钥  
            }  
    
            session = jsch.getSession(username, host, port);  
           
            if (password != null) {  
                session.setPassword(password);    
            }  
            Properties config = new Properties();  
            config.put("StrictHostKeyChecking", "no");  
                
            session.setConfig(config);  
            session.setTimeout(1000 * 30); // 设置超时
            session.connect();  
              
            Channel channel = session.openChannel("sftp");  
            channel.connect();  
    
            sftp = (ChannelSftp) channel;  
            logger.info("login to sftp : success! host:{}, username:{}, port{} ", host, username, port);
        } 
        catch (JSchException e) {  
            logger.error("login to sftp : failed! host:{}, username:{}, port{}", host, username, port);
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        }  
    }    
    
    /** 
     * 关闭连接 server  
     */  
    public void closeConnect() { 
        if (sftp != null) {  
            if (sftp.isConnected()) {  
                sftp.disconnect();  
            }  
        }  
        if (session != null) {  
            if (session.isConnected()) {  
                session.disconnect();  
            }  
        }  
    }  
 
    
   
    /**
    * @Description: 上传文件
    * @param savePath
    * @param sftpFileName
    * @param input
    * @throws SftpException 
    * @return void
    * @author Created by wuzhangwei on 2019年9月24日
    */
    public void upload(String savePath, String sftpFileName, InputStream input) throws SftpException {  
        try {  
            //打开连接
            connectToServer();
            if (!checkPathExist(savePath)) {
                createDir(savePath); //创建目录
            } 
            sftp.put(input, sftpFileName);  //上传文件
            logger.info("upload file to sftp : success! fileName:{} ", sftpFileName);
        } 
        catch (Exception e) {
            logger.error("upload " + savePath + "/" + sftpFileName + " to sftp : failed!");
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        } 
        finally {
            // 关闭连接
            closeConnect();  
        }
    } 
    

    //创建目录
    private void createDir(String savePath) throws SftpException {
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

    //检查是目录是否存在
    private boolean checkPathExist(String savePath) {
        try {
            sftp.cd(savePath);
        } 
        catch (SftpException e) {
            return false;
        }  
        return true;
    }

    /** 
     * 下载文件。
     * @param directory 下载目录  
     * @param downloadFile 下载的文件 
     * @param saveFile 存在本地的路径 
     */    
    public void download(String remoteFilePath, String remoteFileName, String saveFile) {  
        try {
            //打开连接
            connectToServer(); 
            
            sftp.cd(remoteFilePath);  
            File file = new File(saveFile);  
            sftp.get(remoteFileName, new FileOutputStream(file));  
        }
        catch (Exception e) {
            logger.error("download " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        } 
        finally {
            // 关闭连接
            closeConnect();  
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
            //打开连接
            connectToServer(); 
            
            sftp.cd(remoteFilePath);  
            InputStream is = sftp.get(remoteFileName); 
            
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(is);
            return document;
        }
        catch (Exception e) {
            logger.error("downloadXmlDocument " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        } 
        finally {
            // 关闭连接
            closeConnect();  
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
            //打开连接
            connectToServer(); 
            
            sftp.cd(remoteFilePath);  
            return sftp.get(remoteFileName); 
        }
        catch (Exception e) {
            logger.error("downloadFile " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        } 
        finally {
            // 关闭连接
            closeConnect();  
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
            //打开连接
            connectToServer(); 
            
            sftp.cd(remoteFilePath);  
            sftp.rm(remoteFileName);  
        } 
        catch (Exception e) {
            logger.error("delete " + remoteFilePath + "/" + remoteFileName + " from sftp : failed!");
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        } 
        finally {
            // 关闭连接
            closeConnect();  
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
            //打开连接
            connectToServer(); 
            
            @SuppressWarnings("unchecked")
            Vector<ChannelSftp.LsEntry> v = sftp.ls(filePath);
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
            logger.error("getFileList " + filePath + " from sftp : failed!");
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
        } 
        finally {
            // 关闭连接
            closeConnect();  
        }
    }
      
 
    /**
    * @Description: 更改文件路径或文件名称
    * @param remoteFileName
    * @param newRemoteFileName
    * @throws SftpException 
    * @return void
    */
    public void remameFile(String remoteFilePath, String newRemoteFilePath, String fileName) {  
        try {
             //打开连接
             connectToServer(); 
             if (!checkPathExist(newRemoteFilePath)) {
                 createDir(newRemoteFilePath); //创建目录
             } 
             sftp.rename(remoteFilePath, newRemoteFilePath + "/" + fileName); 
             logger.info("remameFile " + remoteFilePath + " to " + newRemoteFilePath + "/" + fileName + " from sftp : success!");
        }
        catch (Exception e) {
             logger.error("remameFile " + remoteFilePath + " to " + newRemoteFilePath + " from sftp : failed!");
            throw new EasyWorkException(ResultEnum.ERROR.getCode(), e.getMessage(), e);
         } 
         finally {
             // 关闭连接
             closeConnect();  
         }
    }
    
    //上传文件测试
    public static void main(String[] args) throws FileNotFoundException, SftpException {
        SFTPUtil sftp = new SFTPUtil("wuzhangwei", "123456", "192.16/**/8.107.130", 2222);
        File file = new File("D:\\software\\uim卡\\C91_27_9390_1806056001.xml");
        InputStream is = new FileInputStream(file);
        //测试文件上传
        sftp.upload("/1001/test2/test7/", "C91_27_9390_1806056001.xml", is);
        File file2 = new File("D:\\software\\UltraEdit_v19绿色版.zip");
        InputStream is2 = new FileInputStream(file2);
          
        sftp.upload("/1001", "UltraEdit.zip", is2);
        
          //文件列表
//        List<String> fileNames = sftp.getFileList("/1001/1965868/1569325261889");
//        for (String filename : fileNames) {
//            System.out.println("文件名称：" + filename);
//        }
        
          //测试删除
//        sftp.delete("/1001//test2/test6", "C91_27_9390_1806056001.xml");
        
          //测试下载xml文档
//        Document document = sftp.downloadXmlDocument("/1001//test2/test5/", "902_27_9390_1806056001.xml");
//        System.out.println(document.toString());
        
          //测试重命名文件和移动存放路径
       // ../sim_back_up/C91_27_9390_1806056001.xml
        ///1001/test2/test5/C91_27_9390_1806056001.xml
//        sftp.remameFile("/1001/test2/test6/C91_27_9390_1806056001.xml", "/1001/back_up/", "C91_27_9390_1806056001.xml");
//        System.out.println("测试结束");
    }  
}

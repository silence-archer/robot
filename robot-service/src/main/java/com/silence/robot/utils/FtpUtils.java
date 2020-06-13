/**
 * @title FTPUtils.java
 * @author mazhaohui
 * @date 2019年8月24日 下午1:42:02
 */
package com.zycfc.alc.core.common.util;

import com.jcraft.jsch.*;
import com.zycfc.alc.core.common.enumeration.FtpTypeEnum;
import com.zycfc.zsf.boot.util.collection.ListUtils;
import com.zycfc.zsf.boot.util.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author mazhaohui
 * <Ftp工具类>
 * @date 2019年8月24日 下午1:42:02
 */
@Slf4j
public class FtpUtils {


    /**
     * 上传文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param localFile  本地文件名
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @date 2020/6/13 15:07
     */
    public static void upload(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        switch (FtpTypeEnum.getByCode(ftpConfig.getFtpType())) {
            case FTP:
                uploadForFtp(ftpConfig, remotePath, remoteFile, localPath, localFile);
                break;
            case SFTP:
                uploadForSftp(ftpConfig, remotePath, remoteFile, localPath, localFile);
                break;
            default:
                break;

        }
    }

    /**
     * 下载文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param localFile  本地文件名
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @date 2020/6/13 15:07
     */
    public static void download(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        switch (FtpTypeEnum.getByCode(ftpConfig.getFtpType())) {
            case FTP:
                downloadForFtp(ftpConfig, remotePath, remoteFile, localPath, localFile);
                break;
            case SFTP:
                downloadForSftp(ftpConfig, remotePath, remoteFile, localPath, localFile);
                break;
            default:
                break;

        }
    }

    /**
     * 下载远程路径下的所有文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @date 2020/6/13 15:07
     */
    public static void downloadAll(FtpConfig ftpConfig, String remotePath, String localPath) {
        switch (FtpTypeEnum.getByCode(ftpConfig.getFtpType())) {
            case FTP:
                downloadAllForFtp(ftpConfig, remotePath, localPath);
                break;
            case SFTP:
                downloadAllForSftp(ftpConfig, remotePath, localPath);
                break;
            default:
                break;

        }
    }

    /**
     * 下载远程路径下的所有文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @date 2020/6/13 15:07
     */
    public static boolean isExists(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        boolean result = false;
        switch (FtpTypeEnum.getByCode(ftpConfig.getFtpType())) {
            case FTP:
                result = isExistsForFtp(ftpConfig, remotePath, remoteFile);
                break;
            case SFTP:
                result = isExistsForSftp(ftpConfig, remotePath, remoteFile);
                break;
            default:
                break;

        }

        return result;
    }

    /**
     * 删除远程路径下的所有文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @date 2020/6/13 15:07
     */
    public static void delete(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        switch (FtpTypeEnum.getByCode(ftpConfig.getFtpType())) {
            case FTP:
                deleteForFtp(ftpConfig, remotePath, remoteFile);
                break;
            case SFTP:
                deleteForSftp(ftpConfig, remotePath, remoteFile);
                break;
            default:
                break;

        }

    }

    /**
     * 判断远程FTP服务器上是否存在某个文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     */
    private static void deleteForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        FTPClient ftpClient = null;

        try {
            ftpClient = getFTPClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.deleteFile(remoteFile);


        } catch (IOException e) {
            log.error("根据ip-{}端口-{}用户名-{}密码-{}查找远程文件路径{}远程文件名{}删除失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, e);
        } finally {
            close(null, null, ftpClient);
        }
    }

    /**
     * 判断远程SFTP服务器上是否存在某个文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     */
    private static void deleteForSftp(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            channelSftp.cd(remotePath);
            channelSftp.rm(remoteFile);
        } catch (Exception e) {
            log.error("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件文件名{}删除文件失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, e);
        } finally {
            close(session, channelSftp);
        }
    }

    /**
     * 判断远程FTP服务器上是否存在某个文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @return 是否存在
     */
    private static boolean isExistsForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        FTPClient ftpClient = null;
        boolean result = false;

        try {
            ftpClient = getFTPClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }

            List<String> fileList = getFileList(ftpClient, remotePath);
            if (ListUtils.isNotEmpty(fileList)) {
                result = fileList.stream().anyMatch(name -> name.equals(remoteFile));
            }


        } catch (IOException e) {
            log.error("根据ip-{}端口-{}用户名-{}密码-{}查找远程文件路径{}远程文件名{}失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, e);
        } finally {
            close(null, null, ftpClient);
        }
        return result;
    }

    /**
     * 判断远程SFTP服务器上是否存在某个文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @return 是否存在
     */
    private static boolean isExistsForSftp(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        Session session = null;
        ChannelSftp channelSftp = null;
        boolean result = false;
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            SftpATTRS attrs = channelSftp.stat(remoteFile);
            result = attrs != null;

        } catch (Exception e) {
            log.error("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件文件名{}查找失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, e);
        } finally {
            close(session, channelSftp);
        }
        return result;
    }

    /**
     * ftp下载远程路径下的所有文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param remotePath 远程路径
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:52:48
     */
    private static void downloadAllForFtp(FtpConfig ftpConfig, String remotePath, String localPath) {
        FileOutputStream out = null;
        FTPClient ftpClient = null;

        try {
            ftpClient = getFTPClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            // 主动模式
            ftpClient.enterLocalActiveMode();
            ftpClient.changeWorkingDirectory(remotePath);
            List<String> fileList = getFileList(ftpClient, remotePath);
            for (String name : fileList) {
                File file = new File(localPath + File.separator + name);
                out = new FileOutputStream(file);
                ftpClient.retrieveFile(name, out);
                out.close();
            }

        } catch (IOException e) {
            log.error("根据ip-{}端口-{}用户名-{}密码-{}本地文件路径{}远程文件路径{}下载文件失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getUser(), ftpConfig.getPassword(), localPath, remotePath, e);
        } finally {
            close(null, out, ftpClient);
        }
    }

    /**
     * sftp下载远程路径下的所有文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param remotePath 远程路径
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:52:48
     */
    private static void downloadAllForSftp(FtpConfig ftpConfig, String remotePath, String localPath) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            if (StringUtils.isNotEmpty(remotePath)) {
                channelSftp.cd(remotePath);
            }
            List<String> fileList = getFileList(channelSftp, remotePath);
            for (String name : fileList) {
                channelSftp.get(name, localPath + File.separator + name);
            }


        } catch (Exception e) {
            log.error("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}本地文件路径{}下载文件失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, localPath, e);
        } finally {
            close(session, channelSftp);
        }


    }

    /**
     * ftp下载文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param localFile  本地文件名
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:52:48
     */
    private static void downloadForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        FileOutputStream out = null;
        FTPClient ftpClient = null;

        try {
            ftpClient = getFTPClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            File file = new File(localPath + File.separator + localFile);
            out = new FileOutputStream(file);
            ftpClient.enterLocalActiveMode();
            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.retrieveFile(remoteFile, out);
        } catch (IOException e) {
            log.error("根据ip-{}端口-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}下载文件失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile, e);
        } finally {
            close(null, out, ftpClient);
        }
    }

    /**
     * sftp下载文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param localFile  本地文件名
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:52:48
     */
    private static void downloadForSftp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            if (StringUtils.isNotEmpty(remotePath)) {
                channelSftp.cd(remotePath);
            }
            channelSftp.get(remoteFile, localPath + File.separator + localFile);

        } catch (Exception e) {
            log.error("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}下载文件失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile, e);
        } finally {
            close(session, channelSftp);
        }


    }

    /**
     * SFTP上传文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param localFile  本地文件名
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @date 2020/6/13 15:07
     */
    private static void uploadForSftp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            createDirectory(remotePath, channelSftp);
            //上传文件
            channelSftp.put(localPath + File.separator + localFile, remoteFile);

        } catch (Exception e) {
            log.error("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}上传文件失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile, e);
        } finally {
            close(session, channelSftp);
        }

    }

    /**
     * FTP上传文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param localFile  本地文件名
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @date 2020/6/13 15:07
     */
    private static void uploadForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        FTPClient ftpClient = null;
        FileInputStream in = null;
        try {
            ftpClient = getFTPClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            // 切换工作目录为用户的根目录 同时创建目录
            createDirectory(remotePath, ftpClient);

            File file = new File(localPath + localFile);
            in = new FileInputStream(file);

            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.storeFile(remoteFile, in);


        } catch (Exception e) {
            log.error("根据ip-{}端口-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}上传文件失败", ftpConfig.getIpAddr(), ftpConfig.getPort(), ftpConfig.getUser(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile, e);
        } finally {
            close(in, null, ftpClient);
        }

    }

    /**
     * <读取指定目录下的所有文件名>
     *
     * @param ftpClient  ftp操作类
     * @param remotePath 远程路径
     * @return 远程路径下的文件名
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:53:02
     */
    private static List<String> getFileList(FTPClient ftpClient, String remotePath) throws IOException {
        List<String> fileList = new ArrayList<>();
        // 获得指定目录下所有文件名
        FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                fileList.add(ftpFile.getName());
            }
        }
        return fileList;
    }

    /**
     * <读取指定目录下的所有文件名>
     *
     * @param channelSftp sftp操作类
     * @param remotePath  远程路径
     * @return 远程路径下的文件名
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:53:02
     */
    private static List<String> getFileList(ChannelSftp channelSftp, String remotePath) throws SftpException {
        List<String> fileList = new ArrayList<>();
        // 获得指定目录下所有文件名
        Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(remotePath);
        if (!ls.isEmpty()) {
            fileList = ls.stream().filter(lsEntry -> !lsEntry.getAttrs().isDir()).map(ChannelSftp.LsEntry::getFilename).collect(Collectors.toList());
        }

        return fileList;
    }

    /**
     * <初始化FTP>
     *
     * @param ftpConfig ftp配置信息
     * @return ftp操作类
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:49:27
     */
    private static FTPClient getFTPClient(FtpConfig ftpConfig) throws IOException {
        FTPClient ftpClient = new FTPClient();
        boolean result = true;
        ftpClient.connect(ftpConfig.getIpAddr(), ftpConfig.getPort());
        if (ftpClient.isConnected()) {
            boolean flag = ftpClient.login(ftpConfig.getUser(), ftpConfig.getPassword());
            if (flag) {
                ftpClient.setControlEncoding(StandardCharsets.UTF_8.name());
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        if (result) {
            return ftpClient;
        } else {
            return null;
        }
    }


    /**
     * <关闭FTP>
     *
     * @param in        输入流
     * @param out       输出流
     * @param ftpClient ftp操作类
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:49:50
     */
    private static void close(InputStream in, OutputStream out, FTPClient ftpClient) {
        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
                log.error("ftp关闭文件输入流失败", e);
            }
        }
        if (null != out) {
            try {
                out.close();
            } catch (IOException e) {
                log.error("ftp关闭文件输出流失败", e);
            }
        }
        if (null != ftpClient) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error("ftp服务器注销失败", e);
            }

        }
    }

    /**
     * <关闭SFTP>
     *
     * @param session     SFTP连接会话
     * @param channelSftp SFTP连接管道
     * @author ZHANGHAORAN
     * @date 2019年8月24日 下午1:49:50
     */
    private static void close(Session session, ChannelSftp channelSftp) {
        if (channelSftp != null) {
            if (channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }


    /**
     * 创建文件目录
     *
     * @param pathName  远程路径
     * @param ftpClient ftp操作类
     * @throws IOException IO异常
     */
    private static void createDirectory(String pathName, FTPClient ftpClient)
            throws IOException {

        String directory = pathName.substring(0, pathName.lastIndexOf(File.separator) + 1);

        if (!directory.equalsIgnoreCase(File.separator)
                && !ftpClient.changeWorkingDirectory(directory)) {

            // 如果远程目录不存在，则递归创建远程服务器目录
            int start = 0;

            if (directory.startsWith(File.separator)) {
                start = 1;
            }
            int end = directory.indexOf(File.separator, start);

            do {
                String subDirectory = pathName.substring(start, end);
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        log.warn("创建目录失败");
                        return;
                    }
                }

                start = end + 1;
                end = directory.indexOf(File.separator, start);

                // 检查所有目录是否创建完毕
            } while (end > start);
        }
    }

    /**
     * 连接sftp服务器
     *
     * @param ftpConfig ftp配置信息
     * @return sftp会话
     */
    private static Session getSftpSession(FtpConfig ftpConfig) throws JSchException {
        JSch jsch = new JSch();
        if (ftpConfig.getSecretKey() != null) {
            // 设置私钥
            jsch.addIdentity(ftpConfig.getSecretKey());
        }
        Session session = jsch.getSession(ftpConfig.getUser(), ftpConfig.getIpAddr(), ftpConfig.getPort());
        if (ftpConfig.getPassword() != null) {
            session.setPassword(ftpConfig.getPassword());
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        session.setConfig(config);
        session.connect();

        return session;
    }


    /**
     * 切换远程sftp服务器路径
     *
     * @param pathName 远程路径
     * @param sftp     sftp操作类
     * @auther oe_machaohui
     * @date 2020/6/13 12:11
     */
    private static void createDirectory(String pathName, ChannelSftp sftp) throws SftpException {
        String directory = pathName.substring(0, pathName.lastIndexOf(File.separator) + 1);

        if (!directory.equalsIgnoreCase(File.separator)) {
            try {
                sftp.cd(directory);
            } catch (SftpException e) {
                // 如果远程目录不存在，则递归创建远程服务器目录
                int start = 0;

                if (directory.startsWith(File.separator)) {
                    start = 1;
                }
                int end = directory.indexOf(File.separator, start);

                do {
                    String subDirectory = pathName.substring(start, end);
                    try {
                        sftp.cd(subDirectory);
                    } catch (SftpException sftpException) {
                        sftp.mkdir(subDirectory);
                        sftp.cd(subDirectory);
                    }
                    start = end + 1;
                    end = directory.indexOf(File.separator, start);

                    // 检查所有目录是否创建完毕
                } while (end > start);
            }

        }
    }


}

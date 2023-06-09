
package com.silence.robot.utils;

import com.jcraft.jsch.*;
import com.silence.robot.config.FtpConfig;
import com.silence.robot.enumeration.FtpTypeEnum;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author silence
 * <Ftp工具类>
 * @since 2019年8月24日 下午1:42:02
 */
public class FtpUtils {

    private static final Logger log = LoggerFactory.getLogger(FtpUtils.class);

    private static final char REMOTE_FILE_SEPARATOR = '/';


    /**
     * 上传文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param localFile  本地文件名
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @since 2020/6/13 15:07
     */
    public static void upload(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        switch (FtpTypeEnum.valueOf(ftpConfig.getFtpType())) {
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
     * @since 2020/6/13 15:07
     */
    public static void download(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        switch (FtpTypeEnum.valueOf(ftpConfig.getFtpType())) {
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
     * 获取远程文件大小
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther mazhaohui
     * @since 2020/6/13 15:07
     */
    public static long getFileSize(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        long fileSize = 0;
        switch (FtpTypeEnum.valueOf(ftpConfig.getFtpType())) {
            case FTP:
                fileSize = getFileSizeForFtp(ftpConfig, remotePath, remoteFile);
                break;
            case SFTP:
                fileSize = getFileSizeForSftp(ftpConfig, remotePath, remoteFile);
                break;
            default:
                break;
        }
        return fileSize;
    }



    /**
     * 下载远程路径下的所有文件
     *
     * @param ftpConfig  ftp配置信息
     * @param localPath  本地路径
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @since 2020/6/13 15:07
     */
    public static void downloadAll(FtpConfig ftpConfig, String remotePath, String localPath) {
        switch (FtpTypeEnum.valueOf(ftpConfig.getFtpType())) {
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
     * 判断远程服务器上是否存在某个文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @auther oe_machaohui
     * @since 2020/6/13 15:07
     */
    public static boolean isExists(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        boolean result = false;
        switch (FtpTypeEnum.valueOf(ftpConfig.getFtpType())) {
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
     * @since 2020/6/13 15:07
     */
    public static void delete(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        switch (FtpTypeEnum.valueOf(ftpConfig.getFtpType())) {
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
        log.info("根据ip-{}端口-{}用户名-{}密码-{}查找远程文件路径{}远程文件名{}删除>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile);
        try {
            ftpClient = getFtpClient(ftpConfig);
            if (ftpClient == null) {
                throw new IllegalStateException("登录ftp服务器失败");
            }
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                throw new IllegalStateException("切换ftp路径失败");
            }
            if (!ftpClient.deleteFile(remoteFile)) {
                throw new IllegalStateException("删除ftp文件失败");
            }


        } catch (IOException e) {
            throw new IllegalStateException("删除ftp文件失败", e);
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
        log.info("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件文件名{}删除>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile);
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            channelSftp.cd(remotePath);
            channelSftp.rm(remoteFile);
        } catch (Exception e) {
            throw new IllegalStateException("删除sftp文件失败", e);
        } finally {
            close(session, channelSftp);
        }
    }

    /**
     * 判断远程FTP服务器上是否存在某个文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径-从ftp的根路径开始以/开头
     * @return 是否存在
     */
    private static boolean isExistsForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        FTPClient ftpClient = null;
        boolean result = false;
        log.info("根据ip-{}端口-{}用户名-{}密码-{}查找远程文件路径{}远程文件名{}>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile);
        try {
            ftpClient = getFtpClient(ftpConfig);
            if (ftpClient == null) {
                throw new IllegalStateException("登录ftp服务器失败");
            }
            List<String> fileList = getFileList(ftpClient, remotePath);
            if (CommonUtils.isNotEmpty(fileList)) {
                result = fileList.stream().anyMatch(name -> name.equals(remoteFile));
            }
        } catch (IOException e) {
            throw new IllegalStateException("查找ftp文件失败", e);
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
        boolean result;
        log.info("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件文件名{}查找>>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile);
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            SftpATTRS attrs = channelSftp.stat(remotePath + REMOTE_FILE_SEPARATOR + remoteFile);
            result = attrs != null;

        } catch (Exception e) {
            throw new IllegalStateException("查找sftp文件失败", e);
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
     * @author silence
     * @since 2019年8月24日 下午1:52:48
     */
    private static void downloadAllForFtp(FtpConfig ftpConfig, String remotePath, String localPath) {
        FileOutputStream out = null;
        FTPClient ftpClient = null;
        log.info("根据ip-{}端口-{}用户名-{}密码-{}本地文件路径{}远程文件路径{}下载文件>>>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), localPath, remotePath);
        try {
            ftpClient = getFtpClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                throw new IllegalStateException("切换ftp远程路径失败");
            }
            List<String> fileList = getFileList(ftpClient, remotePath);
            for (String name : fileList) {
                File file = new File(localPath + File.separator + name);
                out = new FileOutputStream(file);
                ftpClient.retrieveFile(name, out);
                out.close();
            }

        } catch (IOException e) {
            throw new IllegalStateException("从ftp下载文件失败", e);
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
     * @author silence
     * @since 2019年8月24日 下午1:52:48
     */
    private static void downloadAllForSftp(FtpConfig ftpConfig, String remotePath, String localPath) {
        Session session = null;
        ChannelSftp channelSftp = null;
        log.info("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}本地文件路径{}下载文件>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, localPath);
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            if (CommonUtils.isNotEmpty(remotePath)) {
                channelSftp.cd(remotePath);
            }
            List<String> fileList = getFileList(channelSftp, remotePath);
            for (String name : fileList) {
                channelSftp.get(name, localPath + File.separator + name);
            }

        } catch (Exception e) {
            throw new IllegalStateException("从sftp下载文件失败", e);
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
     * @author silence
     * @since 2019年8月24日 下午1:52:48
     */
    private static void downloadForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        FileOutputStream out = null;
        FTPClient ftpClient = null;
        log.info("根据ip-{}端口-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}下载文件>>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile);
        try {
            ftpClient = getFtpClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            FileUtils.createFile(localPath, localFile);
            File file = new File(localPath + File.separator + localFile);

            out = new FileOutputStream(file);
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                throw new IllegalStateException("切换ftp远程路径失败");
            }
            if (!ftpClient.retrieveFile(remoteFile, out)) {
                throw new IllegalStateException("从ftp下载文件失败");
            }

        } catch (IOException e) {
            throw new IllegalStateException("从ftp下载文件失败", e);
        } finally {
            close(null, out, ftpClient);
        }
    }

    /**
     * 获取ftp文件大小
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @author silence
     * @since 2019年8月24日 下午1:52:48
     */
    private static long getFileSizeForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        FileOutputStream out = null;
        FTPClient ftpClient = null;
        log.info("根据ip-{}端口-{}用户名-{}密码-{}远程文件路径{}远程文件名{}>>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile);
        try {
            ftpClient = getFtpClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            FTPFile ftpFile = ftpClient.mlistFile(remotePath + File.separator + remoteFile);
            return ftpFile.getSize();
        } catch (IOException e) {
            throw new IllegalStateException("从ftp下载文件失败", e);
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
     * @author silence
     * @since 2019年8月24日 下午1:52:48
     */
    private static void downloadForSftp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        Session session = null;
        ChannelSftp channelSftp = null;
        log.info("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}下载文件>>>>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile);
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            if (CommonUtils.isNotEmpty(remotePath)) {
                channelSftp.cd(remotePath);
            }
            FileUtils.createFile(localPath, localFile);
            channelSftp.get(remoteFile, localPath + File.separator + localFile);

        } catch (Exception e) {
            throw new IllegalStateException("从sftp下载文件失败", e);
        } finally {
            close(session, channelSftp);
        }


    }

    /**
     * sftp下载文件
     *
     * @param ftpConfig  ftp配置信息
     * @param remoteFile 远程文件名
     * @param remotePath 远程路径
     * @author mazhaohui
     * @since 2019年8月24日 下午1:52:48
     */
    private static long getFileSizeForSftp(FtpConfig ftpConfig, String remotePath, String remoteFile) {
        Session session = null;
        ChannelSftp channelSftp = null;
        log.info("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件名{}>>>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile);
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            if (CommonUtils.isNotEmpty(remotePath)) {
                channelSftp.cd(remotePath);
            }
            SftpATTRS lstat = channelSftp.lstat(remoteFile);
            return lstat.getSize();

        } catch (Exception e) {
            throw new IllegalStateException("从sftp下载文件失败", e);
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
     * @since 2020/6/13 15:07
     */
    private static void uploadForSftp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        Session session = null;
        ChannelSftp channelSftp = null;
        log.info("根据ip-{}端口-{}私钥-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}上传文件>>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getSecretKey(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile);
        try {
            session = getSftpSession(ftpConfig);
            channelSftp = (ChannelSftp) session.openChannel(FtpTypeEnum.SFTP.getCode());
            channelSftp.connect();
            createDirectory(remotePath, channelSftp);
            //上传文件
            channelSftp.put(localPath + File.separator + localFile, remoteFile);

        } catch (Exception e) {
            throw new IllegalStateException("从sftp上传文件失败", e);
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
     * @since 2020/6/13 15:07
     */
    private static void uploadForFtp(FtpConfig ftpConfig, String remotePath, String remoteFile, String localPath, String localFile) {
        FTPClient ftpClient = null;
        FileInputStream in = null;
        log.error("根据ip-{}端口-{}用户名-{}密码-{}远程文件路径{}远程文件名{}本地文件路径{}本地文件名{}上传文件>>>>>", ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), remotePath, remoteFile, localPath, localFile);
        try {
            ftpClient = getFtpClient(ftpConfig);
            if (ftpClient == null) {
                throw new RuntimeException("登录ftp服务器失败");
            }
            // 切换工作目录为用户的根目录 同时创建目录
            createDirectory(remotePath, ftpClient);

            File file = new File(localPath + File.separator + localFile);
            in = new FileInputStream(file);
            if (!ftpClient.storeFile(remoteFile, in)) {
                throw new IllegalStateException("从ftp上传文件失败");
            }

        } catch (Exception e) {
            throw new IllegalStateException("从ftp上传文件失败", e);
        } finally {
            close(in, null, ftpClient);
        }

    }

    /**
     * <读取指定目录下的所有文件名>
     *
     * @param ftpClient  ftp操作类
     * @param remotePath 远程路径-从ftp的根路径开始以/开头
     * @return 远程路径下的文件名
     * @author silence
     * @since 2019年8月24日 下午1:53:02
     */
    private static List<String> getFileList(FTPClient ftpClient, String remotePath) throws IOException {
        List<String> fileList = new ArrayList<>();
        if (remotePath.charAt(0) != REMOTE_FILE_SEPARATOR) {
            throw new IllegalStateException("读取指定目录下的所有文件名时,远程路径必须从ftp根路径开始");
        }
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
     * @author silence
     * @since 2019年8月24日 下午1:53:02
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
     * @author silence
     * @since 2019年8月24日 下午1:49:27
     */
    private static FTPClient getFtpClient(FtpConfig ftpConfig) throws IOException {
        FTPClient ftpClient = new FTPClient();
        boolean result = true;
        ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort());
        if (ftpClient.isConnected()) {
            boolean flag = ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            if (flag) {
                ftpClient.setControlEncoding(StandardCharsets.UTF_8.name());
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 主动模式
                ftpClient.enterLocalActiveMode();
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
     * @author silence
     * @since 2019年8月24日 下午1:49:50
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
     * @author silence
     * @since 2019年8月24日 下午1:49:50
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
     * 创建并切换至文件目录
     *
     * @param remotePath 远程路径
     * @param ftpClient  ftp操作类
     * @throws IOException IO异常
     */
    private static void createDirectory(String remotePath, FTPClient ftpClient)
            throws IOException {

        if (CommonUtils.isNotEmpty(remotePath)) {
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                if (!ftpClient.makeDirectory(remotePath)) {
                    throw new IllegalStateException("ftp创建远程路径失败");
                }
                ftpClient.changeWorkingDirectory(remotePath);
            }
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
        if (CommonUtils.isNotEmpty(ftpConfig.getSecretKey())) {
            // 设置私钥
            jsch.addIdentity(ftpConfig.getSecretKey());
        }
        Session session = jsch.getSession(ftpConfig.getUsername(), ftpConfig.getHost(), ftpConfig.getPort());
        if (ftpConfig.getPassword() != null) {
            session.setPassword(ftpConfig.getPassword());
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        //去掉Kerberos 身份验证
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
        session.setConfig(config);
        session.connect();

        return session;
    }


    /**
     * 切换远程sftp服务器路径
     *
     * @param remotePath 远程路径
     * @param sftp       sftp操作类
     * @auther oe_machaohui
     * @since 2020/6/13 12:11
     */
    private static void createDirectory(String remotePath, ChannelSftp sftp) throws SftpException {
        if (CommonUtils.isEmpty(remotePath)) {
            return;
        }
        try {
            sftp.cd(remotePath);
        } catch (SftpException e) {
            log.error("当前目录{}不存在,需要创建", remotePath, e);
            int endIndex = remotePath.indexOf(REMOTE_FILE_SEPARATOR);
            if (endIndex == 0) {
                endIndex = remotePath.indexOf(REMOTE_FILE_SEPARATOR, 1);
            }
            String subPath;
            String path;
            if (endIndex == -1) {
                subPath = remotePath;
                path = "";
            } else {
                subPath = remotePath.substring(0, endIndex);
                path = remotePath.substring(endIndex + 1);
            }
            try {
                sftp.cd(subPath);
            } catch (SftpException sftpException) {
                sftp.mkdir(subPath + REMOTE_FILE_SEPARATOR);
                sftp.cd(subPath);
            }


            createDirectory(path, sftp);
        }
    }

}
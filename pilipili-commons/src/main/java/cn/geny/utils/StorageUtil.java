package cn.geny.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class StorageUtil {
    public static String storageHostAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();//获取的是本地的IP地址
        } catch (UnknownHostException ex) {
            log.error("获取服务IP地址失败");
        }
        assert address != null;
        return address.getHostAddress();
    }

    public static String defaultStoragePath(String customRootPath, String... paths) {
        StringBuilder defaultPath = null;
        File file;
        boolean createDirectoryIsDone = false;
        System.out.println(customRootPath);
        if (customRootPath.equals("")) {
            defaultPath = new StringBuilder(System.getProperty("user.dir") + "/pilipili/");
            for (String subPath : paths) {
                defaultPath.append(subPath).append("/");
            }
        } else {
            defaultPath = new StringBuilder(customRootPath);
        }
        file = new File(defaultPath.toString());
        if (!file.exists()) {
            createDirectoryIsDone = file.mkdirs();
        } else {
            createDirectoryIsDone = true;
        }
        if (!createDirectoryIsDone) {
            log.info("create storage path is fail");
            throw new RuntimeException();
        }
        log.info("All file will storage in " + defaultPath.toString());
        return defaultPath.toString() + "/";
    }
}
package cn.geny.pilipili.config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@Configuration
@Slf4j
public class CustomConfiguration {
    @Value("${storagePath}")
    private String storagePath;
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${server.port}")
    private String port;

    @Bean
    public String hostAddress() throws UnknownHostException{
        String hostAddress;
        InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址
        hostAddress = address.getHostAddress();//192.168.0.121
        return hostAddress;
    }

    @Bean
    public String storagePath(){
        String defaultPath = null;
        File file;
        boolean createDirectoryIsDone = false;
        System.out.println(storagePath);
        if (storagePath.equals("")){
            defaultPath = System.getProperty("user.dir")+"/pilipili/" + applicationName + port;
        }else {
            defaultPath = storagePath;
        }
        file = new File(defaultPath);
        if (!file.exists()){
            createDirectoryIsDone = file.mkdirs();
        }else {
            createDirectoryIsDone = true;
        }
        if (!createDirectoryIsDone){
            log.info("create storage path is fail");
            throw new RuntimeException();
        }
        log.info("file will storage in " + defaultPath);
        return defaultPath + "/";
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

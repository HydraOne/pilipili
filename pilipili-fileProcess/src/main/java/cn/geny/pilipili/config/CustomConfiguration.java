package cn.geny.pilipili.config;

import cn.geny.utils.StorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class CustomConfiguration {
    @Value("${customStoragePath}")
    private String customStoragePath;
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${server.port}")
    private String port;


    @Bean
    public String hostAddress(){
        return StorageUtil.storageHostAddress();
    }

    @Bean
    public String storagePath(){
        return StorageUtil.defaultStoragePath(customStoragePath,applicationName,port);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

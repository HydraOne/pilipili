package cn.geny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class VDataSource {
    @Bean
    public HashMap<String, String> storageMap(){
        return new HashMap<>();
    }
}

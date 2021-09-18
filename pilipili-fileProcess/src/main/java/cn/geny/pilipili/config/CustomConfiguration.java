package cn.geny.pilipili.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;

@Configuration
public class CustomConfiguration {
    @Bean
    @Scope()
    public HashMap<String, String> customBean(){
        HashMap<String, String> map = new HashMap<>();
        map.put(null,"test");
        map.put("test","testKey");
        return map;
    }
}

package cn.geny.pilipili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FileProcessApplication {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(FileProcessApplication.class, args);
    }
}
package cn.geny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class PiliPiliTmpApplication {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(PiliPiliTmpApplication.class, args);
    }
}
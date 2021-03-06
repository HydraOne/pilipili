package cn.geny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class PiliPiliViewApplication {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(PiliPiliViewApplication.class, args);
    }
}
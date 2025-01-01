package org.spring.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        // .env 파일 로드
        Dotenv dotenv = Dotenv.load();

        // 환경 변수 설정
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("EUREKA_INSTANCE_HOSTNAME", dotenv.get("EUREKA_INSTANCE_HOSTNAME"));
        System.setProperty("EUREKA_CLIENT_FETCH_REGISTRY", dotenv.get("EUREKA_CLIENT_FETCH_REGISTRY"));
        System.setProperty("EUREKA_CLIENT_REGISTER_WITH_EUREKA", dotenv.get("EUREKA_CLIENT_REGISTER_WITH_EUREKA"));

        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

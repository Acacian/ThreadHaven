package org.spring.user_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        // .env 파일 로드
        Dotenv dotenv = Dotenv.load();

        // 환경 변수 설정
        System.setProperty("USERHOST", dotenv.get("USERHOST"));
        System.setProperty("USERDB", dotenv.get("USERDB"));
        System.setProperty("USERUSERNAME", dotenv.get("USERUSERNAME"));
        System.setProperty("USERPASSWORD", dotenv.get("USERPASSWORD"));
        System.setProperty("USER_SERVICE_PORT", dotenv.get("USER_SERVICE_PORT"));
        System.setProperty("DBPORT", dotenv.get("DBPORT"));
        System.setProperty("DBPORT_HOST", dotenv.get("DBPORT_HOST"));
        System.setProperty("USER_SERVICE_PORT", dotenv.get("USER_SERVICE_PORT"));

        // Spring 애플리케이션 실행
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

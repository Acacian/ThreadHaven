package org.spring.user_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

    @BeforeAll
    static void setUp() {
        // .env 파일 로드
        Dotenv dotenv = Dotenv.load();

        // 환경 변수 설정
        System.setProperty("USERHOST", dotenv.get("USERHOST"));
        System.setProperty("USERDB", dotenv.get("USERDB"));
        System.setProperty("USERUSERNAME", dotenv.get("USERUSERNAME"));
        System.setProperty("USERPASSWORD", dotenv.get("USERPASSWORD"));
        System.setProperty("JWTSECRET", dotenv.get("JWTSECRET"));
        System.setProperty("JWTEXPIRE", dotenv.get("JWTEXPIRE"));
    }

    @Test
    void contextLoads() {
    }
}

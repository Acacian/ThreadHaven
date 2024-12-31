package org.spring.api_gateway;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		// .env 파일 로드
		Dotenv dotenv = Dotenv.load();

        // 환경 변수 설정
        System.setProperty("Auth_Path", dotenv.get("Auth_Path"));
        System.setProperty("User_Path", dotenv.get("User_Path"));
        System.setProperty("Port", dotenv.get("Port"));
		
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

package com.tz.campon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tz.campon.mapper")  // MyBatis 매퍼 스캔 추가
public class CamponApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamponApplication.class, args);
	}

}

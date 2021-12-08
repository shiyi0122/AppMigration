package com.jxzy.AppMigration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("com.jxzy.AppMigration.*.dao")
@EnableOpenApi
@EnableKnife4j
@EnableScheduling
public class AppMigrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppMigrationApplication.class, args);
	}

}

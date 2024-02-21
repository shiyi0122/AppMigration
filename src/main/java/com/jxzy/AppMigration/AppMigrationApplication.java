package com.jxzy.AppMigration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.jxzy.AppMigration.NavigationApp.task.ScenicSpotDataTask;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@MapperScan("com.jxzy.AppMigration.*.dao")
@EnableOpenApi
@EnableKnife4j
@EnableScheduling
public class AppMigrationApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppMigrationApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppMigrationApplication.class);
	}

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(AppMigrationApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String name = env.getProperty("spring.application.name");
		String port = env.getProperty("server.port");
		String path = env.getProperty("serverservlet,context-path") == null ? "" : env.getProperty("server,servlet,context-path");
		LOGGER.info("\n----------------------------------------------------------\n\t" +
				"Application <" + name + "> is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + path + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
				"----------------------------------------------------------");
	}

}

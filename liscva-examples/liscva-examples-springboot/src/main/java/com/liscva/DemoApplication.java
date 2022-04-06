package com.liscva;

import com.liscva.framework.security.SecurityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Liscva-Security整合SpringBoot 示例
 *
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("\n启动成功：Liscva-Security配置如下：" + SecurityManager.getConfig());
	}
	
}

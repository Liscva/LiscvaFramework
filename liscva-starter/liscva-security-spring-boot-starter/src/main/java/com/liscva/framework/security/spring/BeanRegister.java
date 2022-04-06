package com.liscva.framework.security.spring;

import com.liscva.framework.security.config.SecurityConfig;
import com.liscva.framework.security.context.SecurityContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * 注册所需要的Bean
 * <p> Bean 的注册与注入应该分开在两个文件中，否则在某些场景下会造成循环依赖 
 * 
 *
 */
public class BeanRegister {

	/**
	 * 获取配置Bean
	 * 
	 * @return 配置对象
	 */
	@Bean
	@ConfigurationProperties(prefix = "liscva.security")
	public SecurityConfig getSecurityConfig() {
		return new SecurityConfig();
	}
	
	/**
	 * 获取容器交互Bean (Spring版)
	 * 
	 * @return 容器交互Bean (Spring版)
	 */
	@Bean
	public SecurityContext getSecurityContext() {
		return new SecurityContextForSpring();
	}

}

package com.liscva.framework.security.spring;

import com.liscva.framework.security.action.SecurityInfoAction;
import com.liscva.framework.security.basic.SecurityBasicTemplate;
import com.liscva.framework.security.basic.SecurityBasicUtil;
import com.liscva.framework.security.config.SecurityConfig;
import com.liscva.framework.security.context.SecurityContext;
import com.liscva.framework.security.dao.SecurityInfoDao;
import com.liscva.framework.security.id.SecurityIdTemplate;
import com.liscva.framework.security.id.SecurityIdUtil;
import com.liscva.framework.security.listener.SecurityListener;
import com.liscva.framework.security.spring.util.PathMatcherHolder;
import com.liscva.framework.security.sso.SecuritySsoTemplate;
import com.liscva.framework.security.sso.SecuritySsoUtil;
import com.liscva.framework.security.lsp.LspInterface;
import com.liscva.framework.security.lsp.LspLogic;
import com.liscva.framework.security.lsp.LspUtil;
import com.liscva.framework.security.temp.SecurityTempInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.PathMatcher;
import com.liscva.framework.security.SecurityManager;

/**
 * 注入所需要的Bean
 *
 */
public class BeanInject {

	/**
	 * 注入配置Bean
	 * 
	 * @param securityConfig SecurityConfig对象
	 */
	@Autowired(required = false)
	public void setConfig(SecurityConfig securityConfig) {
		SecurityManager.setConfig(securityConfig);
	}

	/**
	 * 注入持久化Bean
	 * 
	 * @param securityInfoDao SecurityInfoDao对象
	 */
	@Autowired(required = false)
	public void setSecurityInfoDao(SecurityInfoDao securityInfoDao) {
		SecurityManager.setSecurityInfoDao(securityInfoDao);
	}

	/**
	 * 注入权限认证Bean
	 * 
	 * @param lspInterface StpInterface对象 
	 */
	@Autowired(required = false)
	public void setStpInterface(LspInterface lspInterface) {
		SecurityManager.setStpInterface(lspInterface);
	}

	/**
	 * 注入框架行为Bean
	 * 
	 * @param securityInfoAction SecurityInfoAction对象
	 */
	@Autowired(required = false)
	public void setSecurityInfoAction(SecurityInfoAction securityInfoAction) {
		SecurityManager.setSecurityInfoAction(securityInfoAction);
	}

	/**
	 * 注入上下文Bean
	 * 
	 * @param securityContext SecurityContext对象
	 */
	@Autowired(required = false)
	public void setSecurityContext(SecurityContext securityContext) {
		SecurityManager.setSecurityContext(securityContext);
	}

	/**
	 * 注入侦听器Bean
	 * 
	 * @param securityListener SecurityListener对象
	 */
	@Autowired(required = false)
	public void setSecurityListener(SecurityListener securityListener) {
		SecurityManager.setSecurityListener(securityListener);
	}

	/**
	 * 注入临时令牌验证模块 Bean
	 * 
	 * @param securityTempInterface SecurityTempInterface对象
	 */
	@Autowired(required = false)
	public void setSecurityTempInterface(SecurityTempInterface securityTempInterface) {
		SecurityManager.setSecurityTempInterface(securityTempInterface);
	}

	/**
	 * 注入 ID 模块 Bean
	 * 
	 * @param securityIdTemplate SecurityIdTemplate对象
	 */
	@Autowired(required = false)
	public void setSecurityIdTemplate(SecurityIdTemplate securityIdTemplate) {
		SecurityIdUtil.securityIdTemplate = securityIdTemplate;
	}

	/**
	 * 注入 Http Basic 认证模块
	 * 
	 * @param securityBasicTemplate SecurityBasicTemplate对象
	 */
	@Autowired(required = false)
	public void setSecurityBasicTemplate(SecurityBasicTemplate securityBasicTemplate) {
		SecurityBasicUtil.securityBasicTemplate = securityBasicTemplate;
	}
	
	/**
	 * 注入 单点登录模块 Bean
	 * 
	 * @param securitySsoTemplate SecuritySsoTemplate对象
	 */
	@Autowired(required = false)
	public void setSecuritySsoTemplate(SecuritySsoTemplate securitySsoTemplate) {
		SecuritySsoUtil.securitySsoTemplate = securitySsoTemplate;
	}

	/**
	 * 注入自定义的 LspLogic 
	 * @param lspLogic / 
	 */
	@Autowired(required = false)
	public void setLspLogic(LspLogic lspLogic) {
		LspUtil.setLspLogic(lspLogic);
	}
	
	/**
	 * 利用自动注入特性，获取Spring框架内部使用的路由匹配器
	 * 
	 * @param pathMatcher 要设置的 pathMatcher
	 */
	@Autowired(required = false)
	@Qualifier("mvcPathMatcher")
	public void setPathMatcher(PathMatcher pathMatcher) {
		PathMatcherHolder.setPathMatcher(pathMatcher);
	}

}

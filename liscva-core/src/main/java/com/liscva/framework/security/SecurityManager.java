package com.liscva.framework.security;

import com.liscva.framework.core.exception.CoreException;
import com.liscva.framework.core.util.FoxUtil;
import com.liscva.framework.security.action.SecurityInfoAction;
import com.liscva.framework.security.action.SecurityInfoActionDefaultImpl;
import com.liscva.framework.security.config.SecurityConfig;
import com.liscva.framework.security.config.SecurityConfigFactory;
import com.liscva.framework.security.context.SecurityContext;
import com.liscva.framework.security.context.SecurityContextDefaultImpl;
import com.liscva.framework.security.dao.SecurityInfoDao;
import com.liscva.framework.security.dao.SecurityInfoDaoDefaultImpl;
import com.liscva.framework.security.listener.SecurityListener;
import com.liscva.framework.security.listener.SecurityListenerDefaultImpl;
import com.liscva.framework.security.lsp.*;
import com.liscva.framework.security.temp.SecurityTempDefaultImpl;
import com.liscva.framework.security.temp.SecurityTempInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理 Security模块 所有全局组件
 */
@SuppressWarnings("deprecation")
public class SecurityManager {

	/**
	 * 配置文件 Bean 
	 */
	public volatile static SecurityConfig config;
	public static void setConfig(SecurityConfig config) {
		SecurityManager.config = config;
		if(config.getIsPrint()) {
			FoxUtil.printWelComeInfo();
		}
		// 调用一次StpUtil中的方法，保证其可以尽早的初始化 LspLogic 
		LspUtil.getLoginType();
	}
	public static SecurityConfig getConfig() {
		if (config == null) {
			synchronized (SecurityManager.class) {
				if (config == null) {
					setConfig(SecurityConfigFactory.createConfig());
				}
			}
		}
		return config;
	}


	/**
	 * 上下文Context Bean
	 */
	private volatile static SecurityContext securityContext;
	public static void setSecurityContext(SecurityContext securityContext) {
		SecurityManager.securityContext = securityContext;
	}

	/**
	 * 获取一个可用的SecurityTokenContext
	 * @return /
	 */
	public static SecurityContext getSecurityContext() {

		// s1. 一级Context可用时返回一级Context
		if(securityContext != null) {
			return securityContext;
		}
		// s3. 都不行，就返回默认的 Context
		return SecurityContextDefaultImpl.defaultContext;
	}


	/**
	 * 临时令牌验证模块 Bean
	 */
	private volatile static SecurityTempInterface securityTempInterface;
	public static void setSecurityTempInterface(SecurityTempInterface securityTempInterface) {
		SecurityManager.securityTempInterface = securityTempInterface;
	}
	public static SecurityTempInterface getSecurityTempInterface() {
		if (securityTempInterface == null) {
			synchronized (SecurityManager.class) {
				if (securityTempInterface == null) {
					setSecurityTempInterface(new SecurityTempDefaultImpl());
				}
			}
		}
		return securityTempInterface;
	}

	/**
	 * LspLogic集合, 记录框架所有成功初始化的LspLogic
	 */
	public static Map<String, LspLogic> stpLogicMap = new HashMap<String, LspLogic>();

	/**
	 * 向集合中 put 一个 LspLogic
	 * @param lspLogic LspLogic
	 */
	public static void putLspLogic(LspLogic lspLogic) {
		stpLogicMap.put(lspLogic.getLoginType(), lspLogic);
	}

	/**
	 * 根据 LoginType 获取对应的LspLogic，如果不存在则抛出异常
	 * @param loginType 对应的账号类型
	 * @return 对应的LspLogic
	 */
	public static LspLogic getLspLogic(String loginType) {
		// 如果type为空则返回框架内置的
		if(loginType == null || loginType.isEmpty()) {
			return LspUtil.lspLogic;
		}

		// 从SecurityManager中获取
		LspLogic lspLogic = stpLogicMap.get(loginType);
		if(lspLogic == null) {
			/*
			 * 此时有两种情况会造成 LspLogic == null
			 * 1. loginType拼写错误，请改正 （建议使用常量）
			 * 2. 自定义StpUtil尚未初始化（静态类中的属性至少一次调用后才会初始化），解决方法两种
			 * 		(1) 从main方法里调用一次
			 * 		(2) 在自定义StpUtil类加上类似 @Component 的注解让容器启动时扫描到自动初始化
			 */
			throw new CoreException("未能获取对应LspLogic，type="+ loginType);
		}

		// 返回
		return lspLogic;
	}


	/**
	 * 持久化 Bean,用来将Security信息持久化
	 */
	private volatile static SecurityInfoDao securityInfoDao;
	public static void setSecurityInfoDao(SecurityInfoDao securityInfoDao) {
		if((SecurityManager.securityInfoDao instanceof SecurityInfoDaoDefaultImpl)) {
			((SecurityInfoDaoDefaultImpl)SecurityManager.securityInfoDao).endRefreshThread();
		}
		SecurityManager.securityInfoDao = securityInfoDao;
	}
	public static SecurityInfoDao getSecurityInfoDao() {
		if (securityInfoDao == null) {
			synchronized (SecurityManager.class) {
				if (securityInfoDao == null) {
					setSecurityInfoDao(new SecurityInfoDaoDefaultImpl());
				}
			}
		}
		return securityInfoDao;
	}

	/**
	 * 框架行为 Bean
	 */
	private volatile static SecurityInfoAction securityInfoAction;
	public static void setSecurityInfoAction(SecurityInfoAction securityInfoAction) {
		SecurityManager.securityInfoAction = securityInfoAction;
	}
	public static SecurityInfoAction getSecurityInfoAction() {
		if (securityInfoAction == null) {
			synchronized (SecurityManager.class) {
				if (securityInfoAction == null) {
					setSecurityInfoAction(new SecurityInfoActionDefaultImpl());
				}
			}
		}
		return securityInfoAction;
	}


	/**
	 * 侦听器 Bean
	 */
	private volatile static SecurityListener securityListener;
	public static void setSecurityListener(SecurityListener securityListener) {
		SecurityManager.securityListener = securityListener;
	}
	public static SecurityListener getSecurityListener() {
		if (securityListener == null) {
			synchronized (SecurityManager.class) {
				if (securityListener == null) {
					setSecurityListener(new SecurityListenerDefaultImpl());
				}
			}
		}
		return securityListener;
	}


	/**
	 * 权限认证 Bean
	 */
	private volatile static LspInterface lspInterface;
	public static void setStpInterface(LspInterface lspInterface) {
		SecurityManager.lspInterface = lspInterface;
	}
	public static LspInterface getStpInterface() {
		if (lspInterface == null) {
			synchronized (SecurityManager.class) {
				if (lspInterface == null) {
					setStpInterface(new LspInterfaceDefaultImpl());
				}
			}
		}
		return lspInterface;
	}
}

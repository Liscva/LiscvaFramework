package com.liscva.framework.security.sso;

import com.liscva.framework.core.connect.DefaultPublicConnect;
import com.liscva.framework.core.connect.FailPublicConnect;
import com.liscva.framework.core.context.model.LiscvaRequest;
import com.liscva.framework.core.context.model.LiscvaResponse;
import com.liscva.framework.core.exception.CoreException;
import com.liscva.framework.core.util.FoxUtil;
import com.liscva.framework.security.SecurityManager;
import com.liscva.framework.security.config.SecuritySsoConfig;
import com.liscva.framework.security.context.SecurityHolder;
import com.liscva.framework.security.sso.SecuritySsoConsts.Api;
import com.liscva.framework.security.sso.SecuritySsoConsts.ParamName;
import com.liscva.framework.security.lsp.LspLogic;

/**
 * SSO 单点登录请求处理类封装
 *
 */
public class SecuritySsoHandle {

	/**
	 * 处理Server端所有请求 
	 * @return 处理结果 
	 */
	public static Object serverRequest() {
		
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();

		// ------------------ 路由分发 ------------------ 
		
		// SSO-Server端：授权地址 
		if(req.isPath(Api.ssoAuth)) {
			return ssoAuth();
		}

		// SSO-Server端：RestAPI 登录接口 
		if(req.isPath(Api.ssoDoLogin)) {
			return ssoDoLogin();
		}

		// SSO-Server端：校验ticket 获取账号id 
		if(req.isPath(Api.ssoCheckTicket) && cfg.isHttp) {
			return ssoCheckTicket();
		}
		
		// SSO-Server端：单点注销 [模式一]   (不带loginId参数) 
		if(req.isPath(Api.ssoLogout) && cfg.isSlo && req.hasParam(ParamName.loginId) == false) {
			return ssoServerLogoutType1();
		}
		
		// SSO-Server端：单点注销 [模式三]   (带loginId参数) 
		if(req.isPath(Api.ssoLogout) && cfg.isHttp && cfg.isSlo && req.hasParam(ParamName.loginId)) {
			return ssoServerLogout();
		}
		
		// 默认返回 
		return SecuritySsoConsts.NOT_HANDLE;
	}
	
	/**
	 * SSO-Server端：授权地址
	 * @return 处理结果 
	 */
	public static Object ssoAuth() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		LiscvaResponse res = SecurityHolder.getResponse();
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();
		LspLogic lspLogic = SecuritySsoUtil.securitySsoTemplate.lspLogic;
		
		// ---------- 此处有两种情况分开处理：
		// ---- 情况1：在SSO认证中心尚未登录，需要先去登录 
		if(lspLogic.isLogin() == false) {
			return cfg.notLoginView.get();
		}
		// ---- 情况2：在SSO认证中心已经登录，需要重定向回 Client 端，而这又分为两种方式：
		String mode = req.getParam(ParamName.mode, "");
		
		// 方式1：直接重定向回Client端 (mode=simple)
		if(mode.equals(SecuritySsoConsts.MODE_SIMPLE)) {
			String redirect = req.getParam(ParamName.redirect);
			SecuritySsoUtil.checkRedirectUrl(redirect);
			return res.redirect(redirect);
		} else {
			// 方式2：带着ticket参数重定向回Client端 (mode=ticket)  
			String redirectUrl = SecuritySsoUtil.buildRedirectUrl(lspLogic.getLoginId(), req.getParam(ParamName.redirect));
			return res.redirect(redirectUrl);
		}
	}

	/**
	 * SSO-Server端：RestAPI 登录接口 
	 * @return 处理结果 
	 */
	public static Object ssoDoLogin() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();
		
		// 处理 
		return cfg.doLoginHandle.apply(req.getParam(ParamName.name), req.getParam(ParamName.pwd));
	}

	/**
	 * SSO-Server端：校验ticket 获取账号id 
	 * @return 处理结果 
	 */
	public static Object ssoCheckTicket() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		
		// 获取参数 
		String ticket = req.getParam(ParamName.ticket);
		String sloCallback = req.getParam(ParamName.ssoLogoutCall);
		
		// 校验ticket，获取对应的账号id 
		Object loginId = SecuritySsoUtil.checkTicket(ticket);
		
		// 注册此客户端的单点注销回调URL 
		SecuritySsoUtil.registerSloCallbackUrl(loginId, sloCallback);
		
		// 返回给Client端 
		return loginId;
	}

	/**
	 * SSO-Server端：单点注销 [模式一] 
	 * @return 处理结果 
	 */
	public static Object ssoServerLogoutType1() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		LiscvaResponse res = SecurityHolder.getResponse();
		LspLogic lspLogic = SecuritySsoUtil.securitySsoTemplate.lspLogic;
		
		// 开始处理 
		lspLogic.logout();
		
		// 返回
		return ssoLogoutBack(req, res);
	}

	/**
	 * SSO-Server端：单点注销 [模式三] 
	 * @return 处理结果 
	 */
	public static Object ssoServerLogout() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();
		LspLogic lspLogic = SecuritySsoUtil.securitySsoTemplate.lspLogic;
		
		// 获取参数 
		String loginId = req.getParam(ParamName.loginId);
		String secretkey = req.getParam(ParamName.secretkey);
		
		// 遍历通知Client端注销会话 
		// step.1 校验秘钥 
		SecuritySsoUtil.checkSecretkey(secretkey);
		
		// step.2 遍历通知Client端注销会话 
		SecuritySsoUtil.forEachSloUrl(loginId, url -> cfg.sendHttp.apply(url));
		
		// step.3 Server端注销 
		lspLogic.logout(loginId);
        	
        // 完成
        return SecuritySsoConsts.OK;
	}
	

	/**
	 * 处理Client端所有请求 
	 * @return 处理结果 
	 */
	public static Object clientRequest() {

		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();

		// ------------------ 路由分发 ------------------ 
		
		// ---------- SSO-Client端：登录地址 
		if(req.isPath(Api.ssoLogin)) {
			return ssoLogin();
		}

		// ---------- SSO-Client端：单点注销 [模式二]
		if(req.isPath(Api.ssoLogout) && cfg.isSlo && cfg.isHttp == false) {
			return ssoLogoutType2();
		}

		// ---------- SSO-Client端：单点注销 [模式三]
		if(req.isPath(Api.ssoLogout) && cfg.isSlo && cfg.isHttp) {
			return ssoLogoutType3();
		}

		// ---------- SSO-Client端：单点注销的回调 	[模式三]
		if(req.isPath(Api.ssoLogoutCall) && cfg.isSlo && cfg.isHttp) {
			return ssoLogoutCall();
		}
		
		// 默认返回 
		return SecuritySsoConsts.NOT_HANDLE;
	}
	
	/**
	 * SSO-Client端：登录地址 
	 * @return 处理结果 
	 */
	public static Object ssoLogin() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		LiscvaResponse res = SecurityHolder.getResponse();
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();
		LspLogic lspLogic = SecuritySsoUtil.securitySsoTemplate.lspLogic;
		
		// 获取参数 
		String back = req.getParam(ParamName.back, "/");
		String ticket = req.getParam(ParamName.ticket);
		
		// 如果当前Client端已经登录，则无需访问SSO认证中心，可以直接返回 
		if(lspLogic.isLogin()) {
			return res.redirect(back);
		}
		/*
		 * 此时有两种情况: 
		 * 情况1：ticket无值，说明此请求是Client端访问，需要重定向至SSO认证中心 
		 * 情况2：ticket有值，说明此请求从SSO认证中心重定向而来，需要根据ticket进行登录 
		 */
		if(ticket == null) {
			String serverAuthUrl = SecuritySsoUtil.buildServerAuthUrl(SecurityHolder.getRequest().getUrl(), back);
			return res.redirect(serverAuthUrl);
		} else {
			// ------- 1、校验ticket，获取账号id 
			Object loginId = checkTicket(ticket, Api.ssoLogin);
			
			// Be: 如果开发者自定义了处理逻辑 
			if(cfg.ticketResultHandle != null) {
				return cfg.ticketResultHandle.apply(loginId, back);
			}
			// ------- 2、如果loginId有值，说明ticket有效，进行登录并重定向至back地址 
			if(loginId != null ) {
				lspLogic.login(loginId);
				return res.redirect(back);
			} else {
				// 如果ticket无效: 
				throw new CoreException("无效ticket：" + ticket);
			}
		}
	}

	/**
	 * SSO-Client端：单点注销 [模式二] 
	 * @return 处理结果 
	 */
	public static Object ssoLogoutType2() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		LiscvaResponse res = SecurityHolder.getResponse();
		LspLogic lspLogic = SecuritySsoUtil.securitySsoTemplate.lspLogic;
		
		// 开始处理 
		lspLogic.logout();
		
		// 返回
		return ssoLogoutBack(req, res);
	}

	/**
	 * SSO-Client端：单点注销 [模式三]  
	 * @return 处理结果 
	 */
	public static Object ssoLogoutType3() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		LiscvaResponse res = SecurityHolder.getResponse();
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();
		LspLogic lspLogic = SecuritySsoUtil.securitySsoTemplate.lspLogic;
		
		// 如果未登录，则无需注销 
        if(lspLogic.isLogin() == false) {
            return DefaultPublicConnect.ok();
        }
        
        // 调用SSO-Server认证中心API，进行注销
        String url = SecuritySsoUtil.buildSloUrl(lspLogic.getLoginId());
        String body = String.valueOf(cfg.sendHttp.apply(url));
        if(SecuritySsoConsts.OK.equals(body) == false) {
            return FailPublicConnect.defaultError("单点注销失败");
        }
        
        // 返回 
        return ssoLogoutBack(req, res);
	}

	/**
	 * SSO-Client端：单点注销的回调  [模式三] 
	 * @return 处理结果 
	 */
	public static Object ssoLogoutCall() {
		// 获取对象 
		LiscvaRequest req = SecurityHolder.getRequest();
		LspLogic lspLogic = SecuritySsoUtil.securitySsoTemplate.lspLogic;
		
		// 获取参数 
		String loginId = req.getParam(ParamName.loginId);
		String secretkey = req.getParam(ParamName.secretkey);
		
		SecuritySsoUtil.checkSecretkey(secretkey);
		lspLogic.logoutByTokenValue(lspLogic.getTokenValueByLoginId(loginId));
        return SecuritySsoConsts.OK;
	}
	
	/**
	 * 封装：单点注销成功后返回结果 
	 * @param req LiscvaRequest对象
	 * @param res LiscvaResponse对象
	 * @return 返回结果 
	 */
	public static Object ssoLogoutBack(LiscvaRequest req, LiscvaResponse res) {
		/* 
		 * 三种情况：
		 * 	1. 有back参数，值为SELF -> 回退一级并刷新 
		 * 	2. 有back参数，值为url -> 跳转到此url地址 
		 * 	3. 无back参数 -> 返回json数据 
		 */
		String back = req.getParam(ParamName.back);
		if(FoxUtil.isNotEmpty(back)) {
			if(back.equals(SecuritySsoConsts.SELF)) {
				return "<script>if(document.referrer != location.href){ location.replace(document.referrer || '/'); }</script>";
			}
			return res.redirect(back);
		} else {
			return DefaultPublicConnect.ok("单点注销成功");
		}
	}

	/**
	 * 封装：校验ticket，取出loginId 
	 * @param ticket ticket码
	 * @param currUri 当前路由的uri，用于计算单点注销回调地址 
	 * @return loginId
	 */
	public static Object checkTicket(String ticket, String currUri) {
		SecuritySsoConfig cfg = SecurityManager.getConfig().getSso();
		// --------- 两种模式 
		if(cfg.isHttp) {
			// 模式三：使用http请求校验ticket 
			String ssoLogoutCall = null; 
			if(cfg.isSlo) {
				ssoLogoutCall = SecurityHolder.getRequest().getUrl().replace(currUri, Api.ssoLogoutCall);
			}
			String checkUrl = SecuritySsoUtil.buildCheckTicketUrl(ticket, ssoLogoutCall);
			Object body = cfg.sendHttp.apply(checkUrl);
			return (FoxUtil.isEmpty(body) ? null : body);
		} else {
			// 模式二：直连Redis校验ticket 
			return SecuritySsoUtil.checkTicket(ticket);
		}
	}
	
}

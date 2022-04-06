package com.liscva.framework.security.config;

import java.io.Serializable;

/**
 * LiscvaSecurity 配置类
 */
public class SecurityConfig implements Serializable {

	private static final long serialVersionUID = -6541180061782004705L;

	/** token名称 (同时也是cookie名称) */
	private String tokenName = "token";

	/** token的长久有效期(单位:秒) 默认30天, -1代表永久 */
	private long timeout = 60 * 60 * 24 * 30;

	/**
	 * token临时有效期 [指定时间内无操作就视为token过期] (单位: 秒), 默认-1 代表不限制
	 * (例如可以设置为1800代表30分钟内无操作就过期)
	 */
	private long activityTimeout = -1;

	/** 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录) */
	private Boolean isConcurrent = true;

	/** 是否尝试从请求体里读取token */
	private Boolean isReadBody = true;

	/** 是否尝试从header里读取token */
	private Boolean isReadHead = true;

	/** 是否尝试从cookie里读取token */
	private Boolean isReadCookie = true;

	/** token风格(默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik) */
	private String tokenStyle = "uuid";

	/** 默认dao层实现类中，每次清理过期数据间隔的时间 (单位: 秒) ，默认值30秒，设置为-1代表不启动定时清理 */
	private int dataRefreshPeriod = 30;

	/** 获取[token专属session]时是否必须登录 (如果配置为true，会在每次获取[token-session]时校验是否登录) */
	private Boolean tokenSessionCheckLogin = true;

	/** 是否打开自动续签 (如果此值为true, 框架会在每次直接或间接调用getLoginId()时进行一次过期检查与续签操作)  */
	private Boolean autoRenew = true;

	/** token前缀, 格式样例(satoken: Bearer xxxx-xxxx-xxxx-xxxx) */
	private String tokenPrefix;

	/** 是否在初始化配置时打印版本字符画 */
	private Boolean isPrint = true;

	/** 是否打印操作日志 */
	private Boolean isLog = false;

	/**
	 * jwt秘钥 (只有集成 jwt 模块时此参数才会生效)   
	 */
	private String jwtSecretKey;
	
	/**
	 * Id-Token的有效期 (单位: 秒)
	 */
	private long idTokenTimeout = 60 * 60 * 24;

	/**
	 * Http Basic 认证的账号和密码 
	 */
	private String basic = "";

	/** 配置当前项目的网络访问地址 */
	private String currDomain;

	/** 是否校验Id-Token（部分rpc插件有效） */
	private Boolean checkIdToken = false;

	/**
	 * Cookie配置对象 
	 */
	public SecurityCookieConfig cookie = new SecurityCookieConfig();
	
	/**
	 * SSO单点登录配置对象 
	 */
	public SecuritySsoConfig sso = new SecuritySsoConfig();
	

	/**
	 * @return token名称 (同时也是cookie名称)
	 */
	public String getTokenName() {
		return tokenName;
	}

	/**
	 * @param tokenName token名称 (同时也是cookie名称)
	 * @return 对象自身
	 */
	public SecurityConfig setTokenName(String tokenName) {
		this.tokenName = tokenName;
		return this;
	}

	/**
	 * @return token的长久有效期(单位:秒) 默认30天, -1代表永久
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout token的长久有效期(单位:秒) 默认30天, -1代表永久
	 * @return 对象自身
	 */
	public SecurityConfig setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * @return token临时有效期 [指定时间内无操作就视为token过期] (单位: 秒), 默认-1 代表不限制
	 *         (例如可以设置为1800代表30分钟内无操作就过期)
	 */
	public long getActivityTimeout() {
		return activityTimeout;
	}

	/**
	 * @param activityTimeout token临时有效期 [指定时间内无操作就视为token过期] (单位: 秒), 默认-1 代表不限制
	 *                        (例如可以设置为1800代表30分钟内无操作就过期)
	 * @return 对象自身
	 */
	public SecurityConfig setActivityTimeout(long activityTimeout) {
		this.activityTimeout = activityTimeout;
		return this;
	}

	/**
	 * @return 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录) 
	 */
	public Boolean getIsConcurrent() {
		return isConcurrent;
	}

	/**
	 * @param isConcurrent 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
	 * @return 对象自身
	 */
	public SecurityConfig setIsConcurrent(Boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
		return this;
	}

	/**
	 * @return 是否尝试从请求体里读取token
	 */
	public Boolean getIsReadBody() {
		return isReadBody;
	}

	/**
	 * @param isReadBody 是否尝试从请求体里读取token
	 * @return 对象自身
	 */
	public SecurityConfig setIsReadBody(Boolean isReadBody) {
		this.isReadBody = isReadBody;
		return this;
	}

	/**
	 * @return 是否尝试从header里读取token
	 */
	public Boolean getIsReadHead() {
		return isReadHead;
	}

	/**
	 * @param isReadHead 是否尝试从header里读取token
	 * @return 对象自身
	 */
	public SecurityConfig setIsReadHead(Boolean isReadHead) {
		this.isReadHead = isReadHead;
		return this;
	}

	/**
	 * @return 是否尝试从cookie里读取token
	 */
	public Boolean getIsReadCookie() {
		return isReadCookie;
	}

	/**
	 * @param isReadCookie 是否尝试从cookie里读取token
	 * @return 对象自身
	 */
	public SecurityConfig setIsReadCookie(Boolean isReadCookie) {
		this.isReadCookie = isReadCookie;
		return this;
	}

	/**
	 * @return token风格(默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik)
	 */
	public String getTokenStyle() {
		return tokenStyle;
	}

	/**
	 * @param tokenStyle token风格(默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik)
	 * @return 对象自身
	 */
	public SecurityConfig setTokenStyle(String tokenStyle) {
		this.tokenStyle = tokenStyle;
		return this;
	}

	/**
	 * @return 默认dao层实现类中，每次清理过期数据间隔的时间 (单位: 秒) ，默认值30秒，设置为-1代表不启动定时清理
	 */
	public int getDataRefreshPeriod() {
		return dataRefreshPeriod;
	}

	/**
	 * @param dataRefreshPeriod 默认dao层实现类中，每次清理过期数据间隔的时间 (单位: 秒)
	 *                          ，默认值30秒，设置为-1代表不启动定时清理
	 * @return 对象自身
	 */
	public SecurityConfig setDataRefreshPeriod(int dataRefreshPeriod) {
		this.dataRefreshPeriod = dataRefreshPeriod;
		return this;
	}

	/**
	 * @return 获取[token专属session]时是否必须登录 (如果配置为true，会在每次获取[token-session]时校验是否登录)
	 */
	public Boolean getTokenSessionCheckLogin() {
		return tokenSessionCheckLogin;
	}

	/**
	 * @param tokenSessionCheckLogin 获取[token专属session]时是否必须登录
	 *                               (如果配置为true，会在每次获取[token-session]时校验是否登录)
	 * @return 对象自身
	 */
	public SecurityConfig setTokenSessionCheckLogin(Boolean tokenSessionCheckLogin) {
		this.tokenSessionCheckLogin = tokenSessionCheckLogin;
		return this;
	}

	/**
	 * @return 是否打开了自动续签 (如果此值为true, 框架会在每次直接或间接调用getLoginId()时进行一次过期检查与续签操作) 
	 */
	public Boolean getAutoRenew() {
		return autoRenew;
	}

	/**
	 * @param autoRenew 是否打开自动续签 (如果此值为true, 框架会在每次直接或间接调用getLoginId()时进行一次过期检查与续签操作) 
	 * @return 对象自身
	 */
	public SecurityConfig setAutoRenew(Boolean autoRenew) {
		this.autoRenew = autoRenew;
		return this;
	}

	/**
	 * @return token前缀, 格式样例(satoken: Bearer xxxx-xxxx-xxxx-xxxx)
	 */
	public String getTokenPrefix() {
		return tokenPrefix;
	}

	/**
	 * @param tokenPrefix token前缀, 格式样例(satoken: Bearer xxxx-xxxx-xxxx-xxxx)
	 * @return 对象自身
	 */
	public SecurityConfig setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
		return this;
	}
	
	/**
	 * @return 是否在初始化配置时打印版本字符画
	 */
	public Boolean getIsPrint() {
		return isPrint;
	}

	/**
	 * @param isPrint 是否在初始化配置时打印版本字符画
	 * @return 对象自身
	 */
	public SecurityConfig setIsPrint(Boolean isPrint) {
		this.isPrint = isPrint;
		return this;
	}

	/**
	 * @return 是否打印操作日志
	 */
	public Boolean getIsLog() {
		return isLog;
	}

	/**
	 * @param isLog 是否打印操作日志
	 * @return 对象自身
	 */
	public SecurityConfig setIsLog(Boolean isLog) {
		this.isLog = isLog;
		return this;
	}

	/**
	 * @return jwt秘钥 (只有集成 jwt 模块时此参数才会生效)    
	 */
	public String getJwtSecretKey() {
		return jwtSecretKey;
	}

	/**
	 * @param jwtSecretKey jwt秘钥 (只有集成 jwt 模块时此参数才会生效)  
	 * @return 对象自身
	 */
	public SecurityConfig setJwtSecretKey(String jwtSecretKey) {
		this.jwtSecretKey = jwtSecretKey;
		return this;
	}

	/**
	 * @return Id-Token的有效期 (单位: 秒)
	 */
	public long getIdTokenTimeout() {
		return idTokenTimeout;
	}

	/**
	 * @param idTokenTimeout Id-Token的有效期 (单位: 秒)
	 * @return 对象自身
	 */
	public SecurityConfig setIdTokenTimeout(long idTokenTimeout) {
		this.idTokenTimeout = idTokenTimeout;
		return this;
	}

	/**
	 * @return Http Basic 认证的账号和密码 
	 */
	public String getBasic() {
		return basic;
	}

	/**
	 * @param basic Http Basic 认证的账号和密码 
	 * @return 对象自身
	 */
	public SecurityConfig setBasic(String basic) {
		this.basic = basic;
		return this;
	}

	/**
	 * @return 配置当前项目的网络访问地址
	 */
	public String getCurrDomain() {
		return currDomain;
	}

	/**
	 * @param currDomain 配置当前项目的网络访问地址
	 * @return 对象自身
	 */
	public SecurityConfig setCurrDomain(String currDomain) {
		this.currDomain = currDomain;
		return this;
	}

	/**
	 * @return 是否校验Id-Token（部分rpc插件有效）
	 */
	public Boolean getCheckIdToken() {
		return checkIdToken;
	}

	/**
	 * @param checkIdToken 是否校验Id-Token（部分rpc插件有效）
	 * @return 对象自身 
	 */
	public SecurityConfig setCheckIdToken(Boolean checkIdToken) {
		this.checkIdToken = checkIdToken;
		return this;
	}
	
	/**
	 * @return SSO单点登录配置对象 
	 */
	public SecuritySsoConfig getSso() {
		return sso;
	}
	
	/**
	 * @param sso SSO单点登录配置对象 
	 * @return 对象自身 
	 */
	public SecurityConfig setSso(SecuritySsoConfig sso) {
		this.sso = sso;
		return this;
	}
	
	/**
	 * @return Cookie 全局配置对象
	 */
	public SecurityCookieConfig getCookie() {
		return cookie;
	}

	/**
	 * @param cookie Cookie 全局配置对象
	 * @return 对象自身 
	 */
	public SecurityConfig setCookie(SecurityCookieConfig cookie) {
		this.cookie = cookie;
		return this;
	}
	
	@Override
	public String toString() {
		return "LiscvaSecurityConfig ["
				+ "tokenName=" + tokenName 
				+ ", timeout=" + timeout 
				+ ", activityTimeout=" + activityTimeout
				+ ", isConcurrent=" + isConcurrent
				+ ", isReadBody=" + isReadBody
				+ ", isReadHead=" + isReadHead 
				+ ", isReadCookie=" + isReadCookie
				+ ", tokenStyle=" + tokenStyle
				+ ", dataRefreshPeriod=" + dataRefreshPeriod 
				+ ", tokenSessionCheckLogin=" + tokenSessionCheckLogin
				+ ", autoRenew=" + autoRenew 
				+ ", tokenPrefix=" + tokenPrefix
				+ ", isPrint=" + isPrint 
				+ ", isLog=" + isLog 
				+ ", jwtSecretKey=" + jwtSecretKey 
				+ ", idTokenTimeout=" + idTokenTimeout 
				+ ", basic=" + basic 
				+ ", currDomain=" + currDomain 
				+ ", checkIdToken=" + checkIdToken 
				+ ", sso=" + sso 
				+ ", cookie=" + cookie 
				+ "]";
	}

	
	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 setIsConcurrent() ，使用方式保持不变 </h1>
	 * @param allowConcurrentLogin see note
	 * @return  see note
	 */
	@Deprecated
	public SecurityConfig setAllowConcurrentLogin(Boolean allowConcurrentLogin) {
		this.isConcurrent = allowConcurrentLogin;
		return this;
	}

	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 setIsConcurrent() ，使用方式保持不变 </h1>
	 * @param isV see note
	 * @return see note
	 */
	@Deprecated
	public SecurityConfig setIsV(Boolean isV) {
		this.isPrint = isV;
		return this;
	}

	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 getCookie().getDomain() ，使用方式保持不变 </h1>
	 * @return 写入Cookie时显式指定的作用域, 常用于单点登录二级域名共享Cookie的场景
	 */
	@Deprecated
	public String getCookieDomain() {
		return getCookie().getDomain();
	}

	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 getCookie().setDomain() ，使用方式保持不变 </h1>
	 * @param cookieDomain 写入Cookie时显式指定的作用域, 常用于单点登录二级域名共享Cookie的场景
	 * @return 对象自身
	 */
	@Deprecated
	public SecurityConfig setCookieDomain(String cookieDomain) {
		this.getCookie().setDomain(cookieDomain);
		return this;
	}


}

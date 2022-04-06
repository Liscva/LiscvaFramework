package com.liscva.config.at;


import com.liscva.framework.core.fun.CoreFunction;
import com.liscva.framework.security.SecurityManager;
import com.liscva.framework.security.session.SecurityInfoSession;
import com.liscva.framework.security.lsp.SecurityInfo;
import com.liscva.framework.security.lsp.SecurityLoginModel;
import com.liscva.framework.security.lsp.LspLogic;
import com.liscva.framework.security.lsp.LspUtil;

import java.util.List;

/**
 * 权限认证工具类 (User版)
 */
public class StpUserUtil {
	
	/**
	 * 账号类型标识 
	 */
	public static final String TYPE = "user";
	
	/**
	 * 底层的 LspLogic 对象  
	 */
	public static LspLogic lspLogic = new LspLogic(TYPE);

	/**
	 * 获取当前 LspLogic 的账号类型
	 * @return See Note 
	 */
	public static String getLoginType(){
		return lspLogic.getLoginType();
	}

	/**
	 * 重置 LspLogic 对象
	 * @param lspLogic / 
	 */
	public static void setLspLogic(LspLogic lspLogic) {
		LspUtil.lspLogic = lspLogic;
		// 防止自定义 stpLogic 被覆盖 
		SecurityManager.putLspLogic(lspLogic);
	}
	
	
	// =================== 获取token 相关 ===================

	/**
	 * 返回token名称 
	 * @return 此LspLogic的token名称
	 */
	public static String getTokenName() {
 		return lspLogic.getTokenName();
 	}

 	/**
 	 * 在当前会话写入当前TokenValue 
 	 * @param tokenValue token值 
 	 */
	public static void setTokenValue(String tokenValue){
		lspLogic.setTokenValue(tokenValue);
	}
	
 	/**
 	 * 在当前会话写入当前TokenValue 
 	 * @param tokenValue token值 
 	 * @param cookieTimeout Cookie存活时间(秒)
 	 */
	public static void setTokenValue(String tokenValue, int cookieTimeout){
		lspLogic.setTokenValue(tokenValue, cookieTimeout);
	}
 	
	/**
	 * 获取当前TokenValue
	 * @return 当前tokenValue
	 */
	public static String getTokenValue() {
		return lspLogic.getTokenValue();
	}

	/**
	 * 获取当前TokenValue (不裁剪前缀) 
	 * @return / 
	 */
	public static String getTokenValueNotCut(){
		return lspLogic.getTokenValueNotCut();
	}

	/**
	 * 获取当前会话的Token信息 
	 * @return token信息 
	 */
	public static SecurityInfo getTokenInfo() {
		return lspLogic.getSecurityInfo();
	}

	
	// =================== 登录相关操作 ===================

	// --- 登录 
	
	/**
	 * 会话登录 
	 * @param id 账号id，建议的类型：（long | int | String）
	 */
	public static void login(Object id) {
		lspLogic.login(id);
	}

	/**
	 * 会话登录，并指定登录设备 
	 * @param id 账号id，建议的类型：（long | int | String）
	 * @param device 设备标识 
	 */
	public static void login(Object id, String device) {
		lspLogic.login(id, device);
	}

	/**
	 * 会话登录，并指定是否 [记住我] 
	 * @param id 账号id，建议的类型：（long | int | String）
	 * @param isLastingCookie 是否为持久Cookie 
	 */
	public static void login(Object id, boolean isLastingCookie) {
		lspLogic.login(id, isLastingCookie);
	}

	/**
	 * 会话登录，并指定所有登录参数Model 
	 * @param id 登录id，建议的类型：（long | int | String）
	 * @param loginModel 此次登录的参数Model 
	 */
	public static void login(Object id, SecurityLoginModel loginModel) {
		lspLogic.login(id, loginModel);
	}

	/**
	 * 创建指定账号id的登录会话 
	 * @param id 登录id，建议的类型：（long | int | String）
	 * @return 返回会话令牌 
	 */
	public static String createLoginSession(Object id) {
		return lspLogic.createLoginSession(id);
	}
	
	/**
	 * 创建指定账号id的登录会话
	 * @param id 登录id，建议的类型：（long | int | String）
	 * @param loginModel 此次登录的参数Model 
	 * @return 返回会话令牌 
	 */
	public static String createLoginSession(Object id, SecurityLoginModel loginModel) {
		return lspLogic.createLoginSession(id, loginModel);
	}
	
	// --- 注销 
	
	/** 
	 * 会话注销 
	 */
	public static void logout() {
		lspLogic.logout();
	}

	/**
	 * 会话注销，根据账号id 
	 * @param loginId 账号id 
	 */
	public static void logout(Object loginId) {
		lspLogic.logout(loginId);
	}

	/**
	 * 会话注销，根据账号id 和 设备标识 
	 * 
	 * @param loginId 账号id 
	 * @param device 设备标识 (填null代表所有注销设备) 
	 */
	public static void logout(Object loginId, String device) {
		lspLogic.logout(loginId, device);
	}
	
	/**
	 * 会话注销，根据指定 Token 
	 * 
	 * @param tokenValue 指定token
	 */
	public static void logoutByTokenValue(String tokenValue) {
		lspLogic.logoutByTokenValue(tokenValue);
	}
	
	/**
	 * 踢人下线，根据账号id 
	 * <p> 当对方再次访问系统时，会抛出NotLoginException异常，场景值=-5 </p>
	 * 
	 * @param loginId 账号id 
	 */
	public static void kickout(Object loginId) {
		lspLogic.kickout(loginId);
	}
	
	/**
	 * 踢人下线，根据账号id 和 设备标识 
	 * <p> 当对方再次访问系统时，会抛出NotLoginException异常，场景值=-5 </p>
	 * 
	 * @param loginId 账号id 
	 * @param device 设备标识 (填null代表踢出所有设备) 
	 */
	public static void kickout(Object loginId, String device) {
		lspLogic.kickout(loginId, device);
	}

	/**
	 * 踢人下线，根据指定 Token 
	 * <p> 当对方再次访问系统时，会抛出NotLoginException异常，场景值=-5 </p>
	 * 
	 * @param tokenValue 指定token
	 */
	public static void kickoutByTokenValue(String tokenValue) {
		lspLogic.kickoutByTokenValue(tokenValue);
	}
	
	/**
	 * 顶人下线，根据账号id 和 设备标识 
	 * <p> 当对方再次访问系统时，会抛出NotLoginException异常，场景值=-4 </p>
	 * 
	 * @param loginId 账号id 
	 * @param device 设备标识 (填null代表顶替所有设备) 
	 */
	public static void replaced(Object loginId, String device) {
		lspLogic.replaced(loginId, device);
	}
	
	
	// 查询相关

	/** 
 	 * 当前会话是否已经登录 
 	 * @return 是否已登录 
 	 */
	public static boolean isLogin() {
		return lspLogic.isLogin();
	}

	/** 
 	 * 检验当前会话是否已经登录，如未登录，则抛出异常 
 	 */
 	public static void checkLogin() {
 		lspLogic.checkLogin();
 	}

 	/** 
 	 * 获取当前会话账号id, 如果未登录，则抛出异常 
 	 * @return 账号id
 	 */
	public static Object getLoginId() {
		return lspLogic.getLoginId();
	}

	/** 
	 * 获取当前会话账号id, 如果未登录，则返回默认值 
	 * @param <T> 返回类型 
	 * @param defaultValue 默认值
	 * @return 登录id 
	 */
	public static <T> T getLoginId(T defaultValue) {
		return lspLogic.getLoginId(defaultValue);
	}

	/** 
	 * 获取当前会话账号id, 如果未登录，则返回null 
	 * @return 账号id 
	 */
	public static Object getLoginIdDefaultNull() {
		return lspLogic.getLoginIdDefaultNull();
 	}

	/** 
	 * 获取当前会话账号id, 并转换为String类型
	 * @return 账号id 
	 */
	public static String getLoginIdAsString() {
		return lspLogic.getLoginIdAsString();
	}

	/** 
	 * 获取当前会话账号id, 并转换为int类型
	 * @return 账号id 
	 */
	public static int getLoginIdAsInt() {
		return lspLogic.getLoginIdAsInt();
	}

	/**
	 * 获取当前会话账号id, 并转换为long类型 
	 * @return 账号id 
	 */
	public static long getLoginIdAsLong() {
		return lspLogic.getLoginIdAsLong();
	}

	/** 
 	 * 获取指定Token对应的账号id，如果未登录，则返回 null 
 	 * @param tokenValue token
 	 * @return 账号id
 	 */
 	public static Object getLoginIdByToken(String tokenValue) {
 		return lspLogic.getLoginIdByToken(tokenValue);
 	}
	
 	
	// =================== User-Session 相关 ===================

 	/** 
	 * 获取指定账号id的Session, 如果Session尚未创建，isCreate=是否新建并返回
	 * @param loginId 账号id
	 * @param isCreate 是否新建
	 * @return Session对象
	 */
	public static SecurityInfoSession getSessionByLoginId(Object loginId, boolean isCreate) {
		return lspLogic.getSessionByLoginId(loginId, isCreate);
	}

	/** 
	 * 获取指定key的Session, 如果Session尚未创建，则返回null
	 * @param sessionId SessionId
	 * @return Session对象 
	 */
	public static SecurityInfoSession getSessionBySessionId(String sessionId) {
		return lspLogic.getSessionBySessionId(sessionId);
	}

	/** 
	 * 获取指定账号id的Session，如果Session尚未创建，则新建并返回 
	 * @param loginId 账号id 
	 * @return Session对象 
	 */
	public static SecurityInfoSession getSessionByLoginId(Object loginId) {
		return lspLogic.getSessionByLoginId(loginId);
	}

	/** 
	 * 获取当前会话的Session, 如果Session尚未创建，isCreate=是否新建并返回 
	 * @param isCreate 是否新建 
	 * @return Session对象 
	 */
	public static SecurityInfoSession getSession(boolean isCreate) {
		return lspLogic.getSession(isCreate);
	}

	/** 
	 * 获取当前会话的Session，如果Session尚未创建，则新建并返回 
	 * @return Session对象 
	 */
	public static SecurityInfoSession getSession() {
		return lspLogic.getSession();
	}

	
	// =================== Token-Session 相关 ===================  
	
	/** 
	 * 获取指定Token-Session，如果Session尚未创建，则新建并返回 
	 * @param tokenValue Token值
	 * @return Session对象  
	 */
	public static SecurityInfoSession getTokenSessionByToken(String tokenValue) {
		return lspLogic.getTokenSessionByToken(tokenValue);
	}
	
	/** 
	 * 获取当前Token-Session，如果Session尚未创建，则新建并返回
	 * @return Session对象 
	 */
	public static SecurityInfoSession getTokenSession() {
		return lspLogic.getTokenSession();
	}


	// =================== [临时有效期] 验证相关 ===================  

	/**
 	 * 检查当前token 是否已经[临时过期]，如果已经过期则抛出异常  
 	 */
 	public static void checkActivityTimeout() {
 		lspLogic.checkActivityTimeout();
 	}

 	/**
 	 * 续签当前token：(将 [最后操作时间] 更新为当前时间戳) 
 	 * <h1>请注意: 即时token已经 [临时过期] 也可续签成功，
 	 * 如果此场景下需要提示续签失败，可在此之前调用 checkActivityTimeout() 强制检查是否过期即可 </h1>
 	 */
 	public static void updateLastActivityToNow() {
 		lspLogic.updateLastActivityToNow();
 	}
 	

	// =================== 过期时间相关 ===================  

 	/**
 	 * 获取当前登录者的 token 剩余有效时间 (单位: 秒)
 	 * @return token剩余有效时间
 	 */
 	public static long getTokenTimeout() {
 		return lspLogic.getTokenTimeout();
 	}
 	
 	/**
 	 * 获取当前登录者的 User-Session 剩余有效时间 (单位: 秒)
 	 * @return token剩余有效时间
 	 */
 	public static long getSessionTimeout() {
 		return lspLogic.getSessionTimeout();
 	}

 	/**
 	 * 获取当前 Token-Session 剩余有效时间 (单位: 秒) 
 	 * @return token剩余有效时间
 	 */
 	public static long getTokenSessionTimeout() {
 		return lspLogic.getTokenSessionTimeout();
 	}
 	
 	/**
 	 * 获取当前 token [临时过期] 剩余有效时间 (单位: 秒)
 	 * @return token [临时过期] 剩余有效时间
 	 */
 	public static long getTokenActivityTimeout() {
 		return lspLogic.getTokenActivityTimeout();
 	}
 	

 	
	// =================== 角色验证操作 ===================  

	/**
	 * 获取：当前账号的角色集合 
	 * @return /
	 */
	public static List<String> getRoleList() {
		return lspLogic.getRoleList();
	}

	/**
	 * 获取：指定账号的角色集合 
	 * @param loginId 指定账号id 
	 * @return /
	 */
	public static List<String> getRoleList(Object loginId) {
		return lspLogic.getRoleList(loginId);
	}

 	/** 
 	 * 判断：当前账号是否拥有指定角色, 返回true或false 
 	 * @param role 角色标识
 	 * @return 是否含有指定角色标识
 	 */
 	public static boolean hasRole(String role) {
 		return lspLogic.hasRole(role);
 	}

 	/** 
 	 * 判断：指定账号是否含有指定角色标识, 返回true或false 
 	 * @param loginId 账号id
 	 * @param role 角色标识
 	 * @return 是否含有指定角色标识
 	 */
 	public static boolean hasRole(Object loginId, String role) {
 		return lspLogic.hasRole(loginId, role);
 	}
 	
 	/** 
 	 * 判断：当前账号是否含有指定角色标识 [指定多个，必须全部验证通过] 
 	 * @param roleArray 角色标识数组
 	 * @return true或false
 	 */
 	public static boolean hasRoleAnd(String... roleArray){
 		return lspLogic.hasRoleAnd(roleArray);
 	}

 	/** 
 	 * 判断：当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可] 
 	 * @param roleArray 角色标识数组
 	 * @return true或false
 	 */
 	public static boolean hasRoleOr(String... roleArray){
 		return lspLogic.hasRoleOr(roleArray);
 	}
 	
 	/** 
 	 * 校验：当前账号是否含有指定角色标识, 如果验证未通过，则抛出异常: NotRoleException 
 	 * @param role 角色标识
 	 */
 	public static void checkRole(String role) {
 		lspLogic.checkRole(role);
 	}

 	/** 
 	 * 校验：当前账号是否含有指定角色标识 [指定多个，必须全部验证通过] 
 	 * @param roleArray 角色标识数组
 	 */
 	public static void checkRoleAnd(String... roleArray){
 		lspLogic.checkRoleAnd(roleArray);
 	}

 	/** 
 	 * 校验：当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可] 
 	 * @param roleArray 角色标识数组
 	 */
 	public static void checkRoleOr(String... roleArray){
 		lspLogic.checkRoleOr(roleArray);
 	}

	
	// =================== 权限验证操作 ===================

	/**
	 * 获取：当前账号的权限码集合 
	 * @return / 
	 */
	public static List<String> getPermissionList() {
		return lspLogic.getPermissionList();
	}

	/**
	 * 获取：指定账号的权限码集合 
	 * @param loginId 指定账号id
	 * @return / 
	 */
	public static List<String> getPermissionList(Object loginId) {
		return lspLogic.getPermissionList(loginId);
	}

 	/** 
 	 * 判断：当前账号是否含有指定权限, 返回true或false 
 	 * @param permission 权限码
 	 * @return 是否含有指定权限
 	 */
	public static boolean hasPermission(String permission) {
		return lspLogic.hasPermission(permission);
	}

 	/** 
 	 * 判断：指定账号id是否含有指定权限, 返回true或false 
 	 * @param loginId 账号id
 	 * @param permission 权限码
 	 * @return 是否含有指定权限
 	 */
	public static boolean hasPermission(Object loginId, String permission) {
		return lspLogic.hasPermission(loginId, permission);
	}

 	/** 
 	 * 判断：当前账号是否含有指定权限, [指定多个，必须全部具有] 
 	 * @param permissionArray 权限码数组
 	 * @return true 或 false 
 	 */
 	public static boolean hasPermissionAnd(String... permissionArray){
 		return lspLogic.hasPermissionAnd(permissionArray);
 	}

 	/** 
 	 * 判断：当前账号是否含有指定权限 [指定多个，只要其一验证通过即可] 
 	 * @param permissionArray 权限码数组
 	 * @return true 或 false 
 	 */
 	public static boolean hasPermissionOr(String... permissionArray){
 		return lspLogic.hasPermissionOr(permissionArray);
 	}
 	
 	/** 
 	 * 校验：当前账号是否含有指定权限, 如果验证未通过，则抛出异常: NotPermissionException 
 	 * @param permission 权限码
 	 */
	public static void checkPermission(String permission) {
		lspLogic.checkPermission(permission);
	}

 	/** 
 	 * 校验：当前账号是否含有指定权限 [指定多个，必须全部验证通过] 
 	 * @param permissionArray 权限码数组
 	 */
	public static void checkPermissionAnd(String... permissionArray) {
		lspLogic.checkPermissionAnd(permissionArray);
	}

 	/** 
 	 * 校验：当前账号是否含有指定权限 [指定多个，只要其一验证通过即可] 
 	 * @param permissionArray 权限码数组
 	 */
	public static void checkPermissionOr(String... permissionArray) {
		lspLogic.checkPermissionOr(permissionArray);
	}


	// =================== id 反查token 相关操作 ===================  
	
	/** 
	 * 获取指定账号id的tokenValue 
	 * <p> 在配置为允许并发登录时，此方法只会返回队列的最后一个token，
	 * 如果你需要返回此账号id的所有token，请调用 getTokenValueListByLoginId 
	 * @param loginId 账号id
	 * @return token值 
	 */
	public static String getTokenValueByLoginId(Object loginId) {
		return lspLogic.getTokenValueByLoginId(loginId);
	}

	/** 
	 * 获取指定账号id指定设备端的tokenValue 
	 * <p> 在配置为允许并发登录时，此方法只会返回队列的最后一个token，
	 * 如果你需要返回此账号id的所有token，请调用 getTokenValueListByLoginId 
	 * @param loginId 账号id
	 * @param device 设备标识 
	 * @return token值 
	 */
	public static String getTokenValueByLoginId(Object loginId, String device) {
		return lspLogic.getTokenValueByLoginId(loginId, device);
	}
	
	/** 
	 * 获取指定账号id的tokenValue集合 
	 * @param loginId 账号id 
	 * @return 此loginId的所有相关token 
 	 */
	public static List<String> getTokenValueListByLoginId(Object loginId) {
		return lspLogic.getTokenValueListByLoginId(loginId);
	}

	/** 
	 * 获取指定账号id指定设备端的tokenValue 集合 
	 * @param loginId 账号id 
	 * @param device 设备标识 
	 * @return 此loginId的所有相关token 
 	 */
	public static List<String> getTokenValueListByLoginId(Object loginId, String device) {
		return lspLogic.getTokenValueListByLoginId(loginId, device);
	}
	
	/**
	 * 返回当前会话的登录设备 
	 * @return 当前令牌的登录设备 
	 */
	public static String getLoginDevice() {
		return lspLogic.getLoginDevice(); 
	}

	
	// =================== 会话管理 ===================  

	/**
	 * 根据条件查询Token 
	 * @param keyword 关键字 
	 * @param start 开始处索引 (-1代表查询所有) 
	 * @param size 获取数量 
	 * @return token集合 
	 */
	public static List<String> searchTokenValue(String keyword, int start, int size) {
		return lspLogic.searchTokenValue(keyword, start, size);
	}
	
	/**
	 * 根据条件查询SessionId 
	 * @param keyword 关键字 
	 * @param start 开始处索引 (-1代表查询所有) 
	 * @param size 获取数量 
	 * @return sessionId集合 
	 */
	public static List<String> searchSessionId(String keyword, int start, int size) {
		return lspLogic.searchSessionId(keyword, start, size);
	}

	/**
	 * 根据条件查询Token专属Session的Id 
	 * @param keyword 关键字 
	 * @param start 开始处索引 (-1代表查询所有) 
	 * @param size 获取数量 
	 * @return sessionId集合 
	 */
	public static List<String> searchTokenSessionId(String keyword, int start, int size) {
		return lspLogic.searchTokenSessionId(keyword, start, size);
	}

	
	// ------------------- 账号封禁 -------------------  

	/**
	 * 封禁指定账号
	 * <p> 此方法不会直接将此账号id踢下线，而是在对方再次登录时抛出`DisableLoginException`异常 
	 * @param loginId 指定账号id 
	 * @param disableTime 封禁时间, 单位: 秒 （-1=永久封禁）
	 */
	public static void disable(Object loginId, long disableTime) {
		lspLogic.disable(loginId, disableTime);
	}
	
	/**
	 * 指定账号是否已被封禁 (true=已被封禁, false=未被封禁) 
	 * @param loginId 账号id
	 * @return see note
	 */
	public static boolean isDisable(Object loginId) {
		return lspLogic.isDisable(loginId);
	}
	
	/**
	 * 获取指定账号剩余封禁时间，单位：秒（-1=永久封禁，-2=未被封禁）
	 * @param loginId 账号id
	 * @return see note 
	 */
	public static long getDisableTime(Object loginId) {
		return lspLogic.getDisableTime(loginId);
	}

	/**
	 * 解封指定账号
	 * @param loginId 账号id 
	 */
	public static void untieDisable(Object loginId) {
		lspLogic.untieDisable(loginId);
	}
	
	
	// =================== 身份切换 ===================  

	/**
	 * 临时切换身份为指定账号id 
	 * @param loginId 指定loginId 
	 */
	public static void switchTo(Object loginId) {
		lspLogic.switchTo(loginId);
	}
	
	/**
	 * 结束临时切换身份
	 */
	public static void endSwitch() {
		lspLogic.endSwitch();
	}

	/**
	 * 当前是否正处于[身份临时切换]中 
	 * @return 是否正处于[身份临时切换]中 
	 */
	public static boolean isSwitch() {
		return lspLogic.isSwitch();
	}

	/**
	 * 在一个代码段里方法内，临时切换身份为指定账号id
	 * @param loginId 指定账号id 
	 * @param function 要执行的方法 
	 */
	public static void switchTo(Object loginId, CoreFunction function) {
		lspLogic.switchTo(loginId, function);
	}
	

	// ------------------- 二级认证 -------------------  
	
	/**
	 * 在当前会话 开启二级认证 
	 * @param safeTime 维持时间 (单位: 秒) 
	 */
	public static void openSafe(long safeTime) {
		lspLogic.openSafe(safeTime);
	}

	/**
	 * 当前会话 是否处于二级认证时间内 
	 * @return true=二级认证已通过, false=尚未进行二级认证或认证已超时 
	 */
	public static boolean isSafe() {
		return lspLogic.isSafe();
	}

	/**
	 * 检查当前会话是否已通过二级认证，如未通过则抛出异常 
	 */
	public static void checkSafe() {
		lspLogic.checkSafe();
	}
	
	/**
	 * 获取当前会话的二级认证剩余有效时间 (单位: 秒, 返回-2代表尚未通过二级认证)
	 * @return 剩余有效时间
	 */
	public static long getSafeTime() {
		return lspLogic.getSafeTime();
	}

	/**
	 * 在当前会话 结束二级认证 
	 */
	public static void closeSafe() {
		lspLogic.closeSafe();
	}


	// =================== 历史API，兼容旧版本 ===================  

	/**
	 * 获取当前StpLogin的loginKey 
	 * @return 当前StpLogin的loginKey
	 */
	@Deprecated
	public static String getLoginKey(){
		return lspLogic.getLoginType();
	}

	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 StpUtil.login() ，使用方式保持不变 </h1>
	 * 
	 * 在当前会话上登录id 
	 * @param loginId 登录id，建议的类型：（long | int | String）
	 */
	@Deprecated
	public static void setLoginId(Object loginId) {
		lspLogic.login(loginId);
	}

	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 StpUtil.login() ，使用方式保持不变 </h1>
	 * 
	 * 在当前会话上登录id, 并指定登录设备 
	 * @param loginId 登录id，建议的类型：（long | int | String）
	 * @param device 设备标识 
	 */
	@Deprecated
	public static void setLoginId(Object loginId, String device) {
		lspLogic.login(loginId, device);
	}

	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 StpUtil.login() ，使用方式保持不变 </h1>
	 * 
	 * 在当前会话上登录id, 并指定登录设备 
	 * @param loginId 登录id，建议的类型：（long | int | String）
	 * @param isLastingCookie 是否为持久Cookie 
	 */
	@Deprecated
	public static void setLoginId(Object loginId, boolean isLastingCookie) {
		lspLogic.login(loginId, isLastingCookie);
	}
	
	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 StpUtil.login() ，使用方式保持不变 </h1>
	 * 
	 * 在当前会话上登录id, 并指定所有登录参数Model 
	 * @param loginId 登录id，建议的类型：（long | int | String）
	 * @param loginModel 此次登录的参数Model 
	 */
	@Deprecated
	public static void setLoginId(Object loginId, SecurityLoginModel loginModel) {
		lspLogic.login(loginId, loginModel);
	}
	
	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 StpUtil.kickout() ，使用方式保持不变 </h1>
	 * 
	 * 会话注销，根据账号id （踢人下线）
	 * <p> 当对方再次访问系统时，会抛出NotLoginException异常，场景值=-2
	 * @param loginId 账号id 
	 */
	@Deprecated
	public static void logoutByLoginId(Object loginId) {
		lspLogic.kickout(loginId);
	}
	
	/**
	 * <h1> 本函数设计已过时，未来版本可能移除此函数，请及时更换为 StpUtil.kickout() ，使用方式保持不变 </h1>
	 * 
	 * 会话注销，根据账号id and 设备标识 （踢人下线）
	 * <p> 当对方再次访问系统时，会抛出NotLoginException异常，场景值=-2 </p>
	 * @param loginId 账号id 
	 * @param device 设备标识 (填null代表所有注销设备) 
	 */
	@Deprecated
	public static void logoutByLoginId(Object loginId, String device) {
		lspLogic.kickout(loginId, device);
	}
	
}

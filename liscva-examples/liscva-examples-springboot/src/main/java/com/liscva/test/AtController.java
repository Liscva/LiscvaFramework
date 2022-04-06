package com.liscva.test;

import com.liscva.framework.core.connect.DefaultPublicConnect;
import com.liscva.framework.core.connect.FinalConnect;
import com.liscva.framework.security.annotation.*;
import com.liscva.framework.security.lsp.LspUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注解鉴权测试
 *
 */
@RestController
@RequestMapping("/at/")
public class AtController {

	// 登录认证，登录之后才可以进入方法  ---- http://localhost:8866/at/checkLogin 
	@CheckLogin
	@RequestMapping("checkLogin")
	public FinalConnect checkLogin() {
		return DefaultPublicConnect.ok();
	}
	
	// 权限认证，具备user-add权限才可以进入方法  ---- http://localhost:8866/at/checkPermission 
	@CheckPermission("user-add")
	@RequestMapping("checkPermission")
	public FinalConnect checkPermission() {
		return DefaultPublicConnect.ok();
	}

	// 权限认证，同时具备所有权限才可以进入  ---- http://localhost:8866/at/checkPermissionAnd 
	@CheckPermission({"user-add", "user-delete", "user-update"})
	@RequestMapping("checkPermissionAnd")
	public FinalConnect checkPermissionAnd() {
		return DefaultPublicConnect.ok();
	}

	// 权限认证，只要具备其中一个就可以进入  ---- http://localhost:8866/at/checkPermissionOr 
	@CheckPermission(value = {"user-add", "user-delete", "user-update"}, mode = Mode.OR)
	@RequestMapping("checkPermissionOr")
	public FinalConnect checkPermissionOr() {
		return DefaultPublicConnect.ok();
	}

	// 角色认证，只有具备admin角色才可以进入  ---- http://localhost:8866/at/checkRole 
	@CheckRole("admin")
	@RequestMapping("checkRole")
	public FinalConnect checkRole() {
		return DefaultPublicConnect.ok();
	}

	// 完成二级认证  ---- http://localhost:8866/at/openSafe 
	@RequestMapping("openSafe")
	public FinalConnect openSafe() {
		LspUtil.openSafe(200); // 打开二级认证，有效期为200秒
		return DefaultPublicConnect.ok();
	}
	
	// 通过二级认证后才可以进入  ---- http://localhost:8866/at/checkSafe 
	@CheckSafe
	@RequestMapping("checkSafe")
	public FinalConnect checkSafe() {
		return DefaultPublicConnect.ok();
	}
	
	// 通过Basic认证后才可以进入  ---- http://localhost:8866/at/checkBasic 
	@CheckBasic(account = "sa:123456")
	@RequestMapping("checkBasic")
	public FinalConnect checkBasic() {
		return DefaultPublicConnect.ok();
	}
	
}

package com.liscva.test;

import com.liscva.framework.core.connect.DefaultPublicConnect;
import com.liscva.framework.core.connect.FailPublicConnect;
import com.liscva.framework.core.connect.FinalConnect;
import com.liscva.framework.security.lsp.LspUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录测试
 *
 */
@RestController
@RequestMapping("/acc/")
public class LoginController {

	// 测试登录  ---- http://localhost:8866/acc/doLogin?name=zhang&pwd=123456
	@RequestMapping("doLogin")
	public FinalConnect doLogin(String name, String pwd) {
		// 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对 
		if("zhang".equals(name) && "123456".equals(pwd)) {
			LspUtil.login(10001);
			return DefaultPublicConnect.ok("登录成功");
		}
		return FailPublicConnect.defaultError("登录失败");
	}

	// 查询登录状态  ---- http://localhost:8866/acc/isLogin
	@RequestMapping("isLogin")
	public FinalConnect isLogin() {
		return DefaultPublicConnect.ok("是否登录：" + LspUtil.isLogin());
	}

	// 查询 Token 信息  ---- http://localhost:8866/acc/tokenInfo
	@RequestMapping("tokenInfo")
	public FinalConnect tokenInfo() {
		return DefaultPublicConnect.of(LspUtil.getTokenInfo());
	}
	
	// 测试注销  ---- http://localhost:8866/acc/logout
	@RequestMapping("logout")
	public FinalConnect logout() {
		LspUtil.logout();
		return DefaultPublicConnect.ok();
	}
	
}

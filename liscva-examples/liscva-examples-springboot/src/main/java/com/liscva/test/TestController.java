package com.liscva.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liscva.framework.core.connect.DefaultPublicConnect;
import com.liscva.framework.core.connect.FinalConnect;
import com.liscva.framework.security.annotation.CheckLogin;
import com.liscva.framework.security.annotation.CheckPermission;
import com.liscva.framework.security.annotation.CheckRole;
import com.liscva.framework.security.annotation.Mode;
import com.liscva.framework.security.session.SecurityInfoSessionCustomUtil;
import com.liscva.framework.security.lsp.SecurityInfo;
import com.liscva.framework.security.lsp.LspUtil;
import com.liscva.util.Ttime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 测试专用Controller
 *
 */
@RestController
@RequestMapping("/test/")
public class TestController {

	// 测试登录接口， 浏览器访问： http://localhost:8866/test/login
	@RequestMapping("login")
	public FinalConnect login(@RequestParam(defaultValue="10001") String id) {
		System.out.println("======================= 进入方法，测试登录接口 ========================= ");
		System.out.println("当前会话的token：" + LspUtil.getTokenValue());
		System.out.println("当前是否登录：" + LspUtil.isLogin());
		System.out.println("当前登录账号：" + LspUtil.getLoginIdDefaultNull());
		
		LspUtil.login(id);			// 在当前会话登录此账号
		System.out.println("登录成功");
		System.out.println("当前是否登录：" + LspUtil.isLogin());
		System.out.println("当前登录账号：" + LspUtil.getLoginId());
//		System.out.println("当前登录账号并转为int：" + StpUtil.getLoginIdAsInt());
		System.out.println("当前登录设备：" + LspUtil.getLoginDevice());
//		System.out.println("当前token信息：" + StpUtil.getTokenInfo());	
		
		return DefaultPublicConnect.ok();
	}
	
	// 测试退出登录 ， 浏览器访问： http://localhost:8866/test/logout
	@RequestMapping("logout")
	public FinalConnect logout() {
		LspUtil.logout();
//		StpUtil.logoutByLoginId(10001);
		return DefaultPublicConnect.ok();
	}
	
	// 测试角色接口， 浏览器访问： http://localhost:8866/test/testRole
	@RequestMapping("testRole")
	public FinalConnect testRole() {
		System.out.println("======================= 进入方法，测试角色接口 ========================= ");
		
		System.out.println("是否具有角色标识 user " + LspUtil.hasRole("user"));
		System.out.println("是否具有角色标识 admin " + LspUtil.hasRole("admin"));
		
		System.out.println("没有admin权限就抛出异常");
		LspUtil.checkRole("admin");
		
		System.out.println("在【admin、user】中只要拥有一个就不会抛出异常");
		LspUtil.checkRoleOr("admin", "user");

		System.out.println("在【admin、user】中必须全部拥有才不会抛出异常");
		LspUtil.checkRoleAnd("admin", "user");

		System.out.println("角色测试通过");

		return DefaultPublicConnect.ok();
	}

	// 测试权限接口， 浏览器访问： http://localhost:8866/test/testJur
	@RequestMapping("testJur")
	public FinalConnect testJur() {
		System.out.println("======================= 进入方法，测试权限接口 ========================= ");
		
		System.out.println("是否具有权限101" + LspUtil.hasPermission("101"));
		System.out.println("是否具有权限user-add" + LspUtil.hasPermission("user-add"));
		System.out.println("是否具有权限article-get" + LspUtil.hasPermission("article-get"));
		
		System.out.println("没有user-add权限就抛出异常");
		LspUtil.checkPermission("user-add");
		
		System.out.println("在【101、102】中只要拥有一个就不会抛出异常");
		LspUtil.checkPermissionOr("101", "102");

		System.out.println("在【101、102】中必须全部拥有才不会抛出异常");
		LspUtil.checkPermissionAnd("101", "102");

		System.out.println("权限测试通过");

		return DefaultPublicConnect.ok();
	}

	// 测试会话session接口， 浏览器访问： http://localhost:8866/test/session 
	@RequestMapping("session")
	public FinalConnect session() throws JsonProcessingException {
		System.out.println("======================= 进入方法，测试会话session接口 ========================= ");
		System.out.println("当前是否登录：" + LspUtil.isLogin());
		System.out.println("当前登录账号session的id" + LspUtil.getSession().getId());
		System.out.println("当前登录账号session的id" + LspUtil.getSession().getId());
		System.out.println("测试取值name：" + LspUtil.getSession().get("name"));
		LspUtil.getSession().set("name", new Date());	// 写入一个值
		System.out.println("测试取值name：" + LspUtil.getSession().get("name"));
		System.out.println( new ObjectMapper().writeValueAsString(LspUtil.getSession()));
		return DefaultPublicConnect.ok();
	}
	
	// 测试自定义session接口， 浏览器访问： http://localhost:8866/test/session2 
	@RequestMapping("session2")
	public FinalConnect session2() {
		System.out.println("======================= 进入方法，测试自定义session接口 ========================= ");
		// 自定义session就是无需登录也可以使用 的session ：比如拿用户的手机号当做 key， 来获取 session 
		System.out.println("自定义 session的id为：" + SecurityInfoSessionCustomUtil.getSessionById("1895544896").getId());
		System.out.println("测试取值name：" + SecurityInfoSessionCustomUtil.getSessionById("1895544896").get("name"));
		SecurityInfoSessionCustomUtil.getSessionById("1895544896").set("name", "张三");	// 写入值
		System.out.println("测试取值name：" + SecurityInfoSessionCustomUtil.getSessionById("1895544896").get("name"));
		System.out.println("测试取值name：" + SecurityInfoSessionCustomUtil.getSessionById("1895544896").get("name"));
		return DefaultPublicConnect.ok();
	}

	// ---------- 
	// 测试token专属session， 浏览器访问： http://localhost:8866/test/getTokenSession 
	@RequestMapping("getTokenSession")
	public FinalConnect getTokenSession() {
		System.out.println("======================= 进入方法，测试会话session接口 ========================= ");
		System.out.println("当前是否登录：" + LspUtil.isLogin());
		System.out.println("当前token专属session: " + LspUtil.getTokenSession().getId());

		System.out.println("测试取值name：" + LspUtil.getTokenSession().get("name"));
		LspUtil.getTokenSession().set("name", "张三");	// 写入一个值
		System.out.println("测试取值name：" + LspUtil.getTokenSession().get("name"));

		return DefaultPublicConnect.ok();
	}
	
	// 打印当前token信息， 浏览器访问： http://localhost:8866/test/tokenInfo
	@RequestMapping("tokenInfo")
	public FinalConnect tokenInfo() {
		System.out.println("======================= 进入方法，打印当前token信息 ========================= ");
		SecurityInfo tokenInfo = LspUtil.getTokenInfo();
		System.out.println(tokenInfo);
		return DefaultPublicConnect.of(tokenInfo);
	}
	
	// 测试注解式鉴权， 浏览器访问： http://localhost:8866/test/atCheck
	@CheckLogin                        // 注解式鉴权：当前会话必须登录才能通过
	@CheckRole("super-admin")			// 注解式鉴权：当前会话必须具有指定角色标识才能通过
	@CheckPermission("user-add")		// 注解式鉴权：当前会话必须具有指定权限才能通过
	@RequestMapping("atCheck")
	public FinalConnect atCheck() {
		System.out.println("======================= 进入方法，测试注解鉴权接口 ========================= ");
		System.out.println("只有通过注解鉴权，才能进入此方法");
//		StpUtil.checkActivityTimeout();
//		StpUtil.updateLastActivityToNow();
		return DefaultPublicConnect.ok();
	}
	
	// 测试注解式鉴权， 浏览器访问： http://localhost:8866/test/atJurOr
	@RequestMapping("atJurOr")
	@CheckPermission(value = {"user-add", "user-all", "user-delete"}, mode = Mode.OR)		// 注解式鉴权：只要具有其中一个权限即可通过校验
	public FinalConnect atJurOr() {
		return DefaultPublicConnect.ok("用户信息");
	}
	
	// [活动时间] 续签： http://localhost:8866/test/rene
	@RequestMapping("rene")
	public FinalConnect rene() {
		LspUtil.checkActivityTimeout();
		LspUtil.updateLastActivityToNow();
		return DefaultPublicConnect.ok("续签成功");
	}
	
	// 测试踢人下线   浏览器访问： http://localhost:8866/test/kickOut 
	@RequestMapping("kickOut")
	public FinalConnect kickOut() {
		// 先登录上 
		LspUtil.login(10001);
		// 踢下线 
		LspUtil.kickout(10001);
		// 再尝试获取
		LspUtil.getLoginId();
		// 返回 
		return DefaultPublicConnect.ok();
	}

	// 测试登录接口, 按照设备登录， 浏览器访问： http://localhost:8866/test/login2
	@RequestMapping("login2")
	public FinalConnect login2(@RequestParam(defaultValue="10001") String id, @RequestParam(defaultValue="PC") String device) {
		LspUtil.login(id, device);
		return DefaultPublicConnect.ok();
	}
	
	// 测试身份临时切换： http://localhost:8866/test/switchTo
	@RequestMapping("switchTo")
	public FinalConnect switchTo() {
		System.out.println("当前会话身份：" + LspUtil.getLoginIdDefaultNull());
		System.out.println("是否正在身份临时切换中: " + LspUtil.isSwitch());
		LspUtil.switchTo(10044, () -> {
			System.out.println("是否正在身份临时切换中: " + LspUtil.isSwitch());
			System.out.println("当前会话身份已被切换为：" + LspUtil.getLoginId());
		});		
		System.out.println("是否正在身份临时切换中: " + LspUtil.isSwitch());
		return DefaultPublicConnect.ok();
	}
	
	// 测试会话治理   浏览器访问： http://localhost:8866/test/search
	@RequestMapping("search")
	public FinalConnect search() {
		System.out.println("--------------");
		Ttime t = new Ttime().start();
		List<String> tokenValue = LspUtil.searchTokenValue("8feb8265f773", 0, 10);
		for (String v : tokenValue) {
			System.out.println(v);
		}
		System.out.println("用时：" + t.end().toString());
		return DefaultPublicConnect.ok();
	}

	// 测试指定设备登录   浏览器访问： http://localhost:8866/test/loginByDevice
	@RequestMapping("loginByDevice")
	public FinalConnect loginByDevice() {
		System.out.println("--------------");
		LspUtil.login(10001, "PC");
		return DefaultPublicConnect.of("登录成功");
	}

	// 测试   浏览器访问： http://localhost:8866/test/test
	@RequestMapping("test")
	public FinalConnect test() {
		System.out.println("------------进来了"); 
		return DefaultPublicConnect.ok();
	}
	
	// 测试   浏览器访问： http://localhost:8866/test/test2
	@RequestMapping("test2")
	public FinalConnect test2() {
		return DefaultPublicConnect.ok();
	}

}

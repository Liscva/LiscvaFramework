package com.liscva.framework.security.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * 对 LspInterface 接口默认的实现类
 * <p>
 * 如果开发者没有实现LspInterface接口，则使用此默认实现
 */
public class LspInterfaceDefaultImpl implements LspInterface {

	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		return new ArrayList<String>();
	}

}

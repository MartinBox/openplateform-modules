package org.openplateform.shiro.dynamic.permissions;

import java.util.List;

import org.junit.Test;

import com.open.shiro.dynamic.permissions.handler.CustomerInvocationHandler;
import com.open.shiro.dynamic.permissions.service.UserService;
import com.open.shiro.dynamic.permissions.service.UserServiceImpl;

public class TestHandler extends JunitBase {

	@Test
	public void test() {
		CustomerInvocationHandler hander = new CustomerInvocationHandler();
		UserService userService = (UserService) hander.newProxy(new UserServiceImpl());
		List list = userService.findAll();
		System.out.println(list);
	}
}

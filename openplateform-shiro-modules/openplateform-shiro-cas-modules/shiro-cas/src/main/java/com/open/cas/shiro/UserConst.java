package com.open.cas.shiro;

public class UserConst {

	/**
	 * 用户状态枚举值
	 * @author liuheng
	 * @date 2015年7月10日 下午3:14:27
	 */
	public enum UserStaEnum {
		ENABLE(1), // 启用 
		DISABLE(2);// 禁用

		private int value;

		private UserStaEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}
}

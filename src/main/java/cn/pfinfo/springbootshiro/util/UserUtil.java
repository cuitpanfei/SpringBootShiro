package cn.pfinfo.springbootshiro.util;

import cn.pfinfo.springbootshiro.entity.User;

public class UserUtil {
	
	/**
	 * 佚名用户
	 */
	public static User yiming =  new User();
	
	public static Long yimingId =  (long) Math.random();

	/**
	 * @return 佚名用户
	 */
	public static User getDefultUser() {
		if(yiming.getId()==null){
			yiming = init(yiming);
		}
		return yiming;
	}

	private static User init(User yiming) {
		yiming.setId(yimingId);
		yiming.setName("佚名");
		yiming.setUserName("佚名");
		return yiming;
	}

}

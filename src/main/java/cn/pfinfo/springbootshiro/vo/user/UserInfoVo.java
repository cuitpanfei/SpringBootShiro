package cn.pfinfo.springbootshiro.vo.user;

import java.io.Serializable;

import cn.pfinfo.springbootshiro.entity.User;
import lombok.Getter;
@Getter
public class UserInfoVo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7796780259609078089L;
	
	private User user;
	
	public UserInfoVo(User currentUser) {
		this.user = currentUser;
	}
}


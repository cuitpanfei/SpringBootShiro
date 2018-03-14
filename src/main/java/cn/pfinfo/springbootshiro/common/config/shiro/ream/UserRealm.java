package cn.pfinfo.springbootshiro.common.config.shiro.ream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.pfinfo.springbootshiro.common.config.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.common.config.shiro.authc.UnavalilableAccountException;
import cn.pfinfo.springbootshiro.entity.Role;
import cn.pfinfo.springbootshiro.entity.RolePermission;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IUserService;
import lombok.extern.log4j.Log4j;

/**
 * 
 * @author panfei
 *
 */
@Log4j
public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	IUserService userService;

	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("开始授权");
		User u = (User) principals.getPrimaryPrincipal();
		u.setPassword("");
		List<Role> role = u.getRoles();
		List<RolePermission> rolePermissions = new ArrayList<>();
		Set<String> roles = new HashSet<>();
		Set<String> permissions = new HashSet<>();
		role.forEach(r->{
			roles.add(r.getRole());
			rolePermissions.addAll(r.getRolePermissions());
		});
		rolePermissions.forEach(rp->{
			if(rp.getPermission().getAvailable()!=0){
				permissions.add(rp.getPermission().getPermission());
			}
		});
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roles);
		info.addStringPermissions(permissions);
		ShiroUtil.getSubject().runAs(principals);
		log.info("授权完毕");
		return info;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken u = (UsernamePasswordToken) token;
		User user;
		user = userService.findByUserName(u.getUsername());
		if(user == null){
			throw new UnknownAccountException("你还未注册，请注册");
		}else if(user.getState() == User.STATE_UNAVAILABLE){
			throw new UnavalilableAccountException("无效的账号");
		}else if(user.getState() == User.STATE_LOCKE){
			throw new LockedAccountException("此账号已被锁定，请联系管理人员");
		}else{
			
		}
		//盐值加密
		ByteSource salt = ByteSource.Util.bytes(user.getSaltTo());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
		info.setCredentialsSalt(salt);
		Session session = ShiroUtil.getSession();
        session.setAttribute("userinfo", user);
        session.setAttribute("userSessionId", user.getId());
        log.info("认证成功");
		return info;
	}

}

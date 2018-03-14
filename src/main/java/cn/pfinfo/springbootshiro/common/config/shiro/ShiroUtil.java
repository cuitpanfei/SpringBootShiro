package cn.pfinfo.springbootshiro.common.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.pfinfo.springbootshiro.entity.User;

public class ShiroUtil {

    /**
     * 判断当前是否登录状态
     * @return
     */
    public static boolean isCurrentUser(){
        return getCurrentUser() != null;
    }
    /**
     * 
     * @param name    加密方式
     * @param crdentials   明文
     * @param salt 盐值
     * @return 密文
     */
    public static Object passwordSimpleHash(String name,String crdentials,String salt){
    	HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(3);// 散列的次数，比如散列两次，相当于 md5(md5(""));
    	return new SimpleHash(name,crdentials,salt,3);
    }
    /**
     * 
     * @param name    加密方式
     * @param times   加密次数
     * @return        凭证匹配器
     */
    public static HashedCredentialsMatcher getCredentialsMatcher(String name,int times){
    	HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(times);// 散列的次数，比如散列两次，相当于 md5(md5(""));
    	return hashedCredentialsMatcher;
    }


    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 查询当前登录者信息
     * @return ShiroUser
     */
    public static User getCurrentUser() {
        return  (User) getSubject().getPrincipal();
    }


    /**
     * 查询当前登录者 用户名
     * @return
     */
    public static String getCurrentUserName() {
        return getCurrentUser().getUserName();
    }
    /**
     * 查询当前登录者 用户Id
     * @return
     */
    public static long getCurrentUserId() {
        return getCurrentUser().getId();
    }
    /**
     * 获取session
     * @return
     */
	public static Session getSession() {
		return getSubject().getSession();
	}

}
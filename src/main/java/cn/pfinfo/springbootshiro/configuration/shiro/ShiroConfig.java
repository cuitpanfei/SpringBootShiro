package cn.pfinfo.springbootshiro.configuration.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.pfinfo.springbootshiro.configuration.shiro.ream.UserRealm;
import cn.pfinfo.springbootshiro.entity.Permission;
import cn.pfinfo.springbootshiro.service.common.ICommonService;
import lombok.extern.log4j.Log4j;

@Configuration
@Log4j
public class ShiroConfig {
	
	@Bean("authenticator")
	public Authenticator authenticator(Realm ...realms) {
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		Collection<Realm> crealms = new ArrayList<>(realms.length);
		for(Realm oneRealm:realms){
			crealms.add(oneRealm);
		}
		authenticator.setRealms(crealms );
		return authenticator;
	}


	@Bean(name = "simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");// 数据库异常处理
		mappings.setProperty("UnauthorizedException", "403");
		r.setExceptionMappings(mappings); // None by default
		r.setDefaultErrorView("error"); // No default
		r.setExceptionAttribute("ex"); // Default is "exception"
		return r;
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher = ShiroUtil.getCredentialsMatcher("md5",3);
		return hashedCredentialsMatcher;
	}

	/**
	 * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类， 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultWebSecurityManager securityManager(Authenticator authenticator) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setAuthenticator(authenticator);// set realms
		securityManager.setCacheManager(cacheManager());
		return securityManager;
	}

	/**
	 * 过滤器 
	 * anon 
	 * org.apache.shiro.web.filter.authc.AnonymousFilter 
	 * 
	 * authc 
	 * org.apache.shiro.web.filter.authc.FormAuthenticationFilter 
	 * 
	 * authcBasic
	 * org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter 
	 * 
	 * perms 
	 * org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter 
	 * 
	 * port 
	 * org.apache.shiro.web.filter.authz.PortFilter 
	 * 
	 * rest 
	 * org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter 
	 * 
	 * roles 
	 * org.apache.shiro.web.filter.authz.RolesAuthorizationFilter 
	 * 
	 * ssl 
	 * org.apache.shiro.web.filter.authz.SslFilter 
	 * 
	 * user 
	 * org.apache.shiro.web.filter.authc.UserFilter
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager,ICommonService commonService) {
		log.info("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		loadShiroFilterChain(shiroFilterFactoryBean,commonService);
		
		return shiroFilterFactoryBean;
	}

	/**
	 * 从数据库加载过滤链
	 * @param shiroFilterFactoryBean
	 * @param commonService
	 */
	private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean,ICommonService commonService){
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// authc:所有url都必须认证通过才可以访问
		// anon:所有url都都可以匿名访问
		log.info("=====================数据库读取权限规则，加载到ShiroFilter中====================");
		List<Permission> urls = commonService.findFilterChain();
		urls.forEach(permission->{
			filterChainDefinitionMap.put(permission.getUrl(),permission.getPermission());
		});
			
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "anon");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面;设置后跳转到下面设置的URL
		shiroFilterFactoryBean.setLoginUrl("/loginOrRegister");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

	/**
	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
	 * 
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public UserRealm userRealm(EhCacheManager cacheManager) {
		UserRealm userRealm = new UserRealm();
		userRealm.setCachingEnabled(true);//开启缓存
		userRealm.setCacheManager(cacheManager);
		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return userRealm;
	}

	@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return null;
	}


	/**
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}
	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}

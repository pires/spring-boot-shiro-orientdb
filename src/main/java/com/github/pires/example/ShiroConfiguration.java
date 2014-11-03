package com.github.pires.example;

import com.github.pires.example.shiro.HazelcastSessionDao;
import com.github.pires.example.shiro.OrientDbRealm;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * TODO add description
 */
@Configuration
public class ShiroConfiguration {

  @Bean
  public ShiroFilterFactoryBean shiroFilter() {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    factoryBean.setSecurityManager(securityManager());
    return factoryBean;
  }

  @Bean(name = "securityManager")
  public DefaultWebSecurityManager securityManager() {
    final DefaultWebSecurityManager securityManager
        = new DefaultWebSecurityManager();
    securityManager.setRealm(realm());
    securityManager.setSessionManager(sessionManager());
    return securityManager;
  }

  @Bean
  public DefaultWebSessionManager sessionManager() {
    final DefaultWebSessionManager sessionManager
        = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(sessionDao());
    sessionManager.setGlobalSessionTimeout(43200000); // 12 hours
    return sessionManager;
  }

  @Bean
  public SessionDAO sessionDao() {
    return new HazelcastSessionDao();
  }

  @Bean(name = "realm")
  @DependsOn("lifecycleBeanPostProcessor")
  public OrientDbRealm realm() {
    final OrientDbRealm realm = new OrientDbRealm();
    realm.setCredentialsMatcher(credentialsMatcher());
    return realm;
  }

  @Bean(name = "credentialsMatcher")
  public PasswordMatcher credentialsMatcher() {
    final PasswordMatcher credentialsMatcher = new PasswordMatcher();
    credentialsMatcher.setPasswordService(passwordService());
    return credentialsMatcher;
  }

  @Bean(name = "passwordService")
  public DefaultPasswordService passwordService() {
    return new DefaultPasswordService();
  }

  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

}

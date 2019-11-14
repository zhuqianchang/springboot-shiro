package indi.zqc.shiro.configuration;

import indi.zqc.shiro.constant.Constants;
import indi.zqc.shiro.filter.AdminFilter;
import indi.zqc.shiro.filter.ClientFilter;
import indi.zqc.shiro.filter.KickoutSessionFilter;
import indi.zqc.shiro.realm.AdminRealm;
import indi.zqc.shiro.realm.ClientRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro 配置
 *
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public Realm clientRealm() {
        return new ClientRealm();
    }

    @Bean
    public Realm adminRealm() {
        return new AdminRealm();
    }

    /**
     * Redis 单机模式
     */
    @Bean
    @ConfigurationProperties(prefix = Constants.SHITO_REDIS_PREFIX)
    @ConditionalOnProperty(name = Constants.SHITO_REDIS_PREFIX + ".type", havingValue = "single", matchIfMissing = true)
    public RedisManager redisManager() {
        return new RedisManager();
    }

    /**
     * Redis 集群模式
     */
    @Bean
    @ConfigurationProperties(prefix = Constants.SHITO_REDIS_PREFIX)
    @ConditionalOnProperty(name = Constants.SHITO_REDIS_PREFIX + ".type", havingValue = "cluster")
    public RedisClusterManager redisClusterManager() {
        return new RedisClusterManager();
    }

    /**
     * Redis 哨兵模式
     */
    @Bean
    @ConfigurationProperties(prefix = Constants.SHITO_REDIS_PREFIX)
    @ConditionalOnProperty(name = Constants.SHITO_REDIS_PREFIX + ".type", havingValue = "sentinel")
    public RedisSentinelManager redisSentinelManager() {
        return new RedisSentinelManager();
    }

    @Bean
    public CacheManager cacheManager(IRedisManager redisManager) {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setPrincipalIdFieldName(Constants.PRINCIPAL_ID_FIELD_NAME);
        cacheManager.setRedisManager(redisManager);
        return cacheManager;
    }

    @Bean
    public SessionDAO sessionDAO(IRedisManager redisManager) {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager);
        return sessionDAO;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(List<Realm> realms, CacheManager cacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realms);
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("clientFilter", new ClientFilter());
        filters.put("adminFilter", new AdminFilter());
        filters.put("kickoutFilter", new KickoutSessionFilter((DefaultWebSecurityManager) securityManager));
        shiroFilterFactoryBean.setFilters(filters);

        // 此处必须LinkedHashMap
        Map<String, String> definitions = new LinkedHashMap<>();
        // 登录接口
        definitions.put("/client/login", "anon");
        definitions.put("/admin/login", "anon");
        // Swagger2页面
        definitions.put("/swagger-ui.html", "anon");
        definitions.put("/swagger/**", "anon");
        definitions.put("/webjars/**", "anon");
        definitions.put("/swagger-resources/**", "anon");
        definitions.put("/v2/**", "anon");
        // 权限拦截
        definitions.put("/client/**", "kickoutFilter,clientFilter");
        definitions.put("/admin/**", "adminFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(definitions);

        return shiroFilterFactoryBean;
    }
}

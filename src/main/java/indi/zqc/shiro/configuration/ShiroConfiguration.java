package indi.zqc.shiro.configuration;

import indi.zqc.shiro.filter.AdminFilter;
import indi.zqc.shiro.filter.ClientFilter;
import indi.zqc.shiro.realm.AdminRealm;
import indi.zqc.shiro.realm.ClientRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("clientFilter", new ClientFilter());
        filters.put("adminFilter", new AdminFilter());
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
        definitions.put("/client/**", "clientFilter");
        definitions.put("/admin/**", "adminFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(definitions);

        return shiroFilterFactoryBean;
    }
}
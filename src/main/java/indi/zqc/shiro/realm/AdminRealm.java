package indi.zqc.shiro.realm;

import indi.zqc.shiro.core.SessionUser;
import indi.zqc.shiro.token.AdminToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 管理端认证、授权
 *
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
public class AdminRealm extends AuthorizingRealm {

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AdminToken adminToken = (AdminToken) authenticationToken;
        // 校验管理端用户，此处省略......
        SessionUser sessionUser = new SessionUser(adminToken.getUsername());
        return new SimpleAuthenticationInfo(sessionUser, adminToken.getCredentials(), getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 指定对应的TOKEN
     */
    @Override
    public Class getAuthenticationTokenClass() {
        return AdminToken.class;
    }
}

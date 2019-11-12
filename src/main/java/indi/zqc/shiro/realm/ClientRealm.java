package indi.zqc.shiro.realm;

import indi.zqc.shiro.constant.Constants;
import indi.zqc.shiro.core.SessionUser;
import indi.zqc.shiro.token.ClientToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 客户端认证、授权
 *
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
public class ClientRealm extends AuthorizingRealm {

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ClientToken clientToken = (ClientToken) authenticationToken;
        // 校验客户端用户，此处省略......
        SessionUser sessionUser = new SessionUser(clientToken.getUsername());
        return new SimpleAuthenticationInfo(sessionUser, clientToken.getCredentials(), getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SessionUser sessionUser = (SessionUser) principal.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (StringUtils.equalsIgnoreCase(sessionUser.getUsername(), "USER")) {
            authorizationInfo.addRole(Constants.ROLE_USER);
        }
        if (StringUtils.equalsIgnoreCase(sessionUser.getUsername(), "ADMIN")) {
            authorizationInfo.addRole(Constants.ROLE_ADMIN);
        }
        return authorizationInfo;
    }

    /**
     * 指定对应的TOKEN
     */
    @Override
    public Class getAuthenticationTokenClass() {
        return ClientToken.class;
    }
}

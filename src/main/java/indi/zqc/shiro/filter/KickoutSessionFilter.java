package indi.zqc.shiro.filter;

import indi.zqc.shiro.core.APIResponse;
import indi.zqc.shiro.core.SessionUser;
import indi.zqc.shiro.token.ClientToken;
import indi.zqc.shiro.util.WebUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Zhu.Qianchang
 * @date 2019/11/14.
 */
@Slf4j
@Getter
@Setter
public class KickoutSessionFilter extends AccessControlFilter {

    public static final String KICKOUT = "kickout";

    /**
     * 同一个用户最大同时登录数
     */
    private int maxSession = 1;

    /**
     * 是否踢出后登录的，默认false，踢出前登录的
     */
    private boolean kickoutAfter = false;

    private SessionManager sessionManager;

    private Cache<String, Deque<Serializable>> cache;

    public KickoutSessionFilter(DefaultWebSecurityManager securityManager) {
        setSessionManager(securityManager.getSessionManager());
        setCacheManager(securityManager.getCacheManager());
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        // 没有登录授权，且没有记住我
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }
        Session session = subject.getSession();
        Serializable sessionId = session.getId();
        SessionUser sessionUser = (SessionUser) subject.getPrincipal();
        String username = sessionUser.getUsername();
        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            // 初始化队列
            deque = new ArrayDeque<>();
        }
        // 若队列中没有此sessionId，且用户没有被踢出，则放入队列
        if (!deque.contains(sessionId) && session.getAttribute(KICKOUT) == null) {
            deque.push(sessionId);
            cache.put(username, deque);
        }
        while (deque.size() > maxSession) {
            // 踢出对象的sessionId
            Serializable kickoutSessionId = kickoutAfter ? deque.removeFirst() : deque.removeLast();
            cache.put(username, deque);
            // 获取被踢出的sessionId的session对象
            Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
            if (kickoutSession != null) {
                // 设置会话的kickout属性表示踢出了
                kickoutSession.setAttribute(KICKOUT, true);
            }
        }

        // 如果被踢出了，直接退出
        if (session.getAttribute(KICKOUT) != null
                && (Boolean) session.getAttribute(KICKOUT)) {
            // 会话被踢出了
            try {
                // 退出登录
                subject.logout();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            saveRequest(request);
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        APIResponse apiResponse = APIResponse.fail("已被踢出");
        if (response instanceof HttpServletResponse) {
            WebUtils.writeJson((HttpServletResponse) response, apiResponse);
        }
        return false;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(ClientToken.class.getSimpleName());
    }
}

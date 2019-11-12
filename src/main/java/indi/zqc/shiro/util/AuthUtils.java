package indi.zqc.shiro.util;

import indi.zqc.shiro.core.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

@Slf4j
public class AuthUtils {

    private AuthUtils() {
    }

    public static Object login(AuthenticationToken token) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return subject.getPrincipal();
    }

    public static void setSessionUser(String key, SessionUser userPrinciple) {
        SecurityUtils.getSubject().getSession(true).setAttribute(key, userPrinciple);
    }

    public static Object getSessionAttribute(String name) {
        Session session = SecurityUtils.getSubject().getSession(false);
        return session != null ? session.getAttribute(name) : null;
    }

    public static SessionUser getSessionUser(String key) {
        return (SessionUser) getSessionAttribute(key);
    }

    public static void logout(String key) {
        Session session = SecurityUtils.getSubject().getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }
}

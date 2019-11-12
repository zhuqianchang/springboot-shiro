package indi.zqc.shiro.filter;

import indi.zqc.shiro.constant.Constants;
import indi.zqc.shiro.util.AuthUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 管理端过滤器
 *
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
public class AdminFilter extends AbstractFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        // 校验用户是否登录
        return AuthUtils.getSessionUser(Constants.ADMIN_USER) != null;
    }
}

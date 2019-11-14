package indi.zqc.shiro.filter;

import indi.zqc.shiro.constant.Constants;
import indi.zqc.shiro.core.APIResponse;
import indi.zqc.shiro.util.AuthUtils;
import indi.zqc.shiro.util.WebUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理端过滤器
 *
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
public class AdminFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        // 校验用户是否登录
        return AuthUtils.getSessionUser(Constants.ADMIN_USER) != null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        APIResponse apiResponse = new APIResponse(APIResponse.USER_NOT_LOGIN, null, "用户未登录");
        if (response instanceof HttpServletResponse) {
            WebUtils.writeJson((HttpServletResponse) response, apiResponse);
        }
        return false;
    }
}

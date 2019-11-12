package indi.zqc.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import indi.zqc.shiro.core.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
@Slf4j
public abstract class AbstractFilter extends AccessControlFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        APIResponse apiResponse = new APIResponse(APIResponse.USER_NOT_LOGIN, null, "用户未登录");
        write(response, apiResponse);
        return false;
    }

    private void write(ServletResponse response, APIResponse apiResponse) {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String jsonObject = JSONObject.toJSONString(apiResponse);
            httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.print(jsonObject);
                out.flush();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }
}

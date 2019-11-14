package indi.zqc.shiro.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Zhu.Qianchang
 * @date 2019/11/14.
 */
@Slf4j
public class WebUtils {

    /**
     * 返回json数据
     *
     * @param response
     * @param data
     */
    public static void writeJson(HttpServletResponse response, Object data) {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(new Gson().toJson(data));
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

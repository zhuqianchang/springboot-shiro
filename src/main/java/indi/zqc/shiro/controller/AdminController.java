package indi.zqc.shiro.controller;

import indi.zqc.shiro.constant.Constants;
import indi.zqc.shiro.core.APIResponse;
import indi.zqc.shiro.core.SessionUser;
import indi.zqc.shiro.token.AdminToken;
import indi.zqc.shiro.util.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端 登录 登出
 *
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @PostMapping("/login")
    public APIResponse<SessionUser> login(String username, String password) {
        SessionUser sessionUser = (SessionUser) AuthUtils.login(new AdminToken(username, password));
        AuthUtils.setSessionUser(Constants.ADMIN_USER, sessionUser);
        return APIResponse.success(sessionUser);
    }

    @GetMapping("/session-info")
    public APIResponse<SessionUser> getSessionUser() {
        return APIResponse.success(AuthUtils.getSessionUser(Constants.ADMIN_USER));
    }

    @PostMapping("/logout")
    public APIResponse logout() {
        AuthUtils.logout(Constants.ADMIN_USER);
        return APIResponse.success();
    }
}
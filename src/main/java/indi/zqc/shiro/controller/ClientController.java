package indi.zqc.shiro.controller;

import indi.zqc.shiro.constant.Constants;
import indi.zqc.shiro.core.APIResponse;
import indi.zqc.shiro.core.SessionUser;
import indi.zqc.shiro.token.ClientToken;
import indi.zqc.shiro.util.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端 登录 登出
 *
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
@RestController
@RequestMapping("client")
public class ClientController {

    @PostMapping("/login")
    public APIResponse<SessionUser> login(String username, String password) {
        SessionUser sessionUser = (SessionUser) AuthUtils.login(new ClientToken(username, password));
        AuthUtils.setSessionUser(Constants.CLIENT_USER, sessionUser);
        return APIResponse.success(sessionUser);
    }

    @GetMapping("/session-info")
    public APIResponse<SessionUser> getSessionUser() {
        return APIResponse.success(AuthUtils.getSessionUser(Constants.CLIENT_USER));
    }

    @PostMapping("/logout")
    public APIResponse logout() {
        AuthUtils.logout(Constants.CLIENT_USER);
        return APIResponse.success();
    }
}

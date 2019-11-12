package indi.zqc.shiro.controller;

import indi.zqc.shiro.constant.Constants;
import indi.zqc.shiro.core.APIResponse;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
@RestController
@RequestMapping("/client/message")
public class MessageController {

    /**
     * 需要 USER 角色权限
     */
    @RequiresRoles(Constants.ROLE_USER)
    @GetMapping("/user")
    public APIResponse getUserMessage() {
        return APIResponse.success("普通用户信息");
    }

    /**
     * 需要 ADMIN 角色权限
     */
    @RequiresRoles(Constants.ROLE_ADMIN)
    @GetMapping("/admin")
    public APIResponse getAdminMessage() {
        return APIResponse.success("管理员信息");
    }
}

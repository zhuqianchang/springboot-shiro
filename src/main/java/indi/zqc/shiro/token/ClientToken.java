package indi.zqc.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
@Getter
@Setter
@AllArgsConstructor
public class ClientToken implements AuthenticationToken {

    private String username;

    private String password;

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}

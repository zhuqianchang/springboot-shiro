package indi.zqc.shiro.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Zhu.Qianchang
 * @date 2019/11/12.
 */
@Getter
@Setter
@AllArgsConstructor
public class SessionUser implements Serializable {

    private static final long serialVersionUID = -2923443782071018572L;

    private String username;
}

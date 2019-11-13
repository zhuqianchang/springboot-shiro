# SpringBoot集成Shiro，实现多身份认证

## 背景
在同一个系统中，可能出现多重身份认证，比如客户端、管理端使用不同的认证，又比如客户端有普通密码登录和单点登录两种认证。

## 使用技术
* SpringBoot 2.0.3.RELEASE
* Shiro 1.3.2
* shiro-redis 3.2.3
* Lombok
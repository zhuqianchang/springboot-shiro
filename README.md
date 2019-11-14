# SpringBoot集成Shiro，实现多身份认证

## 背景
在同一个系统中，可能出现多重身份认证，比如客户端、管理端使用不同的认证，又比如客户端有普通密码登录和单点登录两种认证。

## 使用技术
* SpringBoot 2.0.3.RELEASE
* Shiro 1.3.2
* shiro-redis 3.2.3
* Lombok
* Swagger2 2.8.0

## 实现功能
* 集成Shiro，实现多身份认证和授权
* 开启权限注解，实现注解权限拦截
* 集成Redis，支持单例、集群和哨兵模式
* 集成Swagger2，方便API调用测试

## Swagger页面
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## GitHub地址
[https://github.com/zhuqianchang/springboot-shiro](https://github.com/zhuqianchang/springboot-shiro)

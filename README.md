### Spring Security OAuth2.0 + JWT 实现单点登录

#### Spring Security OAuth2.0 的四种授权方式

##### 1.1 授权码模式

> 将获取code和token合并到一步中
>
> http://localhost:8081/oauth/authorize?response_type=code&client_id=order-client&scope=all



> 首先获取授权code，然后通过code获取token
>
> http://localhost:8081/oauth/authorize?response_type=code&client_id=user-client&scope=all&redirect_uri=http://www.baidu.com
>
> 浏览器跳转到	https://www.baidu.com/?code=DctdGQ
>
> 使用 code 获取 token
>
> http://localhost:8081/oauth/token?grant_type=authorization_code&code=DctdGQ&username=admin&password=123456&client_id=user-client&client_secret=user-secret-8888&redirect_uri=http://www.baidu.com

```json
{
    "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjEwNDQsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiYjlhZTJjMzMtNjBhOS00ZTAwLThlNjYtNzU2M2RjODg1MmU3IiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.BXoQBuDFbx7x81ShSZaRM5FvaFWAYWEXEUgPlP2-UMk",
    "token_type":"bearer",
    "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTU1NzMwNDQsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNmVjZDk5MmEtOGI1Zi00NjA2LTk1NmItMmNhYjk4MzI0Yjg2IiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYjlhZTJjMzMtNjBhOS00ZTAwLThlNjYtNzU2M2RjODg1MmU3In0.U6LNCmjZNqlxLc3MzNanNaJrJo7PbAwMK-as07QPih0",
    "expires_in":7199,
    "scope":"all",
    "jti":"b9ae2c33-60a9-4e00-8e66-7563dc8852e7"
}
```

##### 1.2 密码模式

> http://localhost:8081/oauth/token?grant_type=password&username=admin&password=123456&client_id=order-client&client_secret=order-secret-8888

```json
{
  "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjEyMjgsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiZjhmNjkwNzItNjk2My00MGE5LWE5YWUtMWE5YzQ1MzI1OTA2IiwiY2xpZW50X2lkIjoib3JkZXItY2xpZW50Iiwic2NvcGUiOlsiYWxsIl19.nMvNO76FZl0y0mCUZNv9TBvw7dxJqTk69xGAQ45ZTZc",
    "token_type":"bearer",
    "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTU1NzMyMjgsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNjk1Y2E4M2QtZjVjMS00MjM4LThiMDYtOTkwMDk4MTJkZGU4IiwiY2xpZW50X2lkIjoib3JkZXItY2xpZW50Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImY4ZjY5MDcyLTY5NjMtNDBhOS1hOWFlLTFhOWM0NTMyNTkwNiJ9.owg9G_N1CWvToQWcxnY-Aq-pLXtaLNbM6qhrsqpAaiE",
    "expires_in":7199,
    "scope":"all",
    "jti":"f8f69072-6963-40a9-a9ae-1a9c45325906"
}
```

##### 1.3 简化模式

> http://localhost:8081/oauth/authorize?response_type=token&client_id=user-client&client_secret=user-secret-8888&scope=all&redirect_uri=http://www.baidu.com
>
> 浏览器返回
>
> https://www.baidu.com/#access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjEzNDgsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiMWZkNDc2MzgtODI2OC00ZWU2LTkxYmItYzdlMDQ3MTQ0MmIwIiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.ujUPZOWgWwhuXYmWtmV0l4d9308S1gzeJodZLQglJBA&token_type=bearer&expires_in=7199&jti=1fd47638-8268-4ee6-91bb-c7e0471442b0

##### 1.4 客户端模式

> http://localhost:8081/oauth/token?client_id=user-client&client_secret=user-secret-8888&grant_type=client_credentials

```json
{ 	    "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNTk1MzIxNDMyLCJqdGkiOiJjNTljMGM5NS0yNDgwLTQ4NWQtOWZhYS0zMjE5OTllNDJmYTYiLCJjbGllbnRfaWQiOiJ1c2VyLWNsaWVudCJ9.dHMRaw4jXwGGEsQJxokAHd-CLKYjT71ZW-17Baw9THw",
    "token_type":"bearer",
    "expires_in":7199,
    "scope":"all",
    "jti":"c59c0c95-2480-485d-9faa-321999e42fa6"
}
```

##### 1.5 带 token 访问

> 不需要认证：	 http://localhost:8081/test3
>
> ```json
> test3 success: Tue Jul 21 15:44:53 CST 2020
> ```
>
> 需要认证：
>
> 不带 token 访问
>
> http://localhost:8081/auto/test
>
> ```json
> {
>     "timestamp": "2020-07-21T07:32:20.383+0000",
>     "status": 401,
>     "error": "Unauthorized",
>     "message": "Unauthorized",
>     "path": "/auto/test"
> }
> ```
>
> http://localhost:8081/demo/test
>
> ```json
> {
>     "timestamp": "2020-07-21T07:34:24.209+0000",
>     "status": 401,
>     "error": "Unauthorized",
>     "message": "Unauthorized",
>     "path": "/demo/test"
> }
> ```
>
> 带 token 访问
>
> http://localhost:8081/demo/test?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjQ4MjQsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJjNzM3NmUyMi1lOTRkLTRjZjktYjUyZS03Zjg5YzQ5MmVjYzIiLCJjbGllbnRfaWQiOiJvcmRlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.S_EnUvucm9wy-stS4jcKz8MZUJOOzYv_PGk4iSfDmYU
>
> ```json
> demo test success: Tue Jul 21 15:47:21 CST 2020
> ```

##### 1.6 检验和刷新 token

> 检验 token
>
> http://localhost:8081/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjQ4MjQsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJjNzM3NmUyMi1lOTRkLTRjZjktYjUyZS03Zjg5YzQ5MmVjYzIiLCJjbGllbnRfaWQiOiJvcmRlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.S_EnUvucm9wy-stS4jcKz8MZUJOOzYv_PGk4iSfDmYU
>
> ```json
> {
>     "user_name": "admin",
>     "scope": [
>         "all"
>     ],
>     "active": true,
>     "exp": 1595324824,
>     "authorities": [
>         "ROLE_ADMIN"
>     ],
>     "jti": "c7376e22-e94d-4cf9-b52e-7f89c492ecc2",
>     "client_id": "order-client"
> }
> ```
>
> 刷新 token
>
> http://localhost:8081/oauth/token?grant_type=refresh_token&client_id=user-client&client_secret=user-secret-8888&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTU1NzMwNDQsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNmVjZDk5MmEtOGI1Zi00NjA2LTk1NmItMmNhYjk4MzI0Yjg2IiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYjlhZTJjMzMtNjBhOS00ZTAwLThlNjYtNzU2M2RjODg1MmU3In0.U6LNCmjZNqlxLc3MzNanNaJrJo7PbAwMK-as07QPih0
>
> ```json
> {
>     "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjYzNTksInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNzk2ZWY0M2QtMzUxOS00N2QxLTg0MTYtNzQ3MThhMTJjMjNjIiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.SPR1_o0nVegPgziVrIwLy4NILq-NWHClm97RnBfNUY0",
>     "token_type": "bearer",
>     "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTU1NzMwNDQsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNmVjZDk5MmEtOGI1Zi00NjA2LTk1NmItMmNhYjk4MzI0Yjg2IiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiNzk2ZWY0M2QtMzUxOS00N2QxLTg0MTYtNzQ3MThhMTJjMjNjIn0.mxnB-BUqfdqEfZMaoC75gPKRLNGBUMO1f_E7GelE9rA",
>     "expires_in": 7199,
>     "scope": "all",
>     "jti": "796ef43d-3519-47d1-8416-74718a12c23c"
> }
> ```

##### 1.7 资源服务器改造

将认证服务改造为资源服务器

> 不需要认证：
>
> http://localhost:8081/test3
>
> ```json
> test3 success: Tue Jul 21 17:07:15 CST 2020
> ```
>
> 需要认证：
>
> http://localhost:8081/demo/test
>
> ```json
> {
>     "error": "unauthorized",
>     "error_description": "Full authentication is required to access this resource"
> }
> ```
>
> 带token访问：
>
> http://localhost:8081/demo/test?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjQ4MjQsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJjNzM3NmUyMi1lOTRkLTRjZjktYjUyZS03Zjg5YzQ5MmVjYzIiLCJjbGllbnRfaWQiOiJvcmRlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.S_EnUvucm9wy-stS4jcKz8MZUJOOzYv_PGk4iSfDmYU
>
> ```json
> demo test success: Tue Jul 21 17:09:35 CST 2020
> ```

#### 二、Oauth2.0 + JWT 实现分布式登录

上面已经搭建好认证服务，接下来再创建两个服务 user-server 和 order-server 作为资源服务器

以 order-server 为例

测试

> 不需要认证：
>
> http://localhost:8083/order/no-oauth/test
>
> ```json
> Tue Jul 21 17:19:25 CST 2020: order test1 success
> ```
>
> 需要认证：
>
> http://localhost:8083/order/need-oauth/test
>
> ```json
> {
>     "error": "unauthorized",
>     "error_description": "Full authentication is required to access this resource"
> }
> ```
>
> 带token访问：
>
> http://localhost:8083/order/need-oauth/test?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzMjQ4MjQsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJjNzM3NmUyMi1lOTRkLTRjZjktYjUyZS03Zjg5YzQ5MmVjYzIiLCJjbGllbnRfaWQiOiJvcmRlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.S_EnUvucm9wy-stS4jcKz8MZUJOOzYv_PGk4iSfDmYU
>
> ```json
> Tue Jul 21 17:21:33 CST 2020: order test2 success
> ```






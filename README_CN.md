# ontid-app-server
这个是三方对接的后台demo



## 登录对接步骤

1. 你需要先联系我们注册应用方的 ontid, 在 [Constant](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/util/Constant.java) 文件中填写注册时返回的参数
```text
    /**
     * 应用方的WIF
     */
    public static final String ONTID_PROVIDE_WIF = "";
    /**
     * ONT ID后台的公钥
     */
    public static final String ONTID_PUBLIC_KEY = "";
    /**
     * 应用方的Ontid
     */
    public static final String ONTID = "";

```

2. ONTID 用户登录页面会返回 ```refresh_token``` 和 ```access_token```，前端把数据返回给后台。

3. 解析 ```refresh_token``` , 得到登录用户的ontid。

4. 使用 ```access_token``` 访问其他接口。 例子：在 [ControlDemo](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/ControlDemo.java) 文件中填写第二步获取的 token 的参数,再访问接口。 
```text
   String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";
   String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";

   String getBalanceUrl = "http://192.168.3.67:10331/api/v1/ontid/getbalance";
   String refreshUrl = "http://192.168.3.67:10331/api/v1/access/refresh";
```

## 支付和调用合约对接步骤

发起支付订单请求例子，包含回调接口：[PaymentController](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/controller/PaymentController.java)

## JWT 验证签名



### 如何生成 JWT
参考 [MyJWTUtils](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/util/MyJWTUtils.java) 中的 ```sign``` 方法

### JWT 如何验签
参考 [MyJWTUtils](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/util/MyJWTUtils.java) 中的 ```verify``` 方法

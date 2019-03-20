# ontid-app-server
这个是三方对接的后台demo

### 验签
参考MyJWTUtils中的verify方法

### 生成jwt
参考MyJWTUtils中的sign方法

### 获取用户相关信息

1.你需要先联系我们注册好wif和ontid,在Constant文件中修改参数
```text
    /**
     * 三方的WIF
     */
    public static final String ONTID_PROVIDE_WIF = "";
    /**
     * ontid后台的公钥
     */
    public static final String ONTID_PUBLIC_KEY = "";
    /**
     * 三方的Ontid
     */
    public static final String ONTID = "";

```

2.通过用户登录页面获取到两个token，然后在ControlDemo文件中修改对应的参数
```text
    String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";
    String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";

```
3.在ControlDemo文件修改对应的请求接口
```text
    String getBalanceUrl = "http://192.168.3.67:10331/api/v1/ontid/getbalance";
    String refreshUrl = "http://192.168.3.67:10331/api/v1/access/refresh";
```
4.启动服务，请求接口，获取用户相关数据

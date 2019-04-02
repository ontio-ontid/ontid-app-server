English | [中文](README_CN.md)
# ontid-app-server
This is demo for java backend developer.



## Login docking step

1. You need to contact us first and Registered the ontid of Applicant, Fill in the parameters returned at registration in [Constant](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/util/Constant.java). 
```text
    /**
     * provider WIF
     */
    public static final String ONTID_PROVIDE_WIF = "";
    /**
     * ONT ID backend public key
     */
    public static final String ONTID_PUBLIC_KEY = "";
    /**
     * provider Ontid
     */
    public static final String ONTID = "";

```

2. After successful login,you can get ```refresh_token``` 和 ```access_token``` from [login](http://139.219.136.188:10390/#/signin) in the testing environment。

3. analysis ```refresh_token``` , get the ontid of user。

4. use  ```access_token``` access to other interfaces。 For example： [ControlDemo](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/ControlDemo.java)
```text
   String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";
   String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";

   String getBalanceUrl = "http://192.168.3.67:10331/api/v1/ontid/getbalance";
   String refreshUrl = "http://192.168.3.67:10331/api/v1/access/refresh";
```

## Payment and invocation contract docking steps

An example of initiating a payment order request, including a callback interface：[PaymentController](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/controller/PaymentController.java)

## JWT Verifying signature



### How to generate JWT

Reference ```sign```  in [MyJWTUtils](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/util/MyJWTUtils.java)   

### JWT How to verify the signature
Reference ```verify``` in [MyJWTUtils](https://github.com/ontio-ontid/ontid-app-server/blob/master/src/main/java/com/github/ontid_demo/util/MyJWTUtils.java)

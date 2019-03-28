package com.github.ontid_demo;

import com.github.ontid_demo.util.Constant;
import com.github.ontid_demo.util.MyJWTUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ControlDemo {

    /**
     * 你可以从这个网站上获取token http://139.219.136.188:10390/#/signin
     */

    /**
     * 用户登录后获取的
     *
     * @return
     */
    String refreshToken = "";
    String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTM4NDMxMzYsImlhdCI6MTU1Mzc1NjczNiwianRpIjoiNmJhMjdiMjNhOGY1NDc5ODg3MGJmMTVmMWJiZDhhMzciLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVV6TXdVNXRicXFlM0UyS1N1aWlHUW1VbThoR1JUamczRSJ9fQ.MDE5Y2MzZTMwNjZmZDc4YzBjN2RjMzAwYzU1OTMyNmY5YmNjMWVjMWJjZTE1YTIyODlkMjUzODUxYmNiNTg1MzEwMjY5ZDMxYWU5Y2M1YzMyOWIyNjNmNGJjNjc1YmQ0OTM2M2YxZmJkMDExNTNmOTM3NTA1NmUyZjZhOTg0OWQwMQ";

    //测试接口
    String getBalanceUrl = "http://139.219.136.188:10331/api/v1/ontid/getbalance";
    String refreshUrl = "http://139.219.136.188:10331/api/v1/access/refresh";

    /**
     * 用refreshToken刷新 AccessToken
     *
     * @return
     */
    @GetMapping(value = "/refresh/access")
    public String refreshAccessToken() throws Exception {
        return post(Constant.REFRESH_TOKEN, refreshToken, refreshUrl).toString();
    }

    @Autowired
    MyJWTUtils myJWTUtils;
    /**
     * 用AccessToken获取用户余额
     */
    @GetMapping(value = "/get/balance")
    public String getBalance() throws Exception {
        return post(Constant.ACCESS_TOKEN, accessToken, getBalanceUrl).toString();
    }

    private Object post(String type, String token, String refreshUrl) throws Exception {
        HttpPost httpPost = new HttpPost(refreshUrl);
        try {
            httpPost.setHeader(type, token);
            CloseableHttpClient httpClient;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(100000).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            return EntityUtils.toString(entity);
        } finally {
            httpPost.releaseConnection();
        }
    }
}

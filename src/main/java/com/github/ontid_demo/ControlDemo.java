package com.github.ontid_demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ontid_demo.util.Constant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ControlDemo {

    /**
     * 用户登录后获取的
     *
     * @return
     */
    String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";
    String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTMxNDk4MTEsImlhdCI6MTU1MzA2MzQxMSwianRpIjoiMDE3Y2QxMmFjNTAxNDYyZWFlNjgwYjZkYmJlM2MwYWIiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QVZOQUppNW9waGdYTHRtclpGRDc0NjZiS2dkMnY3VXgxNSJ9fQ.MDE3Y2M2MzgwNjYwYWQyZGUyNDU3Mzc0MDgxMjE1NWZhYzQ5NmYwMzA1MzE1MjdmNGIxMWI3ZmIxMjYyMzFkNzhmN2NhNDAxMDVjOTgyOTVkMGJlNDFhZjVhYjA1ODYzOTI1NDI1ODhhN2RhYmNiNjAwZTVjNzUyZTY5MGQ2ZGU0OQ";

    String getBalanceUrl = "http://192.168.3.67:10331/api/v1/ontid/getbalance";
    String refreshUrl = "http://192.168.3.67:10331/api/v1/access/refresh";

    /**
     * 用refreshToken刷新 AccessToken
     *
     * @return
     */
    @GetMapping(value = "/refresh/access")
    public String refreshAccessToken() throws Exception {
        return post(Constant.REFRESH_TOKEN, refreshToken, refreshUrl).toString();
    }

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

package com.github.ontid_demo;

import com.alibaba.fastjson.JSON;
import com.github.ontid_demo.util.Constant;
import com.github.ontid_demo.util.MyJWTUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 根据用户的access token去查询用户相关信息
 */
@Slf4j
@RestController
public class ControlDemo {

    /**
     * 你可以从这个网站上获取token http://139.219.136.188:10390/#/signin
     * you can get token from : http://139.219.136.188:10390/#/signin
     */

    /**
     * 用户登录后获取的
     * get info after user login
     *
     * @return
     */
    String refreshToken = "";
    String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJhdWQiOiJkaWQ6b250OkFNdmpVV1o2Y25BQVVzQk43dWpBQnRMUzlHbWVoOFNQU2oiLCJpc3MiOiJkaWQ6b250OkFhdlJRcVhlOVByYVY1dFlnQnF2VjRiVXE4TFNzdmpjV1MiLCJleHAiOjE1NTUwNTU3MzksImlhdCI6MTU1NDk2OTMzOSwianRpIjoiZjQ1ZmMyMmVkMjBhNDFhMGE1YzdhMzZhYjIxZTkxNTAiLCJjb250ZW50Ijp7InR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJvbnRpZCI6ImRpZDpvbnQ6QU14clNHSHl4Z25XUzZxYzFRalROWWVFYXczWDNEdnpoZiJ9fQ.MDFiZDVhYWQ2MzRkNzlkOTU3ZjE3YWYyNDc3MDUyZGUxNzJjYjdmYjgxZWViOThmYTg2ODgyM2ZiYjM5ZjIyMjZiYWZlYTlkNGFkNjMwMzM0OWY4N2YyYzBiZDlmNzg5M2IzYjhiYjdkZTg1MjFmYzQ1MDMwOGY2NGRmM2E5ZjkwNg";

    //测试接口
    //刷新accesstoken
    String refreshUrl = "http://139.219.136.188:10331/api/v1/access/refresh";
    //获取用户相关信息
    String infoUrl = "http://139.219.136.188:10331/api/v1/ontid/info";
    //获取用户批量订单
    String orderRangeUrl = "http://139.219.136.188:10331/api/v1/ontid/query/order/range";
    //获取用户订单详情
    String orderDetailUrl = "http://139.219.136.188:10331/api/v1/ontid/query/order";

    /**
     * 用refreshToken刷新 AccessToken
     * refresh the accessToken by refreshToken
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
     * 用AccessToken获取用户信息
     * get info from access token
     */
    @GetMapping(value = "/get/info")
    public String getBalance() throws Exception {
        return post(Constant.ACCESS_TOKEN, accessToken, infoUrl).toString();
    }

    /**
     * 用AccessToken获取用户订单
     * get balance from access token
     */
    @GetMapping(value = "/query/order/{currentPage}/{size}")
    public String queryOrder(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size) throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put("currentPage", currentPage);
        param.put("size", size);
        return postParam(Constant.ACCESS_TOKEN, accessToken, orderRangeUrl, param).toString();
    }

    /**
     * 用AccessToken获取用户订单详情
     * get balance from access token
     */
    @GetMapping(value = "/query/order/{orderId}")
    public String queryOrderDetail(@PathVariable("orderId") String orderId) throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        return postParam(Constant.ACCESS_TOKEN, accessToken, orderDetailUrl, param).toString();
    }

    private Object post(String type, String token, String restfulUrl) throws Exception {
        return postParam(type, token, restfulUrl, new HashMap<>());
    }

    private Object postParam(String type, String token, String refreshUrl, Map<String, Object> data) throws Exception {
        HttpPost httpPost = new HttpPost(refreshUrl);
        try {
            httpPost.setHeader(type, token);
            httpPost.setEntity(new StringEntity(JSON.toJSONString(data), ContentType.APPLICATION_JSON));
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

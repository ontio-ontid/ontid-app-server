package com.github.ontid_demo;

import com.alibaba.fastjson.JSON;
import com.github.ontid_demo.util.Constant;
import com.github.ontid_demo.util.MyJWTUtils;
import com.github.ontid_demo.util.ProviderUtil;
import com.github.ontid_demo.util.TransactionUtil;
import com.github.ontid_demo.util.myjwt.MyJwt;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Helper;
import com.github.ontio.crypto.SignatureScheme;

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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

/**
 * 商家直接获取信息
 */
@Slf4j
@RestController
public class ControlProviderDemo {

    @Autowired
    ProviderUtil providerUtil;

    @Autowired
    TransactionUtil transactionUtil;

    String user = "did:ont:AMxrSGHyxgnWS6qc1QjTNYeEaw3X3Dvzhf";
    String wallet = "ASqT8qw2TMXCcTLQtpmbhTrpPhWDj8qCRV";
    String receiver = "ASqT8qw2TMXCcTLQtpmbhTrpPhWDj8qCRV";
    //获取用户相关信息
    private String generateOrder = "http://139.219.136.188:10331/api/v1/provider/request/order";
    //获取用户批量订单
    private String orderRangeUrl = "http://139.219.136.188:10331/api/v1/provider/query/order/range";
    //获取用户订单详情
    private String orderDetailUrl = "http://139.219.136.188:10331/api/v1/provider/query/order";

    //回调地址的接口
    private String callbackAddress = "http://139.219.136.188:11111/ontid/payment/callback";

    /**
     * 生成订单
     * generateOrder
     *
     * @return
     */
    @GetMapping(value = "/generateOrder")
    public String refreshAccessToken() throws Exception {
        Map txMap = transactionUtil.transferParam("ong", wallet, receiver, 1000000000);
        Map appParams = providerUtil.getAppOrderParams("test", "www.baidu.com", "这是一个测试内容", Constant.ONTID, callbackAddress);
        long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
        Date expireDate = new Date(currentTime);
        byte[] bytes = Account.getPrivateKeyFromWIF(Constant.ONTID_PROVIDE_WIF);
        Account account = new Account(Helper.hexToBytes(Helper.toHexString(bytes)), SignatureScheme.SHA256WITHECDSA);
        String jwt = MyJwt.create().withIssuer(Constant.ONTID).withExpiresAt(expireDate).withAudience(Constant.ONTID).withIssuedAt(new Date()).
                withJWTId(UUID.randomUUID().toString().replace("-", "")).withClaim("invokeConfig", txMap).withClaim("app", appParams).sign(account);
        Map request = new HashMap();
        request.put(Constant.APP_TOKEN, jwt);
        request.put("user", user);
        return postParam(generateOrder, request).toString();
    }

    @Autowired
    MyJWTUtils myJWTUtils;

    @GetMapping(value = "/query/order/provider/{currentPage}/{size}")
    public String queryOrder(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size) throws Exception {
        Map appParams = providerUtil.getAppQueryParams(Constant.ONTID);
        long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
        Date expireDate = new Date(currentTime);
        byte[] bytes = Account.getPrivateKeyFromWIF(Constant.ONTID_PROVIDE_WIF);
        Account account = new Account(Helper.hexToBytes(Helper.toHexString(bytes)), SignatureScheme.SHA256WITHECDSA);
        String jwt = MyJwt.create().withIssuer(Constant.ONTID).withExpiresAt(expireDate).withAudience(Constant.ONTID).withIssuedAt(new Date()).
                withJWTId(UUID.randomUUID().toString().replace("-", "")).withClaim("app", appParams).sign(account);

        HashMap<String, Object> param = new HashMap<>();
        param.put("currentPage", currentPage);
        param.put("size", size);
        param.put(Constant.APP_TOKEN, jwt);
        return postParam(orderRangeUrl, param).toString();
    }


    @GetMapping(value = "/query/order/provider/{orderId}")
    public String queryOrderDetail(@PathVariable("orderId") String orderId) throws Exception {
        Map appParams = providerUtil.getAppQueryParams(Constant.ONTID);
        long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
        Date expireDate = new Date(currentTime);
        byte[] bytes = Account.getPrivateKeyFromWIF(Constant.ONTID_PROVIDE_WIF);
        Account account = new Account(Helper.hexToBytes(Helper.toHexString(bytes)), SignatureScheme.SHA256WITHECDSA);
        String jwt = MyJwt.create().withIssuer(Constant.ONTID).withExpiresAt(expireDate).withAudience(Constant.ONTID).withIssuedAt(new Date()).
                withJWTId(UUID.randomUUID().toString().replace("-", "")).withClaim("app", appParams).sign(account);

        HashMap<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        param.put(Constant.APP_TOKEN, jwt);
        return postParam(orderDetailUrl, param).toString();
    }


    private Object postParam(String refreshUrl, Map<String, Object> data) throws Exception {
        HttpPost httpPost = new HttpPost(refreshUrl);
        try {
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

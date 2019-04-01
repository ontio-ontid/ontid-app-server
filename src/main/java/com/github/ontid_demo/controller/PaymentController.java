package com.github.ontid_demo.controller;

import com.alibaba.fastjson.JSON;
import com.github.ontid_demo.util.Constant;
import com.github.ontid_demo.util.myjwt.MyJwt;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Helper;
import com.github.ontio.core.transaction.Transaction;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;


@RestController
public class PaymentController {
    private final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping(value = "/ontid/payment/callback")
    public Object callback(@RequestBody LinkedHashMap<String, String> obj) {
        log.info("callback:{}", JSON.toJSONString(obj));
        return obj;
    }

    public static void main(String[] args) {
        requestOrder();
    }
    public static void requestOrder(){
        String rrequestOrderUrl = "http://127.0.0.1:10332/api/v1/ontid/request/order";
        String ontid = "did:ont:ANS9JnoER5WqcE75jHeYZAuSWRvTjP69WH";
        String contractHash = "0200000000000000000000000000000000000000";
        String method = "transfer";
        String payer = "ANS9JnoER5WqcE75jHeYZAuSWRvTjP69WH";
        List argsList = new ArrayList();
        Map arg0 = new HashMap();
        arg0.put("name","from");
        arg0.put("value","Address:ANS9JnoER5WqcE75jHeYZAuSWRvTjP69WH");
        Map arg1 = new HashMap();
        arg1.put("name","to");
        arg1.put("value","Address:AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ");
        Map arg2 = new HashMap();
        arg2.put("name","amount");
        arg2.put("value",100);
        argsList.add(arg0);
        argsList.add(arg1);
        argsList.add(arg2);

        long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
        Date expireDate = new Date(currentTime);

        try {
            OntSdk ontSdk = getOntSdk();

            Map map = new HashMap();
            map.put("callback","http://127.0.0.1:1111/ontid/payment/callback");
            map.put("ontid",ontid);
            //String privateKey = "03328eee364bc513c3ec5dd389d43e2b2eed4e99aedb09aaf4e7e59dcd5c9342fb";
            //
            //{"address":"ANS9JnoER5WqcE75jHeYZAuSWRvTjP69WH","salt":"5kh9AcbKuJVttehC1Qu3IQ==","label":"42efcf68","type":"I","parameters":{"curve":"P-256"},"scrypt":{"dkLen":64,"n":16384,"p":8,"r":8},"key":"clJjGWPdxpI6SCRBkz4QsLI0xDulrd6TOtzmUb+l5rs1Ui+kkNXFJOvkLyqrlnu7","algorithm":"ECDSA"}
            String privateKey = Account.getGcmDecodedPrivateKey("clJjGWPdxpI6SCRBkz4QsLI0xDulrd6TOtzmUb+l5rs1Ui+kkNXFJOvkLyqrlnu7","12345678a","ANS9JnoER5WqcE75jHeYZAuSWRvTjP69WH",Base64.getDecoder().decode("5kh9AcbKuJVttehC1Qu3IQ=="),16384, SignatureScheme.SHA256WITHECDSA);

            Account account = new Account(Helper.hexToBytes(privateKey), SignatureScheme.SHA256WITHECDSA);
            if(false) {
//            ontSdk.getWalletMgr().createIdentityFromPriKey("12345678a",privateKey);
//            ontSdk.getWalletMgr().writeWallet();

//            String ontidregistry = "did:ont:" + account;//"did:ont:"+account2.getAddressU160().toBase58();//
//            Identity identity = ontSdk.getWalletMgr().getWallet().getIdentity(ontidregistry);
//            ontSdk.nativevm().ontId().sendRegister(identity,"12345678a",account,20000,500);
            }
            Map params = getParams(contractHash,method,argsList,payer);
            Map req = new HashMap();
            Map tmp = new HashMap();
            tmp.put("invokeConfig",params);
           // byte[] bys = java.util.Base64.getDecoder().decode(order.getToken());
            req.put("action","invoke");
            req.put("params",tmp);

            if(false){
                Transaction[] tansactions = ontSdk.makeTransactionByJson(JSON.toJSONString(req));
                ontSdk.addSign(tansactions[0],account);
                ontSdk.getConnect().sendRawTransactionPreExec(tansactions[0].toHexString());
                System.exit(0);
            }

            String jwt =  MyJwt.create().withIssuer(Constant.ONTID).withExpiresAt(expireDate).withAudience(Constant.ONTID).withIssuedAt(new Date()).
                    withJWTId(UUID.randomUUID().toString().replace("-", "")).withClaim("invokeConfig", params).withClaim("app",map).sign(account);
            Map request = new HashMap();
            request.put("data",jwt);
            request.put("user",ontid);
            Object response = post(request,rrequestOrderUrl);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object post(Map<String, Object> data, String refreshUrl) {
        HttpPost httpPost = new HttpPost(refreshUrl);
        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(data), ContentType.APPLICATION_JSON));
            CloseableHttpClient httpClient;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(100000).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return "";
    }
    public static Map getParams(String contractHash, String method, List argsList, String payer) {
        Map str = new HashMap();
        Map parms = new HashMap();
        Map invokeConfig = new HashMap();
        List functions = new ArrayList();
        Map function = new HashMap();

        function.put("operation",method);
        function.put("args",argsList);

        functions.add(function);

        invokeConfig.put("contractHash",contractHash);
        invokeConfig.put("functions",functions);
        invokeConfig.put("payer",payer);
        invokeConfig.put("gasLimit",40000);
        invokeConfig.put("gasPrice",500);

        parms.put("invokeConfig",invokeConfig);

        str.put("action","invoke");
        str.put("params",parms);
        return invokeConfig;
    }

    public static OntSdk getOntSdk() throws Exception {
//        String ip = "http://127.0.0.1";
        String ip = "http://polaris5.ont.io";
//        String ip = "http://139.219.129.55";
//        String ip = "http://101.132.193.149";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("NativeOntIdDemo.json");
        return wm;
    }
}

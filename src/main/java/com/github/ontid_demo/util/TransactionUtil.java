package com.github.ontid_demo.util;

import com.alibaba.fastjson.JSON;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class TransactionUtil {
    String requestOrderUrl = "http://127.0.0.1:10332/api/v1/ontid/request/order";
    String ontid = "did:ont:ANS9JnoER5WqcE75jHeYZAuSWRvTjP69WH";

    public Map transferParam(String type, String payer, String toAddress, long amount) {
        String contractHash = "";
        switch (type) {
            case "ong":
                contractHash = "0200000000000000000000000000000000000000";
                break;
            case "ont":
                contractHash = "0100000000000000000000000000000000000000";
                break;
        }
        String method = "transfer";
        return getParams(contractHash, method, payer, toAddress, amount, 20000);
        //预执行检验
//            OntSdk ontSdk = getOntSdk();
//            Map tmp = new HashMap();
//            tmp.put("invokeConfig", params);
//            // byte[] bys = java.util.Base64.getDecoder().decode(order.getToken());
//            req.put("action", "invoke");
//            req.put("params", tmp);
//                Transaction[] tansactions = ontSdk.makeTransactionByJson(JSON.toJSONString(req));
//                ontSdk.addSign(tansactions[0], account);
//                ontSdk.getConnect().sendRawTransactionPreExec(tansactions[0].toHexString());
//                System.exit(0);

    }


    public static Map getParams(String contractHash, String method, String payer, String toAddress, long amount, long gasLimit) {
        List argsList = new ArrayList();
        Map arg0 = new HashMap();
        arg0.put("name", "from");
        arg0.put("value", "Address:" + payer);
        Map arg1 = new HashMap();
        arg1.put("name", "to");
        arg1.put("value", "Address:" + toAddress);
        Map arg2 = new HashMap();
        arg2.put("name", "amount");
        arg2.put("value", amount);
        argsList.add(arg0);
        argsList.add(arg1);
        argsList.add(arg2);

        Map str = new HashMap();
        Map parms = new HashMap();
        Map invokeConfig = new HashMap();
        List functions = new ArrayList();
        Map function = new HashMap();

        function.put("operation", method);
        function.put("args", argsList);

        functions.add(function);

        invokeConfig.put("contractHash", contractHash);
        invokeConfig.put("functions", functions);
        invokeConfig.put("payer", payer);
        invokeConfig.put("gasLimit", gasLimit);
        invokeConfig.put("gasPrice", 500);

        parms.put("invokeConfig", invokeConfig);

        str.put("action", "invoke");
        str.put("params", parms);
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

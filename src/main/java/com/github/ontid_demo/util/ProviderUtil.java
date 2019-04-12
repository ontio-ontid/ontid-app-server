package com.github.ontid_demo.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Component
public class ProviderUtil {
    public Map getAppOrderParams(String name, String logo, String message, String ontid, String callback) {
        Map map = new HashMap();
//        "name": "",
//                "logo":"",
//                "message": "",
//                "ontid": "",
//                "callback": "",
//                "nonce": 123456
        map.put("name", name);
        map.put("logo", logo);
        map.put("message", message);
        map.put("ontid", ontid);
        map.put("callback", callback);
        map.put("nonce", UUID.randomUUID().toString().replace("-", ""));
        return map;
    }

    public Map getAppQueryParams(String ontid) {
        Map map = new HashMap();
        map.put("ontid", ontid);
        return map;
    }
}

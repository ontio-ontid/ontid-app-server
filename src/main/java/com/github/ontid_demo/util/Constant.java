package com.github.ontid_demo.util;

/**
 * 常量
 * {
 *     "action": "providerRegister",
 *     "error": 0,
 *     "desc": "SUCCESS",
 *     "result": {
 *         "public_key": "023dce4aef2d99cec35808029b7de76b98620b8e2f2f533407e3e0645cafe394aa",
 *         "wif": "L41H63GRhFQ9MbPqE2ZVNEnx4ZcnxcomvMXLCRbJfCCJo2uiU5oh",
 *         "manage_public_key": "026d3557e55fffe7bc5a9a8fc0c7361bc48590c17bf4d0d345e3f354bb64a0452a",
 *         "ontid": "did:ont:AaqWLmN3LNqu8QFpuSnoK3QM4g5KC2ZSTC"
 *     },
 *     "version": "v1"
 * }
 * http://139.219.136.188:10331
 */
public class Constant {
    /**
     * 三方的WIF，目前是测试网的
     * provider WIF
     */
    public static final String ONTID_PROVIDE_WIF = "L41H63GRhFQ9MbPqE2ZVNEnx4ZcnxcomvMXLCRbJfCCJo2uiU5oh";
    /**
     * ontid后台的公钥,测试网的
     * ONT ID backend public key
     */
    public static final String ONTID_PUBLIC_KEY = "026d3557e55fffe7bc5a9a8fc0c7361bc48590c17bf4d0d345e3f354bb64a0452a";
    /**
     * 三方的Ontid，目前是测试网的
     * provider Ontid
     *
     */
    public static final String ONTID = "did:ont:AaqWLmN3LNqu8QFpuSnoK3QM4g5KC2ZSTC";

    /**
     * Token 类型
     */
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String APP_TOKEN = "app_token";

    /**
     * 用于收到回调信息的地址
     * to get callback address
     */
    public static final String CALLBACK_ADDRESS = "";

    /**
     * 测试网的请求地址
     * to get callback address
     */
}

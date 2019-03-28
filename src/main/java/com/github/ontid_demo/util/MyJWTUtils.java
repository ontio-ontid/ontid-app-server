package com.github.ontid_demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.ontid_demo.util.myjwt.MyJwt;
import com.github.ontio.account.Account;
import com.github.ontio.common.Helper;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyJWTUtils {

    /**
     * 校验token是否正确
     *
     * @param token Token
     * @return boolean 是否正确
     */

    private void verify(String token) throws Exception {
        DecodedJWT jwt = JWT.decode(token);
        String content = String.format("%s.%s", jwt.getHeader(), jwt.getPayload());

        String signature = Base64ConvertUtil.decode(jwt.getSignature());

        Account account = new Account(false, Helper.hexToBytes(Constant.ONTID_PUBLIC_KEY));
        boolean flag = account.verifySignature(content.getBytes(), Helper.hexToBytes(signature));
        if (!flag) {
            throw new Exception("Token verify error");
        }

        if (jwt.getExpiresAt().before(new Date())) {
            throw new Exception("Token verify error");
        }
    }

    public void verifyRefreshToken(String token) throws Exception {
        if (!getContentType(token).equals(Constant.REFRESH_TOKEN)) {
            throw new Exception("Token verify error");
        }
        verify(token);
    }

    public void verifyAccessToken(String token) throws Exception {
        if (!getContentType(token).equals(Constant.ACCESS_TOKEN)) {
            throw new Exception("Token verify error");
        }
        verify(token);
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token
     * @param claim
     * @return java.lang.String
     */
    public String getClaim(String token, String claim) throws Exception {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            throw new Exception("解密Token中的公共信息出现JWTDecodeException异常");
        }
    }

    /**
     * 获得Token中的用户
     *
     * @param token
     * @return java.lang.String
     */
    public String getContentUser(String token) throws Exception {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return (String) jwt.getClaim("content").asMap().get("ontid");
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            throw new Exception("解密Token中的公共信息出现JWTDecodeException异常");
        }
    }

    /**
     * 获得Token类型
     *
     * @param token
     * @return java.lang.String
     */
    public static String getContentType(String token) throws Exception {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return (String) jwt.getClaim("content").asMap().get("type");
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            throw new Exception("解密Token中的公共信息出现JWTDecodeException异常");
        }
    }

    /**
     * 获得Aud
     *
     * @param token
     * @return java.lang.String
     */
    public String getAud(String token) throws Exception {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim("aud").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            throw new Exception("解密Token中的公共信息出现JWTDecodeException异常");
        }
    }

    /**
     * 生成jwt
     *
     * @return
     */
    public String sign(String aud, Object obj) throws Exception {
//        ONTID后台的ONTID
//        payload.put("iss", secureConfig.getWalletOntid());
////        过期时间
//        payload.put("exp", new Date().getTime() + Constant.TOKEN_EXPIRE);
////        受众
//        payload.put("aud", ontidProvide);
////        签发时间
//        payload.put("iat", new Date().getTime());
////        编号
//        payload.put("jti", jti);
        //设置token到期时间
        Date expireDate = new Date();
        return MyJwt.create().withIssuer(Constant.ONTID).withExpiresAt(expireDate).withAudience(aud).withIssuedAt(new Date()).
                withJWTId(UUID.randomUUID().toString().replace("-", "")).withClaim("content", obj).sign();
    }


}

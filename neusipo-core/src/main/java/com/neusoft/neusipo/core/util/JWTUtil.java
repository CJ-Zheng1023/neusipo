package com.neusoft.neusipo.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: jwt工具类
 * @author: zhengchj
 * @create: 2019-08-23 15:54
 **/
public class JWTUtil {
    /**
     * token有效期限
     */
    public static final int JWT_EXPIRE_DAY = 7;
    /**
     * jwt密钥
     */
    public static final String JWT_SECRET = "123456";
    /**
     * 用户名健值，用于jwt存储
     */
    public static final String JWT_CLAIM_USERNAME = "username";

    public static boolean verify(String token, String username, String secret){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(JWT_CLAIM_USERNAME, username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(JWT_CLAIM_USERNAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static String sign(String username, String secret, Timestamp expireTime) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, JWT_EXPIRE_DAY);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Date expireDate = expireTime != null && expireTime.getTime() < c.getTimeInMillis() ?
                new Date(expireTime.getTime()) : c.getTime();
        return JWT.create()
                .withClaim(JWT_CLAIM_USERNAME, username)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }
}

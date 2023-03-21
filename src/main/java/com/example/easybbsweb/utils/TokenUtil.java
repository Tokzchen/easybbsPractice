package com.example.easybbsweb.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.easybbsweb.domain.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {
    private static final long EXPIRE_TIME= 10*60*60*1000;   //十小时
    private static final String TOKEN_SECRET="123456";  //密钥盐
    /**
     * 签名生成
     * @param staff
     * @return
     */
    public static String sign(UserInfo staff){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("email", staff.getEmail())
                    .withClaim("userId",staff.getUserId())
//                    .withAudience(staff.getUsername())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
    /**
     * 签名验证
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.err.println("认证通过：");
            System.err.println("email: " + jwt.getClaim("email").asString());
            System.err.println("userId: " + jwt.getClaim("userId").asString());
            System.err.println("过期时间：      " + jwt.getExpiresAt());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //该方法需要在token验证完成后进行使用
    @Deprecated
    public static String getCurrentUserName(String token){
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
        return jwt.getClaim("email").asString();
    }

    public static String getCurrentEmail(String token){
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
        return jwt.getClaim("email").asString();
    }public static String getCurrentUserId(String token){
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
        return jwt.getClaim("userId").asString();
    }

}

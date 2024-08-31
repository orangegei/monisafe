package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "1659227126@qq.com");
        claims.put("username", "sht");

        // 生成jwt的代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60))//添加过期时间 60min
                .sign(Algorithm.HMAC256("monisafe"));//指定算法，配置密钥

        System.out.println(token);
    }

    @Test
    public void testParse() {
        // 用户传递的token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImVtYWlsIjoiMTY1OTIyNzEyNkBxcS5jb20iLCJ1c2VybmFtZSI6InNodCJ9LCJleHAiOjE3MjQ4NDE5OTZ9" +
                ".0FkuwoyWyxn-uQLjv8GUAiyJ7IdtOLXUzxlHKwsD15g";

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("monisafe")).build().verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));

        // 篡改头部和载荷部分的数据
        // 修改密钥
        // token过期


    }


}

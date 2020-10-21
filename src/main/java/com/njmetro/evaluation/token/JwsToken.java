package com.njmetro.evaluation.token;

import com.njmetro.evaluation.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

/**
 * 创建和校验令牌，JWS 是带有签名的 JWT
 *
 * @author RCNJTECH
 * @date 2020/4/12 12:15
 */
@Slf4j
public class JwsToken {

    /**
     * 根据所使用的签名算法来生成足够强度的 SecretKey
     *
     * <p>使用 Base64 或者 Base64URL 的 encode() 方法获取 SecretKey 以字符串形式的值：</p>
     * <pre>
     * String secretKey = Encoders.BASE64.encode(SECRET_KEY.getEncoded());
     * </pre>
     */
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(DigestUtils.sha256("KEY"));

    /**
     * 创建 JWS
     *
     * @param user 用户
     * @return JWS
     */
    public static String create(User user) {
        return Jwts.builder()
                .claim("userName", user.getUserName())
                .claim("role", user.getRole())
                .claim("name",user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000))
                .setId(UUID.randomUUID().toString())
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 读取 JWS
     *
     * @param token 令牌
     * @return Claims
     */
    public static Claims read(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            log.error("Token 失效：{}", token);
            return null;
        }
    }

}

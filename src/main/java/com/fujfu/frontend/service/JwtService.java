package com.fujfu.frontend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Ocean
 * @date 2021/3/8 6:03 PM
 */
@Service
@AllArgsConstructor
@Slf4j
public class JwtService {
    private static final String MOBILE = "mobile";
    private static final String secret = "abcdeB5WeYoceanjc9qbj78AaMdex888";
    // 60*60*24*7 一周的时间
    private final static int expiration = 604800;

    public String generateToken(String userId, String mobile) {
        return Jwts.builder()
                .setSubject(userId)
                .claim(MOBILE, mobile)
                .setExpiration(generateExpirationDate(expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Date generateExpirationDate(long seconds) {
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(seconds);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

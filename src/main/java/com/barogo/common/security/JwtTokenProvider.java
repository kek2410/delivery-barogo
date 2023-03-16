package com.barogo.common.security;

import com.barogo.api.user.dto.UserDTO;
import com.barogo.common.constant.Token;
import com.barogo.common.constant.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  // JWT 토큰 생성
  public String create(String userId, UserRole role) {
    long time = System.currentTimeMillis();
    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject(userId)
        .claim("userId", userId)
        .claim("role", role)
        .setIssuedAt(new Date(time))
        .setExpiration(new Date(time + Token.TOKEN_EXPIRED_AT))
        .signWith(SignatureAlgorithm.HS256, Token.SECRET_KEY.getBytes(StandardCharsets.UTF_8))
        .compact();
  }

  public Authentication getAuthentication(String token) {
    var claims = getClaims(token);
    var userDetails = UserDTO.builder()
        .userId(String.valueOf(claims.getOrDefault("id", "")))
        .roles(UserRole.parse(String.valueOf(claims.getOrDefault("role", ""))))
        .build();
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  // 토큰에서 회원 정보 추출
  public Claims getClaims(String token) {
    return Jwts.parser()
        .setSigningKey(Token.SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  public String resolve(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  public boolean validate(String jwtToken) {
    if (!StringUtils.hasText(jwtToken)) {
      return false;
    }
    try {
      return !Jwts.parser()
          .setSigningKey(Token.SECRET_KEY)
          .parseClaimsJws(jwtToken)
          .getBody()
          .getExpiration()
          .before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}
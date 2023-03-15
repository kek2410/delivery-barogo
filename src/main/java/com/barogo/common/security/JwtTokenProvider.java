package com.barogo.common.security;

import com.barogo.api.user.dto.UserDTO;
import com.barogo.common.constant.Token;
import com.barogo.common.constant.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  // JWT 토큰 생성
  public String create(String userId, UserRole roles) {
    return Jwts.builder()
        .setIssuer(Token.ISSUER)
        .setAudience(Token.AUDIENCE)
        .setClaims(Map.of("id", userId, "role", roles))
        .setSubject(userId)
        .setIssuedAt(new Date(Calendar.getInstance().getTimeInMillis()))
        .setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + (Token.TOKEN_EXPIRED_AT)))
        .signWith(SignatureAlgorithm.RS256, Token.SECRET_KEY)
        .compact();
  }

  // JWT 토큰에서 인증 정보 조회
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
    var header = request.getHeader("Authorization");
    if (!header.startsWith("Bearer ")) {
      return null;
    }
    return header.substring(7);
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
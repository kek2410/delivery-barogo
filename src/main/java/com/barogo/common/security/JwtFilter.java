package com.barogo.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    var header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (hasBearerToken(header)) {
      var bearerToken = getBearerToken(header);
      setAuthentication(bearerToken);
    }
    log.info("Test111111");
    filterChain.doFilter(request, response);
  }

  private void setAuthentication(String bearerToken) {
  }

  private boolean hasBearerToken(String header) {
    return header != null && header.startsWith("Bearer ");
  }

  private String getBearerToken(String header) {
    return header.substring(7);
  }
}

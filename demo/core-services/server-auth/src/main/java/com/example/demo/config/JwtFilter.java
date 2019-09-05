package com.example.demo.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  private final SecurityProperties securityProperties;

  public JwtFilter(JwtTokenProvider jwtTokenProvider, SecurityProperties securityProperties) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.securityProperties = securityProperties;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain) throws IOException, ServletException {
    String header = req.getHeader(securityProperties.getHeader());

    if (header != null && header.startsWith(securityProperties.getPrefix())) {
      String authToken = header.replace(securityProperties.getPrefix(), "");
      if (jwtTokenProvider.validateToken(authToken)) {
        Authentication auth = jwtTokenProvider.getAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
      } else {
        log.error("Authorized token has expired.");
      }
    } else {
      log.error("Invalid authorized token.");
    }

    chain.doFilter(req, res);
  }

}

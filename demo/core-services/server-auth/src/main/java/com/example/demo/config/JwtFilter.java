package com.example.demo.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

  private final SecurityProperties securityProperties;

  private final JwtTokenProvider jwtTokenProvider;

  public JwtFilter(SecurityProperties securityProperties, JwtTokenProvider jwtTokenProvider) {
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
        throw new RuntimeException("Authorized token has expired.");
      }
    } else {
      throw new RuntimeException("Invalid authorized token.");
    }

    chain.doFilter(req, res);
  }

}

package com.example.demo.config;

import java.io.IOException;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.demo.common.Utils;
import com.example.demo.dto.UserDetail;
import com.example.demo.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

  private final SecurityProperties securityProperties;

  public JwtTokenProvider(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }

  public String generateToken(String email, Users user) {
    Claims claims = Jwts.claims().setSubject(email);
    claims.put("profile", user);

    Date current = new Date();
    Date expirationTime = new Date(current.getTime() + securityProperties.getTime());

    String token =
        Jwts.builder().setClaims(claims).setIssuedAt(current).setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS256, securityProperties.getKey()).compact();

    return token;
  }

  public boolean validateToken(String token) {
    return new Date().before(readToken(token).getExpiration());
  }

  public String getUsernameFromToken(String token) {
    return readToken(token).getSubject();
  }

  private UserDetails getUserDetails(String token) throws IllegalArgumentException, IOException {
    String email = readToken(token).getSubject();
    Users profile = Utils.convertTo(new Users(), readToken(token).get("profile"), Users.class);
    return new UserDetail(email, new String[] {profile.getRole().getRole()});
  }

  public Authentication getAuthentication(String token) throws IllegalArgumentException, IOException {
    UserDetails userDetails = getUserDetails(token);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private Claims readToken(String token) {
    return Jwts.parser().setSigningKey(securityProperties.getKey()).parseClaimsJws(token).getBody();
  }

}

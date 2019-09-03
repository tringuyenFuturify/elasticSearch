package com.example.demo.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.demo.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken implements Serializable {

  private static final long serialVersionUID = -4160292099516778353L;

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public String doGenerateToken(Users user, List<SimpleGrantedAuthority> authorities) {
    Claims claims = Jwts.claims().setSubject(user.getEmail()());
    claims.put("scopes", authorities);
    claims.put("user", getPayload(user));

    return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + Const.TOKEN_TIME * 1000))
        .signWith(SignatureAlgorithm.HS256, Const.SIGNING_KEY).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(Const.SIGNING_KEY).parseClaimsJws(token).getBody();
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private TokenDto getPayload(Account u) {
    TokenDto res = new TokenDto();

    res.setUid(u.getUid());
    res.setName(u.getName());

    return res;
  }

}

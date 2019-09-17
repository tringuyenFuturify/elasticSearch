package io.futurify.authentication.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.futurify.authentication.dto.JwtResponse;
import io.futurify.authentication.dto.UserDetail;
import io.futurify.authentication.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private final SecurityProperties securityProperties;

	public JwtTokenProvider(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	public JwtResponse generateToken(String email, User user) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("user", user);
		claims.put("authorities", user.getPermissions().toArray(new String[user.getPermissions().size()]));

		Date current = new Date();
		Date expirationTime = new Date(current.getTime() + securityProperties.getTime());

		String token = Jwts.builder().setClaims(claims).setIssuedAt(current).setExpiration(expirationTime)
				.signWith(SignatureAlgorithm.HS256, securityProperties.getKey()).compact();

		return JwtResponse.builder().accessToken(token).expiredAt(expirationTime).build();
	}

	public boolean validateToken(String token) {
		return new Date().before(readToken(token).getExpiration());
	}

	public String getUsernameFromToken(String token) {
		return readToken(token).getSubject();
	}

	@SuppressWarnings("unchecked")
	public List<SimpleGrantedAuthority> getGrantedAuthority(String token) {
		List<String> authorities = readToken(token).get("authorities", List.class);
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	private UserDetails getUserDetails(String token) throws IllegalArgumentException, IOException {
		String email = readToken(token).getSubject();
		String[] permissions = (String[]) readToken(token).get("authorities");

		return new UserDetail(email, permissions);
	}

	public Authentication getAuthentication(String token) throws IllegalArgumentException, IOException {
		UserDetails userDetails = getUserDetails(token);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public Claims readToken(String token) {
		return Jwts.parser().setSigningKey(securityProperties.getKey()).parseClaimsJws(token).getBody();
	}

}

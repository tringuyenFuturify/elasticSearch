package io.futurify.zuul.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final SecurityProperties config;
	private final SecurityProperties securityProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String token = req.getHeader(config.getHeader());

		if (token != null && token.startsWith(config.getPrefix())) {
			token = token.replace(config.getPrefix(), "");
			try {
				String username = getUsernameFromToken(token);
				if (username != null && validateToken(token)) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
							getGrantedAuthority(token));
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					log.info("Json web token has expired or invalid.");
					SecurityContextHolder.clearContext();
					throw new Exception("Json web token has expired or invalid.");
				}
			} catch (Exception ex) {
				log.info("Ignore");
				SecurityContextHolder.clearContext();
			}
		}

		chain.doFilter(req, res);
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

	public Claims readToken(String token) {
		return Jwts.parser().setSigningKey(securityProperties.getKey()).parseClaimsJws(token).getBody();
	}
}

package io.futurify.authentication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.futurify.authentication.controller.exception.AuthorizeException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	private final SecurityProperties config;

	public JwtFilter(JwtTokenProvider jwtTokenProvider, SecurityProperties config) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.config = config;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String token = req.getHeader(config.getHeader());

		if (token != null && token.startsWith(config.getPrefix())) {
			token = token.replace(config.getPrefix(), "");
			try {
				String username = jwtTokenProvider.getUsernameFromToken(token);
				if (username != null && jwtTokenProvider.validateToken(token)) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
							jwtTokenProvider.getGrantedAuthority(token));
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					log.info("Json web token has expired or invalid.");
					SecurityContextHolder.clearContext();
					throw new AuthorizeException("Json web token has expired or invalid.");
				}
			} catch (Exception ex) {
				log.info("Ignore");
				SecurityContextHolder.clearContext();
			}
		}

		chain.doFilter(req, res);
	}

}

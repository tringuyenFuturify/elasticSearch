package io.futurify.zuul.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtFilter jwtFilter;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				// Handle authorized attempts
				.exceptionHandling().authenticationEntryPoint((req, rsp, ex) -> {
					rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				}).and()
				// Add filter to validate the token with every request
				.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				// Authorization requests config
				.authorizeRequests()
				// Allow endpoint login
				.antMatchers("/authserver/api/auth/login").permitAll()
				// Any other endoint must be authenticated
				.anyRequest().authenticated();
	}	
}

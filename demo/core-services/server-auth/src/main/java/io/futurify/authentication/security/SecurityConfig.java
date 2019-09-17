package io.futurify.authentication.security;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

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
				.antMatchers("/**/auth/**").permitAll()
				// Any other endoint must be authenticated
				.anyRequest().authenticated();
		// TODO [dungdang] add permission check on each endpoint of API
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/*.html", "/*.css", "/*.js");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH",
				"DELETE");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}

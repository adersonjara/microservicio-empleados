package com.app.msvc.empleados.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.msvc.empleados.security.jwt.AuthEntryPointJwt;
import com.app.msvc.empleados.security.jwt.AuthTokenFilter;
import com.app.msvc.empleados.security.services.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.cors()
			.and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().authorizeRequests()
			.antMatchers("/api/empleados/auth/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.and().authorizeRequests()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/h2-console/**").permitAll()
				
			.antMatchers("/v3/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.antMatchers("/swagger-ui.html").permitAll()
			//.antMatchers("/api/empleados").permitAll()
			.anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	//https://www.javachinna.com/configure-openapi-spec-basic-jwt-authentication/
	
	/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable()
        // .authorizeRequests()
        // .antMatchers(HttpMethod.GET, "/api/**")
        // .permitAll()
        // .anyRequest()
        // .authenticated()
        // .and()
        // .httpBasic();

        http.csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll()
        .antMatchers("/api/securityapp/auth/**").permitAll()
        .antMatchers(AUTH_WHITELIST).permitAll()
        .anyRequest()
        .authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
*/
    private static final String[] AUTH_WHITELIST = {
		"/api/securityapp/auth/**",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/v3/**",
        "/webjars/**",
        "/swagger-ui/**"
	};
}
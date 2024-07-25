package com.lcwd.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

	@Autowired
    private JwtAuthenticationEntryPoint point;
 
	@Autowired
    private JwtAuthenticationFilter filter;

	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    private final AuthenticationConfiguration configuration;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	http.csrf((csrf)->csrf.disable())
    		.authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests
    				.requestMatchers("/api/v1/auth/**").permitAll()
    				.requestMatchers("/v3/api-docs").permitAll()
//    				.requestMatchers("/api/v1/auth/register").permitAll()
    				.requestMatchers(HttpMethod.GET).permitAll()
    				.anyRequest().authenticated())
    		.exceptionHandling((ex)->ex.authenticationEntryPoint(point))
    		.sessionManagement((sessionManagement) ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//					.sessionConcurrency((sessionConcurrency) ->
//						sessionConcurrency
//							.maximumSessions(1)
//							.expiredUrl("/login?expired")))
    		;
// 					.hasRole("ADMIN")).build();
    	
    	
//        http.csrf(csrf -> csrf.disable())
//                .authorizeRequests().
//                requestMatchers("/test").authenticated().requestMatchers("/auth/login").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

	@Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }
	    
    @Autowired
    void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
  
    @Bean
    FilterRegistrationBean<CorsFilter> coresFilter() {
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	
    	CorsConfiguration corsConfiguration = new CorsConfiguration();
    	corsConfiguration.setAllowCredentials(true);
    	corsConfiguration.addAllowedOriginPattern("*");
    	corsConfiguration.addAllowedHeader("Authorization");
    	corsConfiguration.addAllowedHeader("Content-Type");
    	corsConfiguration.addAllowedHeader("Accept");
    	corsConfiguration.addAllowedMethod("POST");
    	corsConfiguration.addAllowedMethod("GET");
    	corsConfiguration.addAllowedMethod("DELETE");
    	corsConfiguration.addAllowedMethod("PUT");
    	corsConfiguration.addAllowedMethod("OPTIONS");
    	corsConfiguration.setMaxAge(3600L);

    	source.registerCorsConfiguration("/**",corsConfiguration);
    	FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    	bean.setOrder(-110);
    	return bean;
    }
    
}

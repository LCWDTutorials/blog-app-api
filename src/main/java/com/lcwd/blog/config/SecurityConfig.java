//package com.lcwd.blog.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.lcwd.blog.security.CustomUserDetailService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter {
//
//	@Autowired
//	private CustomUserDetailService customUserDetailService;
//	
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.userDetailsService(customUserDetailService).passwordEncoder(getPasswordEncoder());
//	}
//
//	@Bean
//	PasswordEncoder getPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}

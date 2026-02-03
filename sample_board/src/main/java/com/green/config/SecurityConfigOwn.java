package com.green.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigOwn {
	@Bean // IoC 스프링 컨테이너에 Bean 객체로 등록한다.
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 12223 => 암호화 => !#$%@#%af!$!@4 (대충 이런식)
	}
	
	//기본적으로 동작하는 기능을 해야하기에 모두 disable() 비활성화된다.
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http
	.cors(cors-> cors.disable())
	.csrf(csrf-> csrf.disable());
	http
	.formLogin(login-> login.disable());
	return http.build();
	}
}

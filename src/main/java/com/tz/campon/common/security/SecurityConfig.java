package com.tz.campon.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Security 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) ->
                        requests
                                // 누구나 접근 가능한 경로 설정
                                .requestMatchers("/", "/login", "/register", "/main", "/intro", "/detail", "/detail/3d").permitAll()
                                // 내 정보 수정, 예약 페이지, 예약 조회 등 인증된 사용자만 접근 가능
                                .requestMatchers("/mypage/edit", "/mypage/reservations", "/mypage/cancel", "/reserve").authenticated()
                                // 나머지 경로는 인증된 사용자만 접근 가능
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 로그인 페이지 URL 설정
                        .permitAll()  // 로그인 페이지는 누구나 접근 가능
                )
                .logout(logout -> logout
                        .permitAll()  // 로그아웃 페이지는 누구나 접근 가능
                );

        return http.build();
    }

    // PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt 방식의 PasswordEncoder를 사용
    }
}

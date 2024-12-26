package com.tz.campon.common.config;

import com.tz.campon.login.handler.CustomAuthFailureHandler;
import com.tz.campon.login.handler.CustomAuthSuccessHandler;
import com.tz.campon.login.handler.CustomLogoutFilter;
import com.tz.campon.login.handler.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setRequestCache(requestCache());
        successHandler.setDefaultTargetUrl("/main"); // 기본 이동 경로
        return successHandler;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler CustomAuthFailureHandler() {
        return new CustomAuthFailureHandler();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService UserService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(UserService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomLogoutFilter customLogoutFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 접근 가능한 URL
                        .requestMatchers(
                                "/",            // 인트로 화면
                                "/main",        // 메인 페이지
                                "/login",       // 로그인
                                "/register",    // 회원가입
                                "/callback",    // 소셜 로그인 콜백
                                "/css/**",      // 정적 리소스
                                "/js/**",
                                "/images/**",
                                "/videos/**",
                                "/check-duplicate", // 중복 확인
                                "/detail",      // 캠핑장 상세
                                "/detail/3d",   // 3D 화면
                                "/board",       // 게시판 메인
                                "/camplist",
                                "/campinfo/**",
                                "/campdetail/**",
                                "/check-login-status",
                                "/reviewAll/**"
                        ).permitAll()
                        // 로그인한 사용자만 접근 가능한 URL
                        .requestMatchers(
                                "/mypage/edit",         // 내 정보 수정
                                "/mypage/reservations", // 예약 조회
                                "/mypage/cancel",       // 예약 취소
                                "/reserve",             // 예약 페이지
                                "/board/add/**",           // 게시판 글 작성
                                "/board/edit/**",       // 게시판 글 수정
                                "/board/delete/**",     // 게시판 글 삭제
                                "/board/like/**",       // 게시판 좋아요
                                "/board/comment/**"     // 게시판 댓글 작성
                        ).authenticated()
                        .anyRequest().authenticated() // 기타 모든 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .loginProcessingUrl("/login")
                        .failureHandler(CustomAuthFailureHandler()) // 로그인 실패 처리
                        .successHandler(customAuthSuccessHandler()) // 이전 페이지로 이동 설정
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login?message=loginRequired");
                        })
                )
                .addFilterBefore(customLogoutFilter, LogoutFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler()) // 로그아웃 성공 시 이전 페이지로 이동
                        .invalidateHttpSession(false) // 세션 무효화
                        .clearAuthentication(true) // 인증 정보 삭제
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                )
                .anonymous(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 기존 세션 유지
                );
        return http.build();
    }
}


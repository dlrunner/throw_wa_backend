//package com.project.throw_wa.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.io.IOException;
//import java.util.List;
//
//@Configurable
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    /**
//     * Security Bypass 자원 등록
//     * - 정적파일
//     */
//    @Bean
//    protected WebSecurityCustomizer webSecurityCustomizer() {
//        return (web -> web.ignoring().requestMatchers("/oauth2/**", "/api/**", "/css/**", "/js/**", "/images/**", "/jsx/**"));
//    }
//
//    /**
//     * 자원별 인증정보 등록
//     * - permitAll()
//     * - anonymous() 비인증 사용자만 접근 (로그인, 회원가입)
//     * - authenticated() 인증된 사용자만 접근
//     * - hasRole()
//     * - hasAuthority()
//     */
//    @Bean
//    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(CsrfConfigurer::disable)
//                .httpBasic(HttpBasicConfigurer::disable)
//                // 세션을 사용하지 않는 것을 의미(STATELESS)
//                .sessionManagement(sessionManagement -> sessionManagement
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/**").permitAll()
//                        .anyRequest().permitAll()
//                );
////                .exceptionHandling(exceptionHandling -> exceptionHandling
////                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    protected CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(List.of("http://localhost:5173", "http://localhost:8000", "chrome-extension://**"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true); // allowCredentials 설정
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
//
//    // front단에서 넘어온 비밀번호가 back단으로 들어왔을 때 암호화 해주는 메소드
//    @Bean
//    BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//
////class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
////
////    @Override
////    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
////
////        response.setContentType("application/json");
////        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
////        // {"code": "NP", "message": "No Permission."}
////        response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission.\"}");
////    }
////}
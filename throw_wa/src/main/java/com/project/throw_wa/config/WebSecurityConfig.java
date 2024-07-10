package com.project.throw_wa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    OAuth2UserService oAuth2UserService;

    /**
     * Security Bypass 자원 등록
     * - 정적파일
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/api/**"));
    }

    /**
     * 자원별 인증정보 등록
     * - permitAll()
     * - anonymous() 비인증 사용자만 접근 (로그인, 회원가입)
     * - authenticated() 인증된 사용자만 접근
     * - hasRole()
     * - hasAuthority()
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((authorizeRequest -> {
            authorizeRequest
                    .requestMatchers("/", "/index.html").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/member/createMember.do").anonymous()
                    .anyRequest().permitAll();
        }));
        http.formLogin((formLoginConfigurer -> {
            formLoginConfigurer
                    .loginPage("/auth/login.do")
                    .loginProcessingUrl("/auth/login.do")
                    .permitAll();
        }));
        http.logout(logoutConfigurer -> {
            logoutConfigurer
                    .logoutUrl("/auth/logout.do")
                    .logoutSuccessUrl("/");
        });
        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer
                    .loginPage("/auth/login.do")
                    .userInfoEndpoint(userInfoEndpointConfig -> {
                        userInfoEndpointConfig.userService(oAuth2UserService);
                    });
        });
        return http.build();
    }

    // front단에서 넘어온 비밀번호가 back단으로 들어왔을 때 암호화 해주는 메소드
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

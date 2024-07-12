//package com.project.throw_wa.jwt.filter;
//
//import com.project.throw_wa.jwt.provider.JwtProvider;
//import com.project.throw_wa.member.entity.Member;
//import com.project.throw_wa.member.repository.MemberRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtProvider jwtProvider;
//    private final MemberRepository memberRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        try {
//            String token = parseBearerToken(request);
//            if (token == null || token.isEmpty()) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String email = jwtProvider.validate(token);
//            if (email == null) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            Member member = memberRepository.findByEmail(email);
//            if (member == null) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String authority = member.getAuthority().name();
//
//            // ROLE_DEVELOPER, ROLE_BOSS
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(authority));
//
//            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//
//            AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
//            authenticationToken.setDetails(new WebAuthenticationDetails(request));
//
//            securityContext.setAuthentication(authenticationToken);
//            SecurityContextHolder.setContext(securityContext);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String parseBearerToken(HttpServletRequest request) {
//
//        String authorization = request.getHeader("Authorization");
//
//        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
//            return authorization.substring(7);
//        }
//        return null;
//    }
//}

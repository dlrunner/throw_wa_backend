//package com.project.throw_wa.oauth.service;
//
//import com.project.throw_wa.auth.service.AuthService;
//import com.project.throw_wa.auth.vo.MemberDetails;
//import com.project.throw_wa.member.entity.Member;
//import com.project.throw_wa.member.service.MemberService;
//import com.project.throw_wa.oauth.utils.OAuth2UserUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//@Slf4j
//public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
//
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    AuthService authService;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        MemberDetails memberDetails = null;
//        // ClientRegistration : IDP
//        ClientRegistration clientRegistration = userRequest.getClientRegistration(); // google, kakao
//        OAuth2AccessToken accessToken = userRequest.getAccessToken(); // security가 session에 자동저장 후 관리
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        log.debug("clientRegistration = {}", clientRegistration);
//        log.debug("accessToken = {}", accessToken);
//        log.debug("oAuth2User = {}", oAuth2User);
//        log.debug("attributes = {}", attributes);
//
//        String provider = clientRegistration.getRegistrationId(); // google, kakao
//
//        String username = OAuth2UserUtils.getUsername(oAuth2User, provider);
//        try {
//            memberDetails = (MemberDetails) authService.loadUserByUsername(username);
//        } catch (UsernameNotFoundException e) {
//            // Name이 존재하지 않는 경우 회원가입처리
//            Member member = OAuth2UserUtils.of(oAuth2User, provider);
//            memberService.createMember(member);
//            memberDetails = (MemberDetails) authService.loadUserByUsername(username);
//        }
//        return memberDetails;
//    }
//}

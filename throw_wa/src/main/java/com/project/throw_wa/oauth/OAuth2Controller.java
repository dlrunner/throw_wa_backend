//package com.project.throw_wa.oauth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/")
//public class OAuth2Controller {
//
//    @Value("${kakao.client.id}")
//    String clientId;
//    @Value("${kakao.redirect.uri}")
//    String redirectUri;
//    @Value("${kakao.client.secret}")
//    String clientSecret;
//
//    /**
//     * 카카오 로그인 요청
//     *
//     * @return
//     */
//    @GetMapping(value = "oauth/kakao")
//    public String kakaoConnect() {
//        StringBuffer url = new StringBuffer();
//        url.append("https://kauth.kakao.com/oauth/authorize?");
//        url.append("client_id=" + clientId);
//        url.append("&redirect_uri=" + redirectUri);
//        url.append("&response_type=code");
//        return "redirect:" + url.toString();
//    }
//}

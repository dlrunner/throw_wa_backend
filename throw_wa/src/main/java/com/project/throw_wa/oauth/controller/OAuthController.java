//package com.project.throw_wa.oauth.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//public class OAuthController {
//
//    @Autowired
//    private KakaoApi kakaoApi;
//
//    @RequestMapping("/login/oauth2/code/kakao")
//    public String kakaoLogin(@RequestParam("code") String code) {
//        // 1. 인가 코드 받기
//        System.out.println(code);
//
//        // 2. 토큰 받기
//        String accessToken = kakaoApi.getAccessToken(code);
//
//        // 3. 사용자 정보 받기
//        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
//
//        String email = (String)userInfo.get("email");
//        String nickname = (String)userInfo.get("nickname");
//
//        System.out.println("email = " + email);
//        System.out.println("nickname = " + nickname);
//        System.out.println("accessToken = " + accessToken);
//
//        return "redirect:/result";
//    }
//}

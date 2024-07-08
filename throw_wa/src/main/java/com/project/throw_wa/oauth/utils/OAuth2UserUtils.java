//package com.project.throw_wa.oauth.utils;
//
//import com.project.throw_wa.member.entity.Member;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Map;
//
//public class OAuth2UserUtils {
//
//    public static Member of(OAuth2User oAuth2User, String provider) {
//        return switch (provider) {
//            case "google" -> google(oAuth2User);
//            case "kakao" -> kakao(oAuth2User);
//            default -> throw new AssertionError(provider);
//        };
//    }
//
//    private static Member google(OAuth2User oAuth2User) {
//        String username = oAuth2User.getAttribute("sub") + "@google";
//        String name = oAuth2User.getAttribute("name");
//        String email = oAuth2User.getAttribute("email");
//        return Member.builder()
//                .username(username)
//                .password("1234")
//                .name(name)
//                .email(email)
//                .build();
//    }
//
//    private static Member kakao(OAuth2User oAuth2User) {
//        String username = oAuth2User.getAttribute("sub") + "@kakao";
//        Map<String, Object> properties = oAuth2User.getAttribute("properties");
//        String name = (String) properties.get("nickname");
//        return Member.builder()
//                .username(username)
//                .password("1234")
//                .name(name)
//                .build();
//    }
//
//    public static String getUsername(OAuth2User oAuth2User, String provider) {
//        return switch (provider) {
//            case "google" -> oAuth2User.getAttribute("sub");
//            case "kakao" -> oAuth2User.getAttribute("name");
//            default -> throw new AssertionError(provider);
//        };
//    }
//}

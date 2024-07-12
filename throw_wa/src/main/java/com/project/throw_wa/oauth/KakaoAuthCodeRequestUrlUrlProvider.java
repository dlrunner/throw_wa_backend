package com.project.throw_wa.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeRequestUrlUrlProvider implements AuthCodeRequestUrlProvider {

    @Value("${KAKAO.CLIENT.ID}")
    String kakaoClientId;
    @Value("${KAKAO.CLIENT.SECRET}")
    String kakaoClientSecret;

    @Override
    public OAuthServerType supportServer() {
        return OAuthServerType.KAKAO;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", "http://localhost:5173/oauth2/callback/kakao")
                .queryParam("scope", "profile_nickname")
                .toUriString();

    }
}

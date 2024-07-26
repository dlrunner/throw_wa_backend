package com.project.throw_wa.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.throw_wa.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

//    private final UserRespository userRespository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = request.getClientRegistration(); // google, kakao
        OAuth2AccessToken accessToken = request.getAccessToken();

        log.info("Loading user {}", request);

        OAuth2User oAuth2User = super.loadUser(request);
        String oAuthClientName = request.getClientRegistration().getClientName();

        try {
            log.debug("oAuth2User.getAttributes = {}", new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        User user = null;
        String userId = null;

        if (oAuthClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            user = new User(userId, "email@email.com", "kakao");
        }

//        userRespository.save(user);

        return new CustomOAuth2User(userId);
    }
}

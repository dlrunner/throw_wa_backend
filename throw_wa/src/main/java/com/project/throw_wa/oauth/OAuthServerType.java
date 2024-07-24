package com.project.throw_wa.oauth;

import static java.util.Locale.ENGLISH;

public enum OAuthServerType {

    KAKAO;

    public static OAuthServerType fromName(String type) {
        return OAuthServerType.valueOf(type.toUpperCase(ENGLISH));
    }
}

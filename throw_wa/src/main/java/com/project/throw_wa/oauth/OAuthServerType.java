package com.project.throw_wa.oauth;

public enum OAuthServerType {

    KAKAO;

    public static OAuthServerType fromName(String type) {
        return OAuthServerType.valueOf(type.toUpperCase());
    }
}

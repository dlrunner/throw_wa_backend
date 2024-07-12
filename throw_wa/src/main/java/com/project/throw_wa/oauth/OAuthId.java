package com.project.throw_wa.oauth;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OAuthId {

    @Column(nullable = false, name = "oauth_server_id")
    private String oauthServerId;

    @Enumerated(STRING)
    @Column(nullable = false, name = "oauth_server")
    private OAuthServerType oauthServerType;

    public String oauthServerId() {
        return oauthServerId;
    }

    public OAuthServerType oauthServer() {
        return oauthServerType;
    }
}

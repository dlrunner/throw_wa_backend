package com.project.throw_wa.oauth;

public interface OAuthMemberClient {

    OAuthServerType supportServer();

    OAuthMember fetch(String code);
}

package com.project.throw_wa.oauth;

public interface AuthCodeRequestUrlProvider {

    OAuthServerType supportServer();

    String provide();
}

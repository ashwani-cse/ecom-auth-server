package com.management.user.security.authserver.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

/**
 * @author Ashwani Kumar
 * Created on 18/02/24.
 */

@SpringBootTest
public class JpaRegisteredClientRepositoryTest {

    @Autowired
    private JpaRegisteredClientRepository registeredClientRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testSave() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret("secret") // use bcrypt encoder before saving secret into db else during generate token it will throw error.
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                //.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
                //.postLogoutRedirectUri("http://127.0.0.1:8080/")
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .postLogoutRedirectUri("https://oauth.pstmn.io/v1/callback")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(oidcClient);
    }
}

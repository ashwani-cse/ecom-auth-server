package com.management.user.security.authserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Ashwani Kumar
 * Created on 18/02/24.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * This is the main security configuration for the Authorization Server.
	 * It configures the security filter chain that carries the OAuth 2.0
	 * Authorization Server.
	 * <p>
	 * The first security filter chain is for the Authorization Server itself.
	 * It is responsible for protecting the /oauth2/authorize and /oauth2/token
	 * endpoints, which are used by the client application to obtain access
	 * tokens and refresh tokens.
	 */
	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
			throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);   // Apply OAuth 2.0 security configuration to the Authorization Server
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)        // Configure the OAuth 2.0 Authorization Server
			.oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
		http
			// Redirect to the login page when not authenticated from the
			// authorization endpoint
			.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
			)

			.oauth2ResourceServer((resourceServer) -> resourceServer
				.jwt(Customizer.withDefaults()));

		return http.build();
	}

	/**
	 * This is the security filter chain for the Resource Server. It is
	 * responsible for protecting the resource endpoints, which are used by the
	 * client application to access the user's resources.
	 */
	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers("/users/signup").permitAll()
					.anyRequest().authenticated()
			)
				.csrf(AbstractHttpConfigurer::disable) // // disable CSRF because POST method will not work for permitAll
				//.csrf().disable()  // this is deprecated in spring security 5.0
				// Form login handles the redirect to the login page from the
			// authorization server filter chain
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	/**
	 * This is the user details service for the Authorization Server. It
	 * provides the user details for the user that is authenticated to the
	 * Authorization Server.
	 */
	/*@Bean
	public UserDetailsService userDetailsService() {
		//UserDetails userDetails = User.withDefaultPasswordEncoder()
		UserDetails userDetails = User.builder()
				.username("user")
				.password("$2a$16$7Vegtj8VMhaMTBzkCmibye8Iy9Tg9K4NjFL8XTFxj3OPAleUdmHyK")//password
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}
   */
	/**
	 * This is the registered client repository for the Authorization Server.
	 * It provides the client details for the client that is registered to the
	 * Authorization Server.
	 * <p>
	 * The client details are used to authenticate the client application.
	 *
	 * Note: This is the in-memory client repository. In our project, we are using JpaRegisteredClientRepository in security package.
	 * So we can comment it out or remove from this class.
	 */
	/*@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("oidc-client")
				.clientSecret("{noop}secret")
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

		return new InMemoryRegisteredClientRepository(oidcClient);
	}
*/
	/**
	 * This is the JWK source for the Authorization Server. It provides the JWK
	 * set that is used to verify the signature of the JWT access tokens and
	 * refresh tokens.
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	/**
	 * This is the JWT decoder for the Authorization Server. It decodes the JWT
	 * access tokens and refresh tokens.
	 */
	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/**
	 * This is the settings for the Authorization Server. It provides the
	 * settings for the Authorization Server.
	 */
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	/**
	 * This is the OAuth 2.0 token customizer for the Authorization Server.
	 * It is used to add custom claims to the JWT access tokens and refresh tokens.
	 */
		@Bean
		public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
			return (context) -> {
				if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
					context.getClaims().claims((claims) -> {
						Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
								.stream()
								.map(c -> c.replaceFirst("^ROLE_", ""))
								.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
						claims.put("roles", roles);
						//claims.put("claim-2", "value-2"); // ewe can hard-code authorities also
					});
				}
			};
		}


}
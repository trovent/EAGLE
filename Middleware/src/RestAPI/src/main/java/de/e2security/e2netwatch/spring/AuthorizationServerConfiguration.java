package de.e2security.e2netwatch.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Value("${signing_key}")
    private String signingKey;
    @Value("${client_id}")
    private String clientId;
    @Value("${client_secret}")
    private String clientSecret;
    @Value("${access_token_valid_hours}")
    private int accessTokenValidHours;
    @Value("${refresh_token_valid_hours}")
    private int refreshTokenValidHours;

    public AuthorizationServerConfiguration() {
        super();
    }

    // beans

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }

    // config

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
	        .withClient(clientId)
	        .secret(clientSecret)
	        .authorizedGrantTypes("password", "refresh_token")
	        .refreshTokenValiditySeconds(60 * 60 * refreshTokenValidHours)
	        .scopes("read", "write", "trust")     
	        .accessTokenValiditySeconds(60 * 60 * accessTokenValidHours)
	        ;
        logger.info("Client '" + clientId + "' registered to access token for " + accessTokenValidHours + "h" + 
	        ", refresh token for " + refreshTokenValidHours + "h");
        // @formatter:on
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
		// @formatter:off
		endpoints.
			prefix("/api").
			tokenStore(tokenStore()).
			authenticationManager(authenticationManager).
			userDetailsService(userDetailsService).
			allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).
			accessTokenConverter(accessTokenConverter());
			// @formatter:on
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        security
        	.checkTokenAccess("permitAll()")
        	.allowFormAuthenticationForClients();
        super.configure(security);
    }
    
}

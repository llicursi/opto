package com.llicursi.opto.config;

import com.llicursi.opto.auth.RoleTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");

    }*/

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource)
            .withClient("web")
            .secret("$2a$06$musOBasmxNW3tHA2sW2AVO9FVzzmjKc4/8Wcu8V5li1JCj2023vlG")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
            .scopes("read").and()
            .withClient("test")
            .secret("$2a$06$musOBasmxNW3tHA2sW2AVO9FVzzmjKc4/8Wcu8V5li1JCj2023vlG")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
            .scopes("read");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
            .tokenStore(tokenStore())
            .tokenEnhancer(tokenEnhancer())
            .authenticationManager(authenticationManager);
    }

    private TokenEnhancer tokenEnhancer() {
        return new RoleTokenEnhancer();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}

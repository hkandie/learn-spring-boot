package com.auth.client.hs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Slf4j
public class OAuth2ResourceServerSecurityConfiguration {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2ResourceServer().jwt();
        // @formatter:on
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder nimbusJwtDecoder= JwtDecoders.fromOidcIssuerLocation(jwkSetUri);
        OAuth2TokenValidator<Jwt> defaults= JwtValidators.createDefaultWithIssuer(jwkSetUri);
        DelegatingOAuth2TokenValidator<Jwt> validators=new DelegatingOAuth2TokenValidator<>(defaults);
        nimbusJwtDecoder.setJwtValidator(validators);
        return nimbusJwtDecoder;//NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

}

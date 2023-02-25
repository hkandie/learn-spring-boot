package com.auth.client.hs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration
public class OauthConfig {

    private int connectTimeout = 10000;

    private int readTimeout = 10000;

    @Value("${app.ds.base-url}")
    private String baseUrl;
    @Bean(name = "my-platform")
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrations, new WebSessionServerOAuth2AuthorizedClientRepository());
        oauth.setDefaultClientRegistrationId("my-platform");
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(oauth)
                .build();
    }

    @Bean
    ReactiveClientRegistrationRepository getRegistration(
            @Value("${app.oauth2.clientCredentials.tokenUri}") String tokenUri,
            @Value("${app.oauth2.clientCredentials.clientId}") String clientId,
            @Value("${app.oauth2.clientCredentials.clientSecret}") String clientSecret,
            @Value("${app.oauth2.clientCredentials.scopes}") String scope
    ) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("my-platform")
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope(scope)
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }
    private HttpClient getHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeout))
                        .addHandlerLast(new WriteTimeoutHandler(readTimeout)));
    }
}

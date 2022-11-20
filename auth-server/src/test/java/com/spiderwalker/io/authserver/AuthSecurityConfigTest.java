package com.spiderwalker.io.authserver;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.*;

class AuthSecurityConfigTest {
    @InjectMocks
    AuthSecurityConfig authSecurityConfig;
    @Mock
    HttpSecurity http;

    @Test
    void authorizationServerSecurityFilterChain() throws Exception {
    }

    @Test
    void defaultSecurityFilterChain() {
    }

    @Test
    void userDetailsService() {
    }

    @Test
    void registeredClientRepository() {
    }

    @Test
    void jwkSource() {
    }

    @Test
    void providerSettings() {
    }
}
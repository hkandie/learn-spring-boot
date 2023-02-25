package com.auth.client.hs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OAuth2ResourceServerControllerTest {
    @InjectMocks
    ProductController oAuth2ResourceServerController;

    @Test
    void index() {
        Map<String, Object> headers =new HashMap<>();
        headers.put("Authorization", "Bearer fhfhfh");

        Map<String, Object> claims =new HashMap<>();
        claims.put("sub", "sub");

        Jwt jwt = new Jwt("dhdhdhdhd", Instant.now(),Instant.now().plusSeconds(3600),headers,claims);
        ResponseEntity<String> response = oAuth2ResourceServerController.index(null, jwt);
        assertEquals("Hello, sub!",response.getBody());
    }

    @Test
    void message() {
    }

    @Test
    void createMessage() {
    }

    @Test
    void api() {
    }

    @Test
    void testApi() {
    }
}
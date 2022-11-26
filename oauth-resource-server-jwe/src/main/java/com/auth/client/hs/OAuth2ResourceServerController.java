package com.auth.client.hs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class OAuth2ResourceServerController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }

    @GetMapping("/message")
    @PreAuthorize("hasAuthority(\"SCOPE_message:read\")")
    public String message() {
        return "secret message";
    }

    @PostMapping("/message")
    @PreAuthorize("hasAuthority(\"SCOPE_message:write\")")
    public String createMessage(@RequestBody String message) {
        return String.format("Message was created. Content: %s", message);
    }

    @PostMapping("/api/message")
    @PreAuthorize("hasAuthority(\"SCOPE_api://message:write\")")
    public String api(@RequestBody LargeMessage message) {
        return String.format("Message was created. Content: %s", message);
    }

    @GetMapping(value = "/api/message", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority(\"SCOPE_api://message:read\")")
    public ResponseEntity<LargeMessage> api() {
        LargeMessage message = LargeMessage.builder()
                .id(UUID.randomUUID().toString())
                .message("This is a response")
                .build();
        return ResponseEntity.ok(message);
    }


}
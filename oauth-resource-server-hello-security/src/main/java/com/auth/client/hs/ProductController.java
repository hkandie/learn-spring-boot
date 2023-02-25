package com.auth.client.hs;

import com.auth.client.hs.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    GreetingService service;


    @GetMapping("/")
    public ResponseEntity<String> index(Authentication authentication, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.greet(jwt));
    }

    @GetMapping("/message")
    @PreAuthorize("hasAuthority(\"SCOPE_api://products:read\")")
    public ResponseEntity<List<Product>> message(Authentication authentication, @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(service.products());
    }

    @PostMapping("/message")
    @PreAuthorize("hasAuthority(\"SCOPE_api://message:write\")")
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
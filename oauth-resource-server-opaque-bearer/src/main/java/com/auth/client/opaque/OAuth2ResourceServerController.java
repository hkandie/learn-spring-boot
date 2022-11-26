package com.auth.client.opaque;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2ResourceServerController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return String.format("Hello, %s!", (String) principal.getAttribute("sub"));
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

}
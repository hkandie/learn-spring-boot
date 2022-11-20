package com.spiderwalker.io.authserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok("Hello, you have reached home");
    }
    @GetMapping("/error")
    public ResponseEntity error() {
        return ResponseEntity.ok("Hello, you have reached home");
    }
}

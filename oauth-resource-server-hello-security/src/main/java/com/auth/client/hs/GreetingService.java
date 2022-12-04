package com.auth.client.hs;

import com.auth.client.hs.products.Product;
import com.auth.client.hs.products.ProductList;
import com.auth.client.hs.products.ProductRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class GreetingService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OauthUtility oauthUtility;

    final AtomicInteger atomicInteger = new AtomicInteger(144019);

    public String greet(Jwt jwt) {

        return String.format("Hello, %s!", jwt.getSubject());
    }

    public List<Product> products() {
        Mono<List<Product>> future = oauthUtility.setCallable("/products");
        return future.block();
    }
}

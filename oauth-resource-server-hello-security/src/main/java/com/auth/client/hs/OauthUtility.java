package com.auth.client.hs;

import com.auth.client.hs.products.Product;
import com.auth.client.hs.products.ProductList;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class OauthUtility {
    @Autowired
    WebClient webClient;

    public Mono<List<Product>> setCallable(String uri) {
        return
                webClient.get()
                        .uri(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(httpStatus -> httpStatus.value() == 401,
                                error -> {
                                    log.info("Error:{}", error.headers());
                                    return Mono.error(new UnauthorizedException("error Body"));
                                })
                        .bodyToMono(new ParameterizedTypeReference<>() {
                        });

    }
}

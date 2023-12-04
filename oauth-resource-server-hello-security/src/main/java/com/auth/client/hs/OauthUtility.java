package com.auth.client.hs;

import com.auth.client.hs.products.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

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

package com.auth.client.hs;

import com.auth.client.hs.products.PersonDataGenerator;
import com.auth.client.hs.products.Product;
import com.auth.client.hs.products.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class GreetingService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PersonDataGenerator personDataGenerator;
    @Autowired
    OauthUtility oauthUtility;

    final AtomicInteger atomicInteger = new AtomicInteger(144019);

    public String greet(Jwt jwt) {

        return String.format("Hello, %s!", jwt.getSubject());
    }

    public List<Product> products() {
        List<Product> products = new ArrayList<>() ;

        return personDataGenerator.generatePersons(10);
    }
}

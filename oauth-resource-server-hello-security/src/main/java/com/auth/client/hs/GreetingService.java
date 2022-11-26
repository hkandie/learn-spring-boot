package com.auth.client.hs;

import com.auth.client.hs.products.Product;
import com.auth.client.hs.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingService {
    @Autowired
    ProductRepository productRepository;
    public String greet(Jwt jwt) {

        return String.format("Hello, %s!", jwt.getSubject());
    }

    public List<Product> products() {

        return productRepository.findAll();
    }
}

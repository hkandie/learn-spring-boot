package com.auth.client.hs.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductList {
    private List<Product> products;

    public ProductList() {
        products = new ArrayList<>();
    }
}

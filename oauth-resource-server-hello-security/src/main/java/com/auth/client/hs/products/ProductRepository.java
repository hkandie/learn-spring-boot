package com.auth.client.hs.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        String query = "SELECT * FROM public.products ORDER BY productcode ASC ";
        return jdbcTemplate.query(query, new ProductRowMapper());
    }
}

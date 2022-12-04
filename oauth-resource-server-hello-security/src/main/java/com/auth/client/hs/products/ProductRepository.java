package com.auth.client.hs.products;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.sql.Types;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Repository
@Slf4j
public class ProductRepository {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Product> findAll() {
        String query = "SELECT * FROM public.products ORDER BY productcode ASC ";
        return namedParameterJdbcTemplate.query(query, new ProductRowMapper());
    }

    public List<Product> findAll(int a) {
        String query = "SELECT * FROM public.products ORDER BY productcode ASC ";
        return namedParameterJdbcTemplate.query(query, new ProductRowMapper());
    }
}

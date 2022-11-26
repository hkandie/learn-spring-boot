package com.auth.client.hs.products;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {


        return Product.builder()
                .productCode(rs.getInt("productcode"))
                .productName(rs.getString("productname"))
                .unitCost(rs.getDouble("unitcost"))
                .retailPrice(rs.getDouble("prodrprice"))
                .taxRateCode(rs.getString("taxratecode"))
                .build();
    }
}

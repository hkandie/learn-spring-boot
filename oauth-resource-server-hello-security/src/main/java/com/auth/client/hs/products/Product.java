package com.auth.client.hs.products;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Product implements Serializable {

    private Integer productCode;
    private String productName;
    private Double unitCost;
    private Double retailPrice;
    private String taxRateCode;
}


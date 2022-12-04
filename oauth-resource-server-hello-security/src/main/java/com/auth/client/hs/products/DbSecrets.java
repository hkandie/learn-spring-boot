package com.auth.client.hs.products;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
public class DbSecrets {
    public String dbuser;
    public String password;
}

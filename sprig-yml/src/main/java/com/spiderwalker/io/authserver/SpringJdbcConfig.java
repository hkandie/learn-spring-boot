package com.spiderwalker.io.authserver;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.AWSSecretsManagerException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class SpringJdbcConfig {
    @Value("${app.secret.id}")
    private String secretId;
    @Value("${app.db.url}")
    private String dbUrl;
    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        AppSecrets appSecrets = makeAppSecrets();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(appSecrets.getUser());
        dataSource.setPassword(appSecrets.getPassword());
        log.info("Setting up database");
        return dataSource;
    }

    private AppSecrets makeAppSecrets() {
        try {
            AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                    .build();
            GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                    .withSecretId(secretId);
            GetSecretValueResult result = client.getSecretValue(getSecretValueRequest);


            AppSecrets appSecrets = objectMapper.readValue(result.getSecretString(), AppSecrets.class);
            return appSecrets;
        } catch (AWSSecretsManagerException | JsonProcessingException e) {
            log.error("Error", e);
        }
        return null;
    }
}

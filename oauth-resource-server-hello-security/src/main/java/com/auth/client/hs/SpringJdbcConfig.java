package com.auth.client.hs;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.auth.client.hs.products.DbSecrets;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class SpringJdbcConfig {
    @Value("${app.aws.secrets.dbSecretId}")
    private String dbSecretId;

    @Autowired
    AWSSecretsManager awsSecretsManager;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/taifa");
        DbSecrets s = getSecret();
        dataSource.setUsername(s.getDbuser());
        dataSource.setPassword(s.getPassword());
        log.info("Database is up");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        log.info("Setting up JdbcTemplate");
        return new JdbcTemplate(dataSource);
    }

    private DbSecrets getSecret() {
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(dbSecretId);


        try {
            GetSecretValueResult getSecretValueResult = awsSecretsManager.getSecretValue(getSecretValueRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            DbSecrets dbSecrets = objectMapper.readValue(getSecretValueResult.getSecretString(), DbSecrets.class);
            return dbSecrets;
        } catch (SdkClientException | JsonProcessingException e) {
            log.error("Error retrieving secret: {0}", e);
        }
        return null;
    }
}

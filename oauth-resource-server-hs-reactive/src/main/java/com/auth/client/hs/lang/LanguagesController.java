package com.auth.client.hs.lang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/languages")
@RestController
class LanguagesController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    WebClient webClient;
    Map<String, String> urls = Map.of(
            "Kotlin", "https://run.mocky.io/v3/6654273e-456d-40ce-9209-c879c93a844d",
            "Java", "https://run.mocky.io/v3/f04c90fc-7529-4f0b-a9d7-90e92105d5bf",
            "Go", "https://run.mocky.io/v3/2eece29e-29d8-4d59-aa81-9d47886f2e17",
            "Python", "https://run.mocky.io/v3/72bbad6e-2127-4f8a-a9c1-2fdf15b0c18c",
            "Rust", "https://run.mocky.io/v3/72bbad6e-2127-4f8a-a9c1-2fdf15b0c18c",
            "JavaScript", "https://run.mocky.io/v3/72bbad6e-2127-4f8a-a9c1-2fdf15b0c18c"
    );

    public record Language(String name, Integer year) {
        public Language withYear(Integer year) {
            return new Language(name(), year);
        }
    }

    public record LanguageYear(Integer year) {
    }

    @GetMapping
    public Flux<Language> getLanguages() {
        List<Language> languages = jdbcTemplate.query("select * from languages", new ResultSetExtractor<List<Language>>() {
            @Override
            public List<Language> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {

                List<Language> list = new ArrayList<Language>();
                while (rs.next()) {
                    list.add(new Language(rs.getString("name"), 0));
                }
                return list;
            }
        });

        return Flux.fromIterable(languages).flatMap(language -> {
            var languageYearResponse = webClient
                    .get()
                    .uri(urls.get(language.name).toString())
                    .retrieve()
                    .bodyToMono(LanguageYear.class);
           return languageYearResponse.map(response -> language.withYear(response.year));
        });

    }
}





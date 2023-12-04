package com.auth.client.hs;

import com.auth.client.hs.products.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthClientApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	DataSource dataSource;

	@MockBean
	JdbcTemplate jdbcTemplate;
	@MockBean
	private GreetingService service;

	@Test
	@WithMockUser()
	public void shouldReturnDefaultMessage() throws Exception {
		when(service.greet(any(Jwt.class))).thenReturn("Hello, Mock");
		this.mockMvc
				.perform(get("/").with(
						jwt().authorities(new SimpleGrantedAuthority("any"))))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, Mock")));
	}

	@Test
	public void shouldReturn401DefaultMessage() throws Exception {
		when(service.greet(any(Jwt.class))).thenReturn("Hello, Mock");
		var mockResult= this.mockMvc
				.perform(get("/")

				)
				.andDo(print())
				.andExpect(status().isUnauthorized());

	}

	@Test
	@WithMockUser
	public void shouldReturn403Message() throws Exception {
		when(service.greet(any(Jwt.class))).thenReturn("Hello, Mock");
		var mockResult= this.mockMvc
				.perform(get("/message")
				)
				.andDo(print())
				.andExpect(status().isForbidden());

	}

	@Test
	@WithMockUser
	public void shouldReturn200Message() throws Exception {
		List<Product> productList= List.of(Product.builder().build());
		when(service.products()).thenReturn(productList);
		this.mockMvc
				.perform(get("/message").with(
						jwt().authorities(new SimpleGrantedAuthority("SCOPE_api://products:read"))))
				.andExpect(status().isOk())
				.andExpect(content().json(convertObjectToJsonString(productList)));
	}
	private String convertObjectToJsonString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return  mapper.writeValueAsString(obj);
	}
}

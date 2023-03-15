package com.barogo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.barogo.common.config.SecurityConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ActiveProfiles("test")
@Import(SecurityConfig.class)
public abstract class AbstractControllerTest {

  @Autowired
  private WebApplicationContext applicationContext;

  protected MockMvc mvc;

  protected ObjectMapper objectMapper = new ObjectMapper();

  private final static JsonPathResultMatchers PATH = jsonPath("$.status.code");

  public final static MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;
  public final static ResultMatcher IS_OK = status().isOk();
  public final static ResultMatcher IS_CREATED = status().isCreated();
  public final static ResultMatcher IS_BAD_REQUEST = status().isBadRequest();
  public final static ResultMatcher IS_UNAUTHORIZED = status().isUnauthorized();
  public final static ResultMatcher IS_UNPROCESSABLE_ENTITY = status().isUnprocessableEntity();

  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .alwaysDo(print())
        .build();
  }

  protected final String json(Object value) throws JsonProcessingException {
    return objectMapper.writeValueAsString(value);
  }

  protected final ResultMatcher result(String value) {
    return content().json(value);
  }

  protected final MultiValueMap<String, String> convertParam(Object condition) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    Map<String, String> param = objectMapper.convertValue(condition, new TypeReference<>() {});
    map.setAll(param);
    return map;
  }
}
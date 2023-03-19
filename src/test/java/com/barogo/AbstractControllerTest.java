package com.barogo;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.barogo.common.config.SecurityConfig;
import com.barogo.common.security.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public abstract class AbstractControllerTest {

  protected static final String REST_PATH = "{class-name}/{method-name}";

  @Autowired
  private WebApplicationContext applicationContext;

  @MockBean
  protected JwtTokenProvider jwtTokenProvider;

  protected MockMvc mvc;

  protected ObjectMapper objectMapper = getObjectMapper();

  private final static JsonPathResultMatchers PATH = jsonPath("$.status.code");

  public final static MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;
  public final static ResultMatcher STATUS_IS_OK = status().isOk();
  public final static ResultMatcher STATUS_IS_CREATED = status().isCreated();
  public final static ResultMatcher STATUS_IS_BAD_REQUEST = status().isBadRequest();
  public final static ResultMatcher STATUS_IS_UNAUTHORIZED = status().isUnauthorized();
  public final static ResultMatcher STATUS_IS_UNPROCESSABLE_ENTITY = status().isUnprocessableEntity();

  @BeforeEach
  public void setUp(RestDocumentationContextProvider restDocumentation) {
//    documentationHandler = document(REST_PATH);
    mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
        .apply(documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withRequestDefaults(prettyPrint())
            .withResponseDefaults(prettyPrint())
            .and().snippets().withEncoding(StandardCharsets.UTF_8.displayName())
        )
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .alwaysDo(print())
        .build();
  }

  protected final String toJson(Object dto) throws JsonProcessingException {
    return objectMapper.writeValueAsString(dto);
  }

  protected final ResultMatcher resultJson(Object value) throws JsonProcessingException {
    return content().json(objectMapper.writeValueAsString(value));
  }


  protected final ResultMatcher result(String value) {
    return content().string(value);
  }

  protected final MultiValueMap<String, String> toMap(Object condition) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    Map<String, String> param = objectMapper.convertValue(condition, new TypeReference<>() {});
    map.setAll(param);
    return map;
  }

  private ObjectMapper getObjectMapper() {
    return Jackson2ObjectMapperBuilder
        .json()
        .featuresToEnable(SerializationFeature.INDENT_OUTPUT)
        .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .modules(new JavaTimeModule(), new Jdk8Module())
        .build();
  }
}
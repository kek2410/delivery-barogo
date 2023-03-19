package com.barogo.api.delivery.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.barogo.AbstractControllerTest;
import com.barogo.api.delivery.DeliveryDataInterface;
import com.barogo.api.delivery.service.DeliveryService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

@DisplayName("배송 컨트롤러 테스트")
@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest extends AbstractControllerTest implements DeliveryDataInterface {

  private static final String BASE_URL = "/deliveries";

  @MockBean
  DeliveryService deliveryService;

  @DisplayName("id로 조회")
  @Test
  void getById() throws Exception {
    // given
    var request = 1L;
    given(deliveryService.get(any(Long.class))).willReturn(deliveryResponse());
    // when
    var perform = mvc.perform(get(BASE_URL + "/{id}", request)
        .contentType(CONTENT_TYPE));
    // then
    perform.andExpect(STATUS_IS_OK)
        .andExpect(resultJson(deliveryResponse()));

    perform.andDo(
        document(REST_PATH,
            resource(
                ResourceSnippetParameters.builder()
                    .pathParameters(
                        parameterWithName("id").type(SimpleType.NUMBER).description("배송 ID")
                    )
                    .responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("배송 ID"),
                        fieldWithPath("status").type(JsonFieldType.STRING).description("배송 상태")
                    )
                    .build()
            )
        )
    );
  }

  @DisplayName("pickup 상태로 변경")
  @Test
  void setPickup() throws Exception {
    // given
    var request = 1L;
    // when
    var perform = mvc.perform(put(BASE_URL + "/{id}/pick-up", request)
        .contentType(CONTENT_TYPE));
    // then
    perform.andExpect(STATUS_IS_OK);

    perform.andDo(
        document(REST_PATH,
            resource(
                ResourceSnippetParameters.builder()
                    .pathParameters(
                        parameterWithName("id").type(SimpleType.NUMBER).description("배송 ID")
                    )
                    .build()
            )
        )
    );
  }

  @DisplayName("배송중 상태로 변경")
  @Test
  void setDelivering() throws Exception {
    // given
    var request = 1L;
    // when
    var perform = mvc.perform(put(BASE_URL + "/{id}/delivering", request)
        .contentType(CONTENT_TYPE));
    // then
    perform.andExpect(STATUS_IS_OK);

    perform.andDo(
        document(REST_PATH,
            resource(
                ResourceSnippetParameters.builder()
                    .pathParameters(
                        parameterWithName("id").type(SimpleType.NUMBER).description("배송 ID")
                    )
                    .build()
            )
        )
    );
  }

  @DisplayName("배송완료 상태로 변경")
  @Test
  void setDelivered() throws Exception {
    // given
    var request = 1L;
    // when
    var perform = mvc.perform(put(BASE_URL + "/{id}/delivered", request)
        .contentType(CONTENT_TYPE));
    // then
    perform.andExpect(STATUS_IS_OK);

    perform.andDo(
        document(REST_PATH,
            resource(
                ResourceSnippetParameters.builder()
                    .pathParameters(
                        parameterWithName("id").type(SimpleType.NUMBER).description("배송 ID")
                    )
                    .build()
            )
        )
    );
  }

}
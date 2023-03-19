package com.barogo.api.order.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.barogo.AbstractControllerTest;
import com.barogo.api.order.OrderDataInterface;
import com.barogo.api.order.dto.OrderSaveRequest;
import com.barogo.api.order.dto.OrderSearchRequest;
import com.barogo.api.order.service.OrderService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("주문 컨트롤러 테스트")
@WebMvcTest(OrderController.class)
@WithMockUser(value = "customer", authorities = "CUSTOMER")
class OrderControllerTest extends AbstractControllerTest implements OrderDataInterface {

  private static final String BASE_URL = "/orders";

  @MockBean
  OrderService orderService;

  @DisplayName("id로 조회")
  @Test
  void getById() throws Exception {
    // given
    var request = 1L;
    given(orderService.get(any(Long.class))).willReturn(getOrderResponse());
    // when
    var perform = mvc.perform(get(BASE_URL + "/{id}", request)
        .contentType(CONTENT_TYPE));
    // then
    perform.andExpect(STATUS_IS_OK)
        .andExpect(resultJson(getOrderResponse()));

    perform.andDo(
        document(REST_PATH,
            resource(
                ResourceSnippetParameters.builder()
                    .pathParameters(
                        parameterWithName("id").type(SimpleType.NUMBER).description("주문 ID")
                    )
                    .responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("주문 ID"),
                        fieldWithPath("status").type(JsonFieldType.STRING).description("주문 상태"),
                        fieldWithPath("address").type(JsonFieldType.STRING).description("배송 주소"),
                        fieldWithPath("subAddress").type(JsonFieldType.STRING).description("배송 주소 추가정보"),
                        fieldWithPath("zipCode").type(JsonFieldType.STRING).description("우편번호")
                    )
                    .build()
            )
        )
    );
  }

  @DisplayName("주문 저장")
  @Test
  void saveOrders() throws Exception {
    // given
    given(orderService.save(any(OrderSaveRequest.class))).willReturn(1L);
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .content(toJson(saveOrderRequest())));
    // then
    perform.andExpect(STATUS_IS_CREATED)
        .andExpect(resultJson(1L));

    perform.andDo(
        document(REST_PATH,
            resource(
                ResourceSnippetParameters.builder()
                    .requestFields(
                        fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                        fieldWithPath("subAddress").type(JsonFieldType.STRING).description("주소 추가정보"),
                        fieldWithPath("zipCode").type(JsonFieldType.STRING).description("우편번호")
                    )
                    .build()
            )
        )
    );
  }


  @DisplayName("주문조회")
  @Test
  void getListAll() throws Exception {
    // given
    var request = OrderSearchRequest.builder()
        .fromDateTime(LocalDateTime.now().minusDays(3L)).toDateTime(LocalDateTime.now())
        .build();
    given(orderService.list(any(OrderSearchRequest.class))).willReturn(List.of(getOrderResponse()));
    // when
    var perform = mvc.perform(get(BASE_URL)
        .contentType(CONTENT_TYPE)
        .queryParams(toMap(request)));
    // then
    perform.andExpect(STATUS_IS_OK);

    perform.andDo(
        document(REST_PATH,
            resource(
                ResourceSnippetParameters.builder()
                    .responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("주문 ID"),
                        fieldWithPath("[].status").type(JsonFieldType.STRING).description("주문 상태"),
                        fieldWithPath("[].address").type(JsonFieldType.STRING).description("배송 주소"),
                        fieldWithPath("[].subAddress").type(JsonFieldType.STRING).description("배송 주소 추가정보"),
                        fieldWithPath("[].zipCode").type(JsonFieldType.STRING).description("우편번호")
                    )
                    .build()
            )
        )
    );
  }

  @DisplayName("주문 저장 실패")
  @Test
  void failToSaveOrders() throws Exception {
    // given
    given(orderService.save(any(OrderSaveRequest.class))).willReturn(1L);
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .content(toJson(OrderSaveRequest.builder().build())));
    // then
    perform.andExpect(STATUS_IS_BAD_REQUEST);
  }

  @DisplayName("주문 수정")
  @Test
  void updateOrder() throws Exception {
    // given
    var request = saveOrderRequest();
    // when
    var perform = mvc.perform(put(BASE_URL + "/{id}", 1L)
        .contentType(CONTENT_TYPE)
        .content(toJson(request)));
    // the
    perform.andExpect(STATUS_IS_OK);
  }

  @DisplayName("배송준비 목록 조회")
  @Test
  @WithMockUser(value = "deliver", authorities = "DELIVER")
  void deliveryReadyList() throws Exception {
    // given
    given(orderService.deliveryReadyList()).willReturn(List.of(getOrderResponse()));
    // when
    var perform = mvc.perform(get(BASE_URL + "/delivery-ready")
        .contentType(CONTENT_TYPE));
    // then
    perform.andExpect(STATUS_IS_OK);
  }

}
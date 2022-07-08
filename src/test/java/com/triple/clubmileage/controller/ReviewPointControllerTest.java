package com.triple.clubmileage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.clubmileage.common.enums.PointType;
import com.triple.clubmileage.common.enums.review.ReviewType;
import com.triple.clubmileage.dto.RequestDto;
import com.triple.clubmileage.dto.UserPointResponseDto;
import com.triple.clubmileage.service.ReviewPointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest(ReviewPointController.class)
@ExtendWith(RestDocumentationExtension.class)
class ReviewPointControllerTest {

    @MockBean
    private ReviewPointService reviewPointService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("리뷰 등록/수정/삭제 : 성공")
    void addPoint() throws Exception {

        RequestDto.Review request = RequestDto.Review.builder()
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        mvc.perform(post("/events")
                .contentType((MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value(200))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(print())
                .andDo(
                        document("post-events",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                                ),
                                requestFields(
                                        fieldWithPath("type").type(JsonFieldType.STRING).description("포인트 타입").optional(),
                                        fieldWithPath("action").type(JsonFieldType.STRING).description("리뷰 생성 이벤트").optional().attributes(key("constraints").value("ADD|MOD|DELETE")),
                                        fieldWithPath("reviewId").type(JsonFieldType.STRING).description("UUID 포맷의 리뷰 아이디").optional(),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("리뷰의 내용"),
                                        fieldWithPath("attachedPhotoIds").type(JsonFieldType.ARRAY).description("리뷰에 첨부된 이미지들의 아이디"),
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("리뷰의 작성자 아이디").optional(),
                                        fieldWithPath("placeId").type(JsonFieldType.STRING).description("리뷰가 작성된 장소의 아이디").optional()
                                ),
                                responseHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                                )));
    }

    @Test
    @DisplayName("클라이언트 포인트 조회 : 성공")
    void searchPoint() throws Exception {

        UserPointResponseDto.UserPointDto result = UserPointResponseDto.UserPointDto.builder()
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .accumulatedPoint(0L)
                .build();

        given(reviewPointService.searchUserPoint(any())).willReturn(result);

        mvc.perform(RestDocumentationRequestBuilders
                .get("/events/{userId}/point", "userId", "3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .contentType((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value(200))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("$..result[?(@.userId == '%s')]", "3ede0ef2-92b7-4817-a5f3-0c575361f745").exists())
                .andExpect(jsonPath("$..result[?(@.point == '%s')]", "0").exists())
                .andDo(print())
                .andDo(document("get-user-point",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        pathParameters(
                                parameterWithName("userId").description("유저 아이디")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"))
                                .andWithPrefix("result.", fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디"))
                                .andWithPrefix("result.", fieldWithPath("point").type(JsonFieldType.NUMBER).description("유저 누적 포인트"))));

    }


}

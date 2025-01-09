package kr.hhplus.be.server.api.concert.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.*
import java.util.*

class ScheduleControllerTest : RestDocsTestSupport() {
    private lateinit var scheduleController: ScheduleController

    @BeforeEach
    fun setup() {
        scheduleController = ScheduleController()
        mockMvc = mockController(scheduleController)
    }

    @Test
    fun `예약 가능 날짜 목록 조회 API`() {
        given()
            .queryParams("status", "AVAILABLE")
            .contentType(ContentType.JSON)
            .get("/api/v1/concerts/{concertId}/schedules", UUID.randomUUID().toString())
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "예약 가능 날짜 목록 조회",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    pathParameters(
                        parameterWithName("concertId").description("콘서트 ID"),
                    ),
                    queryParameters(
                        parameterWithName("status").description("상태"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data[]").type(ARRAY).description("결과 데이터"),
                        fieldWithPath("data[].concertScheduleId").type(STRING).description("콘서트 스케줄 ID"),
                        fieldWithPath("data[].date").type(STRING).description("날짜"),
                    ),
                ),
            )
    }
}
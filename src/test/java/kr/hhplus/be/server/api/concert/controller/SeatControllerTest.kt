package kr.hhplus.be.server.api.concert.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters

class SeatControllerTest : RestDocsTestSupport() {
    private lateinit var seatController: SeatController

    @BeforeEach
    fun setup() {
        seatController = SeatController()
        mockMvc = mockController(seatController)
    }

    @Test
    fun `예약 가능 좌석 목록 조회 API`() {
        val concertId = TsidCreator.getTsid().toString()
        val concertScheduleId = TsidCreator.getTsid().toString()

        given()
            .contentType(ContentType.JSON)
            .get(
                "/api/v1/concerts/{concertId}/schedules/{concertScheduleId}/seats/available",
                concertId,
                concertScheduleId,
            )
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "예약 가능 좌석 목록 조회",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    pathParameters(
                        parameterWithName("concertId").description("콘서트 ID"),
                        parameterWithName("concertScheduleId").description("콘서트 스케줄 ID"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data[]").type(ARRAY).description("결과 데이터"),
                        fieldWithPath("data[].seatId").type(STRING).description("좌석 ID"),
                        fieldWithPath("data[].number").type(NUMBER).description("좌석 번호"),
                    ),
                ),
            )
    }
}
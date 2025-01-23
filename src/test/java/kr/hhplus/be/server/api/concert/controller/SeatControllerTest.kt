package kr.hhplus.be.server.api.concert.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.concert.application.SeatFacade
import kr.hhplus.be.server.api.concert.application.dto.GetAvailableSeatResult
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

@DisplayName("좌석 API 문서 테스트")
class SeatControllerTest : RestDocsTestSupport() {
    private lateinit var seatController: SeatController
    private lateinit var seatFacade: SeatFacade

    @BeforeEach
    fun setup() {
        seatFacade = mockk()
        seatController = SeatController(seatFacade)
        mockMvc = mockController(seatController)
    }

    @Test
    fun `예약 가능 좌석 목록 조회 API`() {

        val concertId = TsidCreator.getTsid().toString()
        val concertScheduleId = TsidCreator.getTsid().toString()

        val result1 = GetAvailableSeatResult(
            id = TsidCreator.getTsid().toString(),
            number = 1,
        )

        val result2 = GetAvailableSeatResult(
            id = TsidCreator.getTsid().toString(),
            number = 2,
        )

        every { seatFacade.getAvailableSeats(any()) }
            .returns(listOf(result1, result2))

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
                    ResourceSnippetParametersBuilder()
                        .tag("콘서트")
                        .summary("예약 가능 좌석 목록 조회")
                        .description("""
                            예약 가능한 좌석 목록을 조회합니다.
                            예약이 아직 이루어지지 않은 좌석을 예약 가능한 상태로 판단합니다.
                        """.trimIndent()
                        )
                        .pathParameters(
                            parameterWithName("concertId").description("콘서트 ID"),
                            parameterWithName("concertScheduleId").description("콘서트 스케줄 ID"),
                        )
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data[]").type(ARRAY).description("결과 데이터"),
                            fieldWithPath("data[].id").type(STRING).description("좌석 ID"),
                            fieldWithPath("data[].number").type(NUMBER).description("좌석 번호"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }
}
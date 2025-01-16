package kr.hhplus.be.server.api.concert.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.domain.concert.ConcertScheduleService
import kr.hhplus.be.server.domain.concert.fixture.ConcertScheduleFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import java.util.*

class ConcertConcertEntityScheduleEntityControllerTest : RestDocsTestSupport() {
    private lateinit var concertScheduleService: ConcertScheduleService
    private lateinit var scheduleController: ConcertScheduleController

    @BeforeEach
    fun setup() {
        concertScheduleService = mockk()
        scheduleController = ConcertScheduleController(concertScheduleService)
        mockMvc = mockController(scheduleController)
    }

    @Test
    fun `예약 가능 날짜 목록 조회 API`() {
        val data = listOf(
            ConcertScheduleFixture.createConcertSchedule(),
            ConcertScheduleFixture.createConcertSchedule()
        )

        every { concertScheduleService.getAvailableConcertSchedules(any()) }
            .returns(data)

        given()
            .contentType(ContentType.JSON)
            .get("/api/v1/concerts/{concertId}/schedules/available", UUID.randomUUID().toString())
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "예약 가능 콘서트 일정 목록 조회",
                    ResourceSnippetParametersBuilder()
                        .tag("콘서트")
                        .summary("예약 가능 콘서트 일정 목록 조회")
                        .description("""
                            예약 가능한 콘서트 일정 목록을 조회합니다.
                            콘서트 날짜가 조회 시점보다 이후라면 예약 가능 콘서트 일정으로 판단합니다.
                        """.trimIndent())
                        .pathParameters(
                            parameterWithName("concertId").description("콘서트 ID"),
                        )
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data[]").type(ARRAY).description("결과 데이터"),
                            fieldWithPath("data[].id").type(STRING).description("콘서트 일정 ID"),
                            fieldWithPath("data[].date").type(STRING).description("날짜"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }
}
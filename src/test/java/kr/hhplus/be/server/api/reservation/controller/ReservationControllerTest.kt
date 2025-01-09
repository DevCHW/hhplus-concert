package kr.hhplus.be.server.api.reservation.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.reservation.application.ReservationFacade
import kr.hhplus.be.server.api.reservation.application.dto.CreateReservationResult
import kr.hhplus.be.server.api.reservation.controller.dto.request.CreateReservationRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import java.time.LocalDateTime

class ReservationControllerTest : RestDocsTestSupport() {
    private lateinit var reservationFacade: ReservationFacade
    private lateinit var reservationController: ReservationController

    @BeforeEach
    fun setup() {
        reservationFacade = mockk()
        reservationController = ReservationController(reservationFacade)
        mockMvc = mockController(reservationController)
    }

    @Test
    fun `예약 API`() {
        val request = CreateReservationRequest(
            concertId = TsidCreator.getTsid().toString(),
            seatId = TsidCreator.getTsid().toString(),
            userId = TsidCreator.getTsid().toString(),
        )

        val result = CreateReservationResult(
            id = TsidCreator.getTsid().toString(),
            userId = request.userId,
            seatId = request.seatId,
            status = "PENDING",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        every { reservationFacade.createReservation(any(), any(), any()) }
            .returns(result)

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/v1/reservations")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "예약",
                    ResourceSnippetParametersBuilder()
                        .tag("예약")
                        .summary("예약 생성")
                        .description("""
                            예약에 필요한 정보를 입력받아 예약을 생성합니다.
                            예약 성공 이후 약 5분간 결제가 이루어지지 않을 경우 자동 취소처리됩니다.
                        """.trimIndent())
                        .requestFields(
                            fieldWithPath("concertId").type(STRING).description("콘서트 일정 ID"),
                            fieldWithPath("userId").type(STRING).description("유저 ID"),
                            fieldWithPath("seatId").type(STRING).description("좌석 ID"),
                        )
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                            fieldWithPath("data.reservationId").type(STRING).description("예약 ID"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }

}


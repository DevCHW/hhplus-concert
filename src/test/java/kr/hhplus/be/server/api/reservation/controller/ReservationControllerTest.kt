package kr.hhplus.be.server.api.reservation.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.reservation.controller.dto.request.CreateReservationRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.*
import java.util.*

class ReservationControllerTest : RestDocsTestSupport() {
    private lateinit var reservationController: ReservationController

    @BeforeEach
    fun setup() {
        reservationController = ReservationController()
        mockMvc = mockController(reservationController)
    }

    @Test
    fun `예약 API`() {
        val request = CreateReservationRequest(
            concertScheduleId = UUID.randomUUID().toString(),
            seatId = UUID.randomUUID().toString(),
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/v1/reservations")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "예약",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("concertScheduleId").type(STRING).description("콘서트 일정 ID"),
                        fieldWithPath("seatId").type(STRING).description("좌석 ID"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                        fieldWithPath("data.reservationId").type(STRING).description("예약 ID"),
                    ),
                ),
            )
    }

}


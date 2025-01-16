package kr.hhplus.be.server.api.payment.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.payment.application.PaymentFacade
import kr.hhplus.be.server.api.payment.application.dto.CreatePaymentResult
import kr.hhplus.be.server.api.payment.controller.dto.request.CreatePaymentRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import java.math.BigDecimal
import java.util.*

class PaymentEntityControllerTest : RestDocsTestSupport() {
    private lateinit var paymentController: PaymentController
    private lateinit var paymentFacade: PaymentFacade

    @BeforeEach
    fun setup() {
        paymentFacade = mockk()
        paymentController = PaymentController(paymentFacade)
        mockMvc = mockController(paymentController)
    }

    @Test
    fun `결제`() {
        val request = CreatePaymentRequest(
            reservationId = TsidCreator.getTsid().toString(),
            token = UUID.randomUUID(),
        )

        val result = CreatePaymentResult(
            paymentId = TsidCreator.getTsid().toString(),
            reservationId = request.reservationId,
            amount = BigDecimal.ZERO,
        )

        every { paymentFacade.createPayment(any(), any()) } returns result

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/v1/payment")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "결제",
                    ResourceSnippetParametersBuilder()
                        .tag("결제")
                        .summary("결제 생성")
                        .description("""
                            결제에 필요한 정보를 받아 사용자의 잔액을 차감하고 결제를 생성합니다.
                            결제 완료 시 대기열 토큰을 삭제 처리합니다.
                        """.trimIndent())
                        .requestFields(
                            fieldWithPath("reservationId").type(STRING).description("예약 ID"),
                            fieldWithPath("token").type(STRING).description("토큰"),
                        )
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                            fieldWithPath("data.id").type(STRING).description("결제 ID"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }

}
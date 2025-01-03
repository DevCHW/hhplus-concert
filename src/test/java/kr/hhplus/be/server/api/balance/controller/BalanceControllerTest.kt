package kr.hhplus.be.server.api.balance.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.balance.controller.dto.request.ChargeRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import java.math.BigDecimal
import java.util.UUID

class BalanceControllerTest : RestDocsTestSupport() {
    private lateinit var balanceController: BalanceController

    @BeforeEach
    fun setup() {
        balanceController = BalanceController()
        mockMvc = mockController(balanceController)
    }

    @Test
    fun `잔고 충전 API`() {
        val request = ChargeRequest(
            userId = UUID.randomUUID().toString(),
            amount = BigDecimal.valueOf(100),
        )
        given()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/api/v1/balance/charge")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "잔고 충전",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("userId").type(STRING).description("유저 ID"),
                        fieldWithPath("amount").type(NUMBER).description("충전 금액"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                        fieldWithPath("data.balance").type(NUMBER).description("잔고"),
                    ),
                ),
            )
    }

    @Test
    fun `잔고 조회 API`() {
        given()
            .queryParam("userId", UUID.randomUUID().toString())
            .contentType(ContentType.JSON)
            .get("/api/v1/balance")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "잔고 조회",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    queryParameters(
                        parameterWithName("userId").description("유저 ID"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                        fieldWithPath("data.balance").type(NUMBER).description("잔고"),
                    ),
                ),
            )
    }
}
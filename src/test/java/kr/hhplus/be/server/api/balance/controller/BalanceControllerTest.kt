package kr.hhplus.be.server.api.balance.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.balance.controller.dto.request.ChargeRequest
import kr.hhplus.be.server.api.balance.controller.dto.response.ChargeResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import java.math.BigDecimal
import java.util.*


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
            userId = TsidCreator.getTsid().toString(),
            amount = BigDecimal.valueOf(100),
        )

        val response = ChargeResponse(
            balance = BigDecimal.valueOf(100L),
        )
        given()
            .body(request)
            .contentType(ContentType.JSON)
            .put("/api/v1/balance/charge")
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
                    ResourceSnippetParametersBuilder()
                        .tag("태그")
                        .description("설명")
                        .queryParameters(
                            parameterWithName("userId").description("유저 ID"),
                        )
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                            fieldWithPath("data.balance").type(NUMBER).description("잔고"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }
}
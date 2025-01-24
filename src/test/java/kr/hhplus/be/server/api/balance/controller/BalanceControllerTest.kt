package kr.hhplus.be.server.api.balance.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.balance.application.BalanceFacade
import kr.hhplus.be.server.api.balance.application.dto.ChargeBalanceResult
import kr.hhplus.be.server.api.balance.application.dto.GetBalanceResult
import kr.hhplus.be.server.api.balance.controller.dto.request.ChargeRequest
import kr.hhplus.be.server.domain.balance.fixture.BalanceFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import java.math.BigDecimal

@DisplayName("잔고 API 문서 테스트")
class BalanceControllerTest : RestDocsTestSupport() {
    private lateinit var balanceController: BalanceController
    private lateinit var balanceFacade: BalanceFacade

    @BeforeEach
    fun setup() {
        balanceFacade = mockk()
        balanceController = BalanceController(balanceFacade)
        mockMvc = mockController(balanceController)
    }

    @Test
    fun `잔고 충전 API`() {
        val request = ChargeRequest(
            userId = TsidCreator.getTsid().toString(),
            amount = BigDecimal.valueOf(100),
        )

        val chargeResult =
            ChargeBalanceResult.from(BalanceFixture.get(userId = request.userId, balance = request.amount))
        every { balanceFacade.charge(any(), any()) }
            .returns(chargeResult)

        given()
            .body(request)
            .contentType(ContentType.JSON)
            .put("/api/v1/balance/charge")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "잔고 충전",
                    ResourceSnippetParametersBuilder()
                        .tag("잔고")
                        .summary("잔고 충전")
                        .description("사용자 ID를 입력받아 잔고를 충전합니다.")
                        .requestFields(
                            fieldWithPath("userId").type(STRING).description("유저 ID"),
                            fieldWithPath("amount").type(NUMBER).description("충전 금액"),
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

    @Test
    fun `잔고 조회 API`() {
        val userId = TsidCreator.getTsid().toString()
        val balance = BalanceFixture.get(userId = userId)
        val getBalanceResult = GetBalanceResult.from(balance)
        every { balanceFacade.getBalance(any()) }
            .returns(getBalanceResult)

        given()
            .queryParam("userId", userId)
            .contentType(ContentType.JSON)
            .get("/api/v1/balance")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "잔고 조회",
                    ResourceSnippetParametersBuilder()
                        .tag("잔고")
                        .summary("잔고 조회")
                        .description("사용자 ID를 입력받아 잔고를 조회합니다.")
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
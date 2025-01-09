package kr.hhplus.be.server.api.token.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.token.controller.dto.enums.TokenStatus
import kr.hhplus.be.server.api.token.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.api.token.controller.dto.response.GetTokenResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import java.util.*

class TokenControllerTest : RestDocsTestSupport() {

    private lateinit var queueController: TokenController

    @BeforeEach
    fun setup() {
        queueController = TokenController()
        mockMvc = mockController(queueController)
    }

    @Test
    fun `토큰 생성 API`() {
        val request = CreateTokenRequest(
            userId = UUID.randomUUID().toString(),
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/v1/token")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "대기열 토큰 생성",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("userId").type(STRING).description("유저 ID"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                        fieldWithPath("data.id").type(STRING).description("토큰 ID"),
                        fieldWithPath("data.token").type(STRING).description("토큰 값"),
                        fieldWithPath("data.status").type(STRING).description("토큰 상태"),
                    ),
                ),
            )
    }

    @Test
    fun `토큰 상태 조회 API`() {
        val token = UUID.randomUUID()
        val request = GetTokenResponse(
            id = TsidCreator.getTsid().toString(),
            token = token,
            position = 100,
            status = TokenStatus.ACTIVE,
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .get("/api/v1/token/{token}", token.toString())
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "토큰 상태 조회",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                        fieldWithPath("data.id").type(STRING).description("토큰 ID"),
                        fieldWithPath("data.token").type(STRING).description("토큰 값"),
                        fieldWithPath("data.status").type(STRING).description("토큰 상태"),
                        fieldWithPath("data.position").type(NUMBER).description("순서"),
                    ),
                ),
            )
    }
}

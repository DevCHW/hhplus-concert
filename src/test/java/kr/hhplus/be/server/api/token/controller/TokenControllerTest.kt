package kr.hhplus.be.server.api.token.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.github.f4b6a3.tsid.TsidCreator
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.token.controller.dto.enums.TokenStatus
import kr.hhplus.be.server.api.token.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.api.token.controller.dto.response.GetTokenResponse
import kr.hhplus.be.server.domain.token.TokenService
import kr.hhplus.be.server.domain.token.fixture.TokenFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class TokenControllerTest : RestDocsTestSupport() {

    private lateinit var tokenController: TokenController
    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setup() {
        tokenService = mockk()
        tokenController = TokenController(tokenService)
        mockMvc = mockController(tokenController)
    }

    @Test
    fun `토큰 생성 API`() {
        val request = CreateTokenRequest(
            userId = TsidCreator.getTsid().toString(),
        )

        val token = TokenFixture.createToken(userId = request.userId)
        every { tokenService.createToken(any()) }
            .returns(token)

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/v1/token")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "토큰 생성",
                    ResourceSnippetParametersBuilder()
                        .tag("대기열")
                        .summary("대기열 토큰 생성")
                        .description(
                            """
                            유저 ID를 입력받아 대기열 토큰을 생성합니다.
                        """.trimIndent()
                        )
                        .requestFields(
                            fieldWithPath("userId").type(STRING).description("유저 ID"),
                        )
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                            fieldWithPath("data.id").type(STRING).description("토큰 ID"),
                            fieldWithPath("data.token").type(STRING).description("토큰 값"),
                            fieldWithPath("data.status").type(STRING).description("토큰 상태"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }

    @Test
    fun `토큰 조회 API`() {
        val token = TokenFixture.createToken()

        val request = GetTokenResponse(
            id = token.id,
            token = token.token,
            status = TokenStatus.INACTIVE,
        )

        every { tokenService.getToken(any()) }
            .returns(token)

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .get("/api/v1/token/{token}", token.token.toString())
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "토큰 조회",
                    ResourceSnippetParametersBuilder()
                        .tag("대기열")
                        .summary("대기열 토큰 조회")
                        .description(
                            """
                            입력받은 토큰의 정보를 조회합니다.
                        """.trimIndent()
                        )
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                            fieldWithPath("data.id").type(STRING).description("토큰 ID"),
                            fieldWithPath("data.token").type(STRING).description("토큰 값"),
                            fieldWithPath("data.status").type(STRING).description("토큰 상태"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }
}

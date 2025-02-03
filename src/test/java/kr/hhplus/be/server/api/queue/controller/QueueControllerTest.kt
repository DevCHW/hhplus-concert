package kr.hhplus.be.server.api.queue.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.queue.controller.dto.enums.TokenStatus
import kr.hhplus.be.server.api.queue.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.api.queue.controller.dto.response.GetTokenResponse
import kr.hhplus.be.server.domain.queue.QueueService
import kr.hhplus.be.server.domain.queue.fixture.TokenFixture
import kr.hhplus.be.server.support.IdGenerator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

@DisplayName("대기열 API 문서 테스트")
class QueueControllerTest : RestDocsTestSupport() {

    private lateinit var queueController: QueueController
    private lateinit var queueService: QueueService

    @BeforeEach
    fun setup() {
        queueService = mockk()
        queueController = QueueController(queueService)
        mockMvc = mockController(queueController)
    }

    @Test
    fun `대기열 토큰 생성 API`() {
        val request = CreateTokenRequest(
            userId = IdGenerator.generate()
        )

        val token = TokenFixture.get(userId = request.userId)
        every { queueService.createQueueToken(any()) }
            .returns(token)

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/v1/queue/token")
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
        val token = TokenFixture.get()

        val request = GetTokenResponse(
            id = token.id,
            token = token.token,
            status = TokenStatus.INACTIVE,
        )

        every { queueService.getToken(any()) }
            .returns(token)

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .header("Queue-Token", token.token.toString())
            .get("/api/v1/queue/token")
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

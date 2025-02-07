package kr.hhplus.be.server.api.queue.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.queue.application.QueueFacade
import kr.hhplus.be.server.api.queue.application.dto.enums.TokenStatus
import kr.hhplus.be.server.api.queue.application.dto.result.CreateWaitingQueueTokenResult
import kr.hhplus.be.server.api.queue.application.dto.result.GetTokenResult
import kr.hhplus.be.server.api.queue.controller.dto.request.CreateTokenRequest
import kr.hhplus.be.server.support.IdGenerator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import java.util.*

@DisplayName("대기열 API 문서 테스트")
class QueueControllerTest : RestDocsTestSupport() {

    private lateinit var queueController: QueueController
    private lateinit var queueFacade: QueueFacade

    @BeforeEach
    fun setup() {
        queueFacade = mockk()
        queueController = QueueController(queueFacade)
        mockMvc = mockController(queueController)
    }

    @Test
    fun `대기열 토큰 생성 API`() {
        val request = CreateTokenRequest(
            userId = IdGenerator.generate()
        )

        val result = CreateWaitingQueueTokenResult(
            token = UUID.randomUUID(),
            status = TokenStatus.INACTIVE,
            rank = 1
        )
        every { queueFacade.createWaitingQueueToken(any()) }
            .returns(result)

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
        val token = UUID.randomUUID()
        val userId = IdGenerator.generate()

        val result = GetTokenResult(
            userId = userId,
            token = token,
            status = TokenStatus.INACTIVE,
            rank = 1,
        )

        every { queueFacade.getToken(any(), any()) }
            .returns(result)

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", userId)
            .header("Queue-Token", token.toString())
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
                            fieldWithPath("data.token").type(STRING).description("토큰 값"),
                            fieldWithPath("data.status").type(STRING).description("토큰 상태"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }
}

package kr.hhplus.be.server.api.queue.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.queue.controller.dto.enums.TokenStatus
import kr.hhplus.be.server.api.queue.controller.dto.request.CreateQueueTokenRequest
import kr.hhplus.be.server.api.queue.controller.dto.response.CreateQueueTokenResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import java.util.*

class QueueControllerTest : RestDocsTestSupport() {

    private lateinit var queueController: QueueController

    @BeforeEach
    fun setup() {
        queueController = QueueController()
        mockMvc = mockController(queueController)
    }

    @Test
    fun `대기열 토큰 생성 API`() {
        val request = CreateQueueTokenRequest(
            userId = UUID.randomUUID().toString(),
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/v1/queue/token")
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
                        fieldWithPath("data.token").type(STRING).description("토큰"),
                        fieldWithPath("data.tokenStatus").type(STRING).description("토큰 상태"),
                        fieldWithPath("data.position").type(NUMBER).description("대기 순서"),
                    ),
                ),
            )
    }

    @Test
    fun `대기열 토큰 상태 조회 API`() {
        val request = CreateQueueTokenResponse(
            token = UUID.randomUUID(),
            tokenStatus = TokenStatus.WAITING,
            position = 100,
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .get("/api/v1/queue/status")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "대기열 토큰 상태 조회",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                        fieldWithPath("data").type(OBJECT).description("결과 데이터"),
                        fieldWithPath("data.token").type(STRING).description("토큰"),
                        fieldWithPath("data.tokenStatus").type(STRING).description("토큰 상태"),
                        fieldWithPath("data.position").type(NUMBER).description("대기 순서"),
                    ),
                ),
            )
    }
}

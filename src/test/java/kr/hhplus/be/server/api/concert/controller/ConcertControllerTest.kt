package kr.hhplus.be.server.api.concert.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import kr.hhplus.be.server.api.concert.application.ConcertFacade
import kr.hhplus.be.server.api.concert.application.dto.result.GetConcertsResult
import kr.hhplus.be.server.domain.concert.ConcertService
import kr.hhplus.be.server.domain.concert.fixture.ConcertFixture
import kr.hhplus.be.server.support.IdGenerator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import java.math.BigDecimal
import java.time.LocalDateTime

@DisplayName("콘서트 API 문서 테스트")
class ConcertControllerTest : RestDocsTestSupport() {

    private lateinit var concertFacade: ConcertFacade
    private lateinit var concertController: ConcertController

    @BeforeEach
    fun setup() {
        concertFacade = mockk()
        concertController = ConcertController(concertFacade)
        mockMvc = mockController(concertController)
    }

    @Test
    fun `콘서트 목록 조회 API`() {
        val data = listOf(
            GetConcertsResult(
                id = IdGenerator.generate(),
                title = "title1",
                price = BigDecimal.valueOf(100),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            ),
            GetConcertsResult(
                id = IdGenerator.generate(),
                title = "title2",
                price = BigDecimal.valueOf(100),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            ),
        )

        every { concertFacade.getConcerts() }
            .returns(data)

        given()
            .contentType(ContentType.JSON)
            .get("/api/v1/concerts")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "콘서트 목록 조회",
                    ResourceSnippetParametersBuilder()
                        .tag("콘서트")
                        .summary("콘서트 목록 조회")
                        .description("""
                            콘서트 목록을 조회합니다.
                        """.trimIndent())
                        .responseFields(
                            fieldWithPath("result").type(STRING).description("요청 결과(SUCCESS / ERROR)"),
                            fieldWithPath("data[]").type(ARRAY).description("결과 데이터"),
                            fieldWithPath("data[].id").type(STRING).description("콘서트 ID"),
                            fieldWithPath("data[].title").type(STRING).description("콘서트 이름"),
                        ),
                    requestPreprocessor(),
                    responsePreprocessor(),
                ),
            )
    }

}
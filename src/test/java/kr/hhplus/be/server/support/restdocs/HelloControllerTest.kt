package kr.hhplus.be.server.support.restdocs

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields

class HelloControllerTest : RestDocsTestSupport() {
    private lateinit var helloController: HelloController

    @BeforeEach
    fun setup() {
        helloController = HelloController()
        mockMvc = mockController(helloController)
    }

    @Test
    fun `Hello API 문서`() {
        given()
            .contentType(ContentType.JSON)
            .get("/hello")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "헬로",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("message").type(STRING).description("메세지"),
                    ),
                ),
            )
    }
}
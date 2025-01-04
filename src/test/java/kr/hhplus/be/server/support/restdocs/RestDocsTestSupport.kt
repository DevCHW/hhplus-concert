package com.hhplus.board.support.restdocs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.RestAssured
import io.restassured.config.EncoderConfig
import io.restassured.config.LogConfig
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import java.nio.charset.StandardCharsets

@ExtendWith(RestDocumentationExtension::class)
abstract class RestDocsTestSupport {
    lateinit var mockMvc: MockMvcRequestSpecification
    private lateinit var restDocumentation: RestDocumentationContextProvider

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        this.restDocumentation = restDocumentation
    }

    protected fun given(): MockMvcRequestSpecification {
        return mockMvc
    }

    protected fun mockController(controller: Any): MockMvcRequestSpecification {
        val config =
            RestAssuredMockMvcConfig.config()
                .encoderConfig(
                    EncoderConfig.encoderConfig()
                        .defaultCharsetForContentType(StandardCharsets.UTF_8, ContentType.JSON)
                )
                .logConfig(
                    LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
                        .enablePrettyPrinting(true)
                )

        return RestAssuredMockMvc.given()
            .config(config)
            .mockMvc(createMockMvc(controller))
            .contentType(ContentType.JSON).log()
            .everything(true)
    }

    private fun createMockMvc(controller: Any): MockMvc {
        RestAssured
            .filters(RequestLoggingFilter(), ResponseLoggingFilter())

        val converter = MappingJackson2HttpMessageConverter(objectMapper())
        return MockMvcBuilders.standaloneSetup(controller)
            .apply<StandaloneMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .setMessageConverters(converter)
            .build()
    }

    private fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
    }
}



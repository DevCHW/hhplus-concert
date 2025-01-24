package kr.hhplus.be.server.api.support.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class LoggingFilter : OncePerRequestFilter() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val responseWrapper = ContentCachingResponseWrapper(response)

        val startTime = System.currentTimeMillis()
        MDC.put("requestId", UUID.randomUUID().toString())
        try {
            val requestWrapper = CachingBodyRequestWrapper(request)
            logRequest(requestWrapper)
            filterChain.doFilter(requestWrapper, responseWrapper)
        } finally {
            logResponse(responseWrapper, startTime)
            responseWrapper.copyBodyToResponse()
        }
        MDC.clear()
    }

    @Throws(IOException::class)
    private fun logRequest(request: HttpServletRequest) {
        val queryString = request.queryString
        val body = getBody(request.inputStream)
        log.info(
            ">>> Request : {} URI=[{}], Content-Type=[{}], Body=[{}], Request ID=[{}]",
            request.method,
            if (queryString == null) request.requestURI else request.requestURI + "?" + queryString,
            request.contentType,
            body,
            MDC.get("requestId")
        )
    }

    @Throws(IOException::class)
    private fun logResponse(response: ContentCachingResponseWrapper, startTime: Long) {
        val body = getBody(response.contentInputStream)

        log.info(
            "<<< Response : Status Code=[{}], Body=[{}], Request ID=[{}] Transaction Time=[{}ms]",
            response.status,
            body,
            MDC.get("requestId"),
            (System.currentTimeMillis() - startTime)
        )
    }

    @Throws(IOException::class)
    fun getBody(inputStream: InputStream): String? {
        val content = StreamUtils.copyToByteArray(inputStream)
        if (content.isEmpty()) {
            return null
        }
        val rawBody = String(content, StandardCharsets.UTF_8)

        // JSON 데이터 압축
        return try {
            val objectMapper = ObjectMapper()
            val jsonNode = objectMapper.readTree(rawBody)
            objectMapper.writeValueAsString(jsonNode) // Minified JSON
        } catch (e: Exception) {
            rawBody // JSON이 아닌 경우 원본 반환
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val excludeFilterPath = arrayOf("/h2-console")
        val path = request.requestURI
        return Arrays.stream(excludeFilterPath)
            .anyMatch { prefix: String? -> path.startsWith(prefix!!) }
    }
}
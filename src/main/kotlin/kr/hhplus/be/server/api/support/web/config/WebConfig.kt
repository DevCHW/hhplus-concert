package kr.hhplus.be.server.api.support.web.config

import kr.hhplus.be.server.api.support.web.interceptor.QueueTokenValidInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val queueTokenValidInterceptor: QueueTokenValidInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(queueTokenValidInterceptor)
            .excludePathPatterns("/api/v1/queue/token")
            .excludePathPatterns("/api/v1/balance/**")
            .excludePathPatterns("/api/v1/concerts/popular")
            .excludePathPatterns("/api/v1/data-platform/reservations/pay")
    }
}
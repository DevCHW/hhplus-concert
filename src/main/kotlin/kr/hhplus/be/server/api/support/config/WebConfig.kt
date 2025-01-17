package kr.hhplus.be.server.api.support.config

import kr.hhplus.be.server.api.support.filter.QueueTokenValidInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val queueTokenValidInterceptor: QueueTokenValidInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(queueTokenValidInterceptor)
            .excludePathPatterns("/api/v1/token")
            .excludePathPatterns("/api/v1/balance/**")
    }
}
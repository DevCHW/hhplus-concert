package kr.hhplus.be.server.infra.feign.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["kr.hhplus.be.server.infra.feign"])
class FeignConfig
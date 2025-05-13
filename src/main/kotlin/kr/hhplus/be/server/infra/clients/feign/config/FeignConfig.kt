package kr.hhplus.be.server.infra.clients.feign.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["kr.hhplus.be.server.infra.clients.feign"])
class FeignConfig
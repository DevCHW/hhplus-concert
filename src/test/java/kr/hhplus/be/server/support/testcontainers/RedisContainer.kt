package kr.hhplus.be.server.support.testcontainers

import org.testcontainers.containers.GenericContainer

class RedisContainer {
    companion object {
        val redisContainer: GenericContainer<*> = GenericContainer("redis:6.2.7-alpine")
            .withExposedPorts(6379)
            .withReuse(true)
            .apply { start() }
    }
}
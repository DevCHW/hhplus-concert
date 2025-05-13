package kr.hhplus.be.server.support.testcontainers

import org.testcontainers.containers.GenericContainer

object RedisContainer {

    val redisContainer: GenericContainer<*> = GenericContainer("redis:6.2.6-alpine")
        .withExposedPorts(6379)
        .withReuse(true)
}
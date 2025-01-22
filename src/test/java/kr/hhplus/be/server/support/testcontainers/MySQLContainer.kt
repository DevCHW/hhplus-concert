package kr.hhplus.be.server.support.testcontainers

import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

class MySQLContainer {
    companion object {
        val mySqlContainer: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("hhplus")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("sql/schema.sql")
            .waitingFor(Wait.forHttp("/"))
            .withReuse(true)
            .apply { start() }
    }
}
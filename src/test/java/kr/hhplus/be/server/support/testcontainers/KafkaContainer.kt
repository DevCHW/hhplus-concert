package kr.hhplus.be.server.support.testcontainers

import org.testcontainers.kafka.ConfluentKafkaContainer
import org.testcontainers.utility.DockerImageName

class KafkaContainer {

    companion object {
        val kafkaContainer: ConfluentKafkaContainer = ConfluentKafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.8.1")
        )
            .withReuse(true)
    }
}
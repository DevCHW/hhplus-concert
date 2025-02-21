package kr.hhplus.be.server.support.testcontainers

import org.testcontainers.kafka.ConfluentKafkaContainer
import org.testcontainers.utility.DockerImageName

class KafkaContainer {

    companion object {
        val kafkaContainer: ConfluentKafkaContainer = ConfluentKafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
                .asCompatibleSubstituteFor("apache/kafka")
        )
            .withReuse(true)
            .apply { start() }
    }
}
package kr.hhplus.be.server.scheduler.outbox

import kr.hhplus.be.server.domain.outbox.OutboxService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OutboxScheduler(
    private val outboxService: OutboxService
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedRate = 10000)
    fun processOutbox() {
        // 'PENDING' 상태의 아웃박스 데이터 목록 조회
        val outboxList = outboxService.readPendingMessages()

        outboxList.forEach {
            outboxService.send(it)
            log.info("아웃박스 데이터 처리 완료. data = ${it}")
        }
    }
}
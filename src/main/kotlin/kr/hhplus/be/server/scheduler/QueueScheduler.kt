package kr.hhplus.be.server.scheduler

import kr.hhplus.be.server.domain.queue.QueueService
import org.springframework.stereotype.Component

@Component
class QueueScheduler(
    private val queueService: QueueService,
) {

}

package kr.hhplus.be.server.listener

import kr.hhplus.be.server.domain.client.DataPlatformService
import kr.hhplus.be.server.listener.event.ReservationCompleteEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReservationCompleteEventListener(
    private val dataPlatformService: DataPlatformService,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun listen(event: ReservationCompleteEvent) {
        val isSuccess = dataPlatformService.sendData(event.toModel())

        if (!isSuccess) {
            log.error("데이터 플랫폼에 데이터 전달이 실패하였습니다.")
        }
    }
}
